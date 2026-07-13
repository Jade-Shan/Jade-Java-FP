package net.jadedungeon.javautil.collection.solitary;

import java.util.function.Function;

import net.jadedungeon.javautil.functional.Monad;

/**
 * Some —— Option 的"有值"状态。
 * <p>
 * 表示一个确实存在的值。构造函数为私有，通过 {@link #of(Object)} 工厂方法构造实例。
 * </p>
 * <p>
 * 不可变：内部值在构造时确定，之后不会改变。
 * </p>
 *
 * @param <T> 内部值的类型
 */
public final class Some<T> extends Option<T> {

	private final T value;

	/**
	 * 对内部值应用 mapper 函数，返回包装了变换结果的 Some。
	 *
	 * @param <R>    变换后的结果类型
	 * @param mapper 对内部值进行变换的函数
	 * @return 包装了变换结果的 Some
	 */
	@Override
	public <R> Option<R> map(Function<? super T, ? extends R> mapper) {
		return of(mapper.apply(value));
	}

	/**
	 * 对内部值应用 mapper 函数，直接返回 mapper 的结果（不做额外包装）。
	 *
	 * @param <R>    变换后的元素类型
	 * @param <M>    返回的 Monad 具体类型
	 * @param mapper 将内部值映射到另一个 Monad 的函数
	 * @return mapper 函数的返回值（已是一个 Monad）
	 */
	@Override
	public <R, M extends Monad<? extends R>> M flatMap(Function<? super T, M> mapper) {
		return mapper.apply(value);
	}

	/**
	 * 私有构造函数，请使用 {@link #of(Object)} 工厂方法创建实例。
	 *
	 * @param value 要包装的非 null 值
	 */
	private Some(T value) {
		this.value = value;
	}

	/**
	 * 工厂方法：创建一个包含指定值的 Some 实例。
	 *
	 * @param <T>   值的类型
	 * @param value 要包装的值
	 * @return 新的 Some 实例
	 */
	public static <T> Some<T> of(T value) {
		return new Some<T>(value);
	}


	/**
	 * Some 永远不为空。
	 *
	 * @return 始终返回 false
	 */
	@Override
	public boolean isEmpty() {
		return false;
	}

	/**
	 * 返回内部持有的值。
	 *
	 * @return 内部值
	 */
	@Override
	public T eval() {
		return value;
	}

	@Override
	public String toString() {
		return String.format("{\"type\":\"Some\",\"value\":\"%s\"}", value.toString());
	}
}
