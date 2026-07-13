package net.jadedungeon.javautil.tco;

import java.util.function.Supplier;

/**
 * CallStack —— 尾调用优化（TCO / Trampoline）实现。
 * <p>
 * 将递归调用链表示为一个惰性求值的栈帧链表，通过 {@link #eval()} 方法
 * 以迭代方式展开递归链，从而避免 JVM 栈溢出（{@link StackOverflowError}）。
 * 这是蹦床（Trampoline）模式的典型实现，在无法直接进行尾调用优化的 JVM 上
 * 模拟了尾递归的效果。
 * </p>
 *
 * <p>使用模式：</p>
 * <pre>{@code
 * // 普通递归（会栈溢出）：
 * long sum(long n) { return n == 0 ? 0 : n + sum(n - 1); }
 *
 * // TCO 版本（不会栈溢出）：
 * CallStack<Long> sumTCO(long n, long acc) {
 *     return n == 0
 *         ? CallStack.of(acc)                              // 终止帧
 *         : CallStack.of(() -> sumTCO(n - 1, acc + n));    // 中间帧
 * }
 * long result = sumTCO(100000, 0).eval();                  // 以循环方式求值
 * }</pre>
 *
 * <p>核心子类：</p>
 * <ul>
 *   <li>{@link StackBottom} —— 递归终点，持有即刻可得的值</li>
 *   <li>{@link StackFrame} —— 中间栈帧，持有 {@link Supplier} 用于惰性求值下一步</li>
 * </ul>
 *
 * @param <T> 递归计算的返回值类型
 */
public abstract class CallStack<T> {

	/**
	 * 计算整个递归链的最终结果。
	 * <p>
	 * 从当前帧开始，逐帧调用 {@link #stepForward()} 前进，
	 * 直到到达 {@link StackBottom}（递归终点），然后返回该帧的值。
	 * 整个过程是迭代的，不会产生深层方法调用栈。
	 * </p>
	 *
	 * @return 递归计算的最终结果
	 */
	public abstract T eval();

	/**
	 * 工厂方法：创建递归终点栈帧（StackBottom）。
	 * <p>
	 * 对应当前递归分支已得到最终结果，无需继续递归的情况。
	 * </p>
	 *
	 * @param <T>        结果类型
	 * @param eagerValue 即刻可用的最终值
	 * @return 栈底帧（递归终点）
	 */
	public static <T> StackBottom<T> of(T eagerValue) {
		return new StackBottom<>(eagerValue);
	}

	/**
	 * 工厂方法：创建中间栈帧（StackFrame）。
	 * <p>
	 * 对应当前递归分支还需进一步计算的情况，
	 * lazyValue 定义了下一步的递归逻辑。
	 * </p>
	 *
	 * @param <T>       结果类型
	 * @param lazyValue 提供下一步 CallStack 的 Supplier（惰性求值）
	 * @return 中间栈帧
	 */
	public static <T> StackFrame<T> of(Supplier<CallStack<T>> lazyValue) {
		return new StackFrame<>(lazyValue);
	}

	/**
	 * 前进到递归链中的下一个栈帧。
	 *
	 * @return 下一个 CallStack（中间帧返回下一帧，栈底帧会抛出异常）
	 * @throws IllegalStateException 如果当前帧已经是栈底
	 */
	protected abstract CallStack<T> stepForward();

	/**
	 * 检查当前帧是否为递归终点（栈底）。
	 *
	 * @return 如果是 StackBottom 返回 true
	 */
	protected abstract boolean isButtom();

	/**
	 * StackBottom —— 递归终点栈帧。
	 * <p>
	 * 表示计算已完成，持有最终的结果值。
	 * {@link #stepForward()} 会抛出 {@link IllegalStateException}，
	 * 因为已经没有进一步的递归步骤。
	 * </p>
	 *
	 * @param <T> 结果类型
	 */
	public static class StackBottom<T> extends CallStack<T> {

		/** 最终的即刻值 */
		private final T value;

		/**
		 * 私有构造函数，通过 {@link CallStack#of(Object)} 创建。
		 *
		 * @param value 最终计算结果
		 */
		private StackBottom(T value) {
			this.value = value;
		}

		/**
		 * 直接返回最终值（无需迭代）。
		 *
		 * @return 最终计算结果
		 */
		@Override
		public T eval() {
			return value;
		}

		/**
		 * 栈底帧是递归终点。
		 *
		 * @return 始终返回 true
		 */
		@Override
		public boolean isButtom() {
			return true;
		}

		/**
		 * 栈底帧无法继续前进。
		 *
		 * @return 永远不会正常返回
		 * @throws IllegalStateException 因为已是递归终点
		 */
		@Override
		public CallStack<T> stepForward() {
			throw new IllegalStateException("Return has no resume");
		}
	}

	/**
	 * StackFrame —— 中间栈帧。
	 * <p>
	 * 表示计算尚未完成，持有一个 {@link Supplier}，
	 * 在需要下一步时通过 {@link #stepForward()} 惰性求值。
	 * {@link #eval()} 方法通过 while 循环将整个递归链展开为迭代计算。
	 * </p>
	 *
	 * @param <T> 结果类型
	 */
	public static class StackFrame<T> extends CallStack<T> {

		/** 提供下一步 CallStack 的惰性求值 Supplier */
		private final Supplier<CallStack<T>> lazyValue;

		/**
		 * 私有构造函数，通过 {@link CallStack#of(Supplier)} 创建。
		 *
		 * @param lazyValue 提供下一步 CallStack 的 Supplier
		 */
		private StackFrame(Supplier<CallStack<T>> lazyValue) {
			this.lazyValue = lazyValue;
		}

		/**
		 * 以迭代方式计算递归链的最终结果。
		 * <p>
		 * 从当前帧开始，循环调用 {@link #stepForward()} 前进，
		 * 直到到达 {@link StackBottom}，然后返回最终值。
		 * 整个过程使用 while 循环而非递归调用，因此不会导致栈溢出。
		 * </p>
		 *
		 * @return 递归计算的最终结果
		 */
		@Override
		public T eval() {
			CallStack<T> curr = this;
			while (!curr.isButtom()) { // 将递归调用链展开为迭代循环
				curr = curr.stepForward();
			}
			return curr.eval();
		}

		/**
		 * 中间帧不是递归终点。
		 *
		 * @return 始终返回 false
		 */
		@Override
		public boolean isButtom() {
			return false;
		}

		/**
		 * 惰性求值并返回下一步的 CallStack。
		 *
		 * @return 递归链中的下一帧
		 */
		@Override
		public CallStack<T> stepForward() {
			return lazyValue.get();
		}
	}
}
