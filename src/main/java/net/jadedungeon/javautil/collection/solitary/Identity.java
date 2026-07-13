package net.jadedungeon.javautil.collection.solitary;

import java.util.function.Function;

import net.jadedungeon.javautil.functional.Monad;

/**
 * Identity Monad —— 最简单的 Monad 实现。
 * <p>
 * Identity 仅仅是一个不可变的包装容器，没有任何额外的计算逻辑或副作用。
 * 它的 {@link #map(Function)} 和 {@link #flatMap(Function)} 行为与
 * Haskell 的 Identity Monad 完全一致：
 * </p>
 * <ul>
 *   <li>{@code map(f)} 等价于包装 {@code f(value)} 的结果</li>
 *   <li>{@code flatMap(f)} 等价于直接调用 {@code f(value)}</li>
 * </ul>
 *
 * <p>Identity 通常用于以下场景：</p>
 * <ul>
 *   <li>教学用途 —— 理解 Monad 的最小示例</li>
 *   <li>类型适配 —— 在需要 Monad 的地方包装一个普通值</li>
 *   <li>Monad Transformer 中的基础层</li>
 * </ul>
 *
 * @param <T> 内部值的类型
 */
public class Identity<T> implements Monad<T> {

	private final T value;

	/**
	 * 私有构造函数，请使用 {@link #of(Object)} 工厂方法创建实例。
	 *
	 * @param value 要包装的值
	 */
	private Identity(T value) {
		this.value = value;
	}

	/**
	 * 工厂方法：创建一个包装了指定值的 Identity 实例。
	 *
	 * @param <T>   值的类型
	 * @param value 要包装的值
	 * @return 新的 Identity 实例
	 */
	public static <T> Identity<T> of(T value) {
		return new Identity<T>(value);
	}

	/**
	 * 对内部值应用 mapper 函数，返回包装了变换结果的 Identity。
	 *
	 * @param <R>    变换后的结果类型
	 * @param mapper 对内部值进行变换的函数
	 * @return 包装了变换结果的 Identity
	 */
	@Override
	public <R> Identity<R> map(Function<? super T, ? extends R> mapper) {
		return new Identity<R>(mapper.apply(value));
	}

	/**
	 * 对内部值应用 mapper 函数，直接返回 mapper 的结果。
	 *
	 * @param <R>    变换后的元素类型
	 * @param <M>    返回的 Monad 具体类型
	 * @param mapper 将内部值映射到另一个 Monad 的函数
	 * @return mapper 函数的返回值
	 */
	@Override
	public <R, M extends Monad<? extends R>> M flatMap(Function<? super T, M> mapper) {
		return mapper.apply(value);
	}

	@Override
	public String toString() {
		return String.format("{\"type\":\"Identity\",\"value\":\"%s\"}", value.toString());
	}
}
