package net.jadedungeon.javautil.collection.multi;

import java.util.Iterator;
import java.util.function.Function;

import net.jadedungeon.javautil.functional.Monad;
import net.jadedungeon.javautil.tco.CallStack;

/**
 * Seq —— 不可变持久化链表（Immutable Persistent List）。
 * <p>
 * Seq 是函数式编程风格的单链表，实现了 {@link Monad} 接口。
 * 它有两个子类：
 * </p>
 * <ul>
 *   <li>{@link Cons} —— 非空节点，包含 head（当前元素）和 tail（剩余列表）</li>
 *   <li>{@link Nil} —— 空列表单例</li>
 * </ul>
 *
 * <p><b>关键特性：</b></p>
 * <ul>
 *   <li><b>不可变</b>：所有操作返回新的 Seq 实例，原 Seq 不受影响</li>
 *   <li><b>持久化</b>：{@link #attach(Object)} 在头部插入（O(1)），复用原列表结构</li>
 *   <li><b>尾调用优化（TCO）</b>：{@link #map(Function)} 和 {@link #flatMap(Function)}
 *       内部使用 {@link CallStack} 实现蹦床（Trampoline）模式，避免大列表递归导致的
 *       {@link StackOverflowError}</li>
 * </ul>
 *
 * <p>使用示例：</p>
 * <pre>{@code
 * Seq<Integer> list = Seq.of(List.of(1, 2, 3));
 * Seq<String> mapped = list.map(x -> "value: " + x);
 * }</pre>
 *
 * @param <T> 列表元素类型
 */
public abstract class Seq<T> implements Monad<T> {

	/**
	 * 判断列表是否为空。
	 *
	 * @return 如果为 Nil 返回 true，否则返回 false
	 */
	public abstract boolean isEmpty();

	/**
	 * 返回列表的元素数量。
	 *
	 * @return 列表长度（O(1)，长度在构造时缓存）
	 */
	public abstract int size();

	/**
	 * 获取列表的第一个元素（head）。
	 *
	 * @return 头元素
	 * @throws java.util.NoSuchElementException 如果列表为空
	 */
	public abstract T head();

	/**
	 * 获取移除头元素后的剩余列表（tail）。
	 *
	 * @return 剩余列表
	 * @throws UnsupportedOperationException 如果列表为空
	 */
	public abstract Seq<T> tail();

	/**
	 * 获取列表的求值结果（即 head）。
	 *
	 * @return 头元素的值
	 */
	protected abstract T eval();

	/**
	 * 创建一个空的 Seq（Nil 单例）。
	 *
	 * @param <T> 元素类型
	 * @return 空的 Seq 实例（Nil 单例）
	 */
	@SuppressWarnings("unchecked")
	public static <T> Nil<T> empty() {
		return (Nil<T>) Nil.NIL;
	}

	/**
	 * 从 {@link Iterable} 创建 Seq。
	 * <p>
	 * 注意：元素会以逆序附加，因此 Iterable 的第一个元素将位于 Seq 的末尾。
	 * 内部使用 {@link #attach(Object)} 逐步构建列表。
	 * </p>
	 *
	 * @param <T>   元素类型
	 * @param items 要转换的可迭代对象
	 * @return 包含 items 中所有元素的新 Seq
	 */
	public static <T> Seq<T> of(Iterable<T> items) {
		return ofAcc(items.iterator(), empty());
	}

	/**
	 * 辅助方法：递归地将迭代器中的元素附加到列表中。
	 *
	 * @param <T>  元素类型
	 * @param ite  元素迭代器
	 * @param list 当前正在构建的列表
	 * @return 构建完成的 Seq
	 */
	private static <T> Seq<T> ofAcc(Iterator<T> ite, Seq<T> list) {
		if (ite.hasNext()) {
			T t = ite.next();
			return ofAcc(ite, list.attach(t));
		} else {
			return list;
		}
	}

//	private static <T, I extends Monad<T>> Seq<T> //
//			unpackInnerElems(Seq<I> from, Seq<T> to) //
//	{
//		if (from.isEmpty()) {
//			return to;
//		} else {
//			T t = from.head().eval();
//			return unpackInnerElems(from.tail(), to.attach(t));
//		}
//	}
//
//	public static <T, I extends Monad<T>, P extends Monad<Seq<T>>> P //
//			merge(Seq<I> from, Function<Seq<T>, P> creater//
//	) {
//		Seq<T> inner = unpackInnerElems(from, empty());
//		return creater.apply(inner);
//	}

	/**
	 * 在列表头部附加一个元素（O(1)）。
	 * <p>
	 * 返回一个新列表，其中头元素为 element，尾部为当前列表。
	 * 原列表完全不受影响，体现了持久化数据结构的特点。
	 * 如果 element 为 null，返回原列表不变。
	 * </p>
	 *
	 * @param element 要附加的元素
	 * @return 新列表（element 在头部，原列表在尾部）
	 */
	public Seq<T> attach(T element) {
		return null == element ? this : new Cons<T>(element, this);
	}

	/**
	 * 连接两个 Seq（当前实现返回 null，待完善）。
	 *
	 * @param seq 要连接的 Seq
	 * @return 连接后的 Seq（当前未实现）
	 */
	public Seq<T> concat(Seq<T> seq) {
		return null;
	}

	/**
	 * 对列表中的每个元素应用 mapper 函数，返回新列表。
	 * <p>
	 * 使用 {@link CallStack} 实现尾调用优化，避免大列表导致的栈溢出。
	 * </p>
	 *
	 * @param <R>    变换后的元素类型
	 * @param mapper 对每个元素进行变换的函数
	 * @return 包含变换后元素的新 Seq
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <R> Seq<R> map(Function<? super T, ? extends R> mapper) {
		return (Seq<R>) mapAcc(this, empty(), mapper).eval();
	}

	/**
	 * map 的尾调用优化辅助方法。
	 * <p>
	 * 递归遍历原列表，对每个元素应用 mapper，将结果累积到临时列表中。
	 * 最终通过 {@link CallStack#eval()} 以迭代方式执行递归链。
	 * </p>
	 *
	 * @param <R>    变换后的元素类型
	 * @param lst    当前正在处理的源列表
	 * @param tmp    当前累积的结果列表
	 * @param mapper 元素变换函数
	 * @return 包装了最终结果的 CallStack
	 */
	private <R> CallStack<Seq<R>> mapAcc( //
			Seq<T> lst, Seq<R> tmp, Function<? super T, ? extends R> mapper) //
	{
		if (lst.isEmpty()) {
			return CallStack.of(tmp);
		} else {
			return CallStack.of(() -> {
				R e = mapper.apply(lst.head());
				return mapAcc(lst.tail(), tmp.attach(e), mapper);
			});
		}
	}

	/**
	 * 对列表中的每个元素应用 mapper 函数，并将结果扁平化。
	 * <p>
	 * mapper 函数返回的是一个 Monad（Seq），flatMap 会将其中的元素
	 * 逐一提炼出来放入结果列表，避免产生嵌套的列表结构。
	 * 使用 {@link CallStack} 实现尾调用优化。
	 * </p>
	 *
	 * @param <R>    变换后的元素类型
	 * @param <M>    返回的 Monad 具体类型（通常为 Seq）
	 * @param mapper 将每个元素映射到一个 Seq 的函数
	 * @return 扁平化后的新 Monad
	 */
	@Override
//	 public <R, M extends Seq<? extends R>> M flatMap(Function<? super T, M> mapper) {
	public <R, M extends Monad<? extends R>> M flatMap(Function<? super T, M> mapper) {
		@SuppressWarnings("unchecked")
		M res = (M) empty(); // MList<R> -> ? extends Monad<? extends R>
		return flatMapAcc(this, res, mapper).eval();
	}

	/**
	 * flatMap 的尾调用优化辅助方法。
	 * <p>
	 * 递归遍历原列表，对每个元素应用 mapper（返回一个 Seq），
	 * 将返回的 Seq 中的元素逐一附加到结果列表。
	 * </p>
	 *
	 * @param <R>    变换后的元素类型
	 * @param <M>    返回的 Monad 具体类型
	 * @param lst    当前正在处理的源列表
	 * @param tmp    当前累积的结果列表
	 * @param mapper 将每个元素映射到一个 Seq 的函数
	 * @return 包装了最终结果的 CallStack
	 */
	private <R, M extends Monad<? extends R>> CallStack<M> flatMapAcc( //
			Seq<T> lst, M tmp, Function<? super T, M> mapper) //
	{
		if (lst.isEmpty()) {
			return CallStack.of(tmp);
		} else {
			return CallStack.of(() -> {
				@SuppressWarnings("unchecked")
				R e = ((Seq<R>) mapper.apply(lst.head())).eval();
				@SuppressWarnings("unchecked")
				Seq<R> tr = (Seq<R>) tmp; // Monad -> MList
				@SuppressWarnings("unchecked")
				M tmp2 = (M) tr.attach(e); // MList.attach(element) ; MList -> Monad
				return flatMapAcc(lst.tail(), tmp2, mapper);
			});
		}
	}

//	@Override
//	public Seq<T> filter(Predicate<T> pdt) {
//		return filterAcc(this, empty(), pdt);
//	}
//
//	public Seq<T> filterAcc(Seq<T> src, Seq<T> tgt, Predicate<T> pdt) {
//		if (src.isEmpty()) {
//			return tgt;
//		} else if (pdt.test(src.head())) {
//			return filterAcc(src.tail(), tgt.attach(src.head()), pdt);
//		} else {
//			return filterAcc(src.tail(), tgt, pdt);
//		}
//	}

}
