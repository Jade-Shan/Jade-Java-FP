package net.jadedungeon.javautil.collection.solitary;

import java.util.function.Function;

import net.jadedungeon.javautil.functional.Monad;

/**
 * Promise —— 一个与 {@link Identity} 类似的值包装器（Monad）。
 * <p>
 * Promise 在行为上与 Identity 基本一致，但语义上代表一个"已决议"的值
 * （区别于尚未完成的异步 Future/Promise 概念）。
 * 可以用于在类型系统中标记一个已确定的值。
 * </p>
 * <p>
 * 不可变：内部值在构造时确定，所有操作返回新的 Promise 实例。
 * </p>
 *
 * @param <T> 内部值的类型
 */
public class Promise<T> implements Monad<T> {

	private final T value;

	/**
	 * 私有构造函数，请使用 {@link #of(Object)} 工厂方法创建实例。
	 *
	 * @param value 要包装的值
	 */
	private Promise(T value) {
		this.value = value;
	}

	/**
	 * 工厂方法：创建一个包装了指定值的 Promise 实例。
	 *
	 * @param <T>   值的类型
	 * @param value 要包装的值
	 * @return 新的 Promise 实例
	 */
	public static <T> Promise<T> of(T value) {
		return new Promise<T>(value);
	}

	/**
	 * 对内部值应用 mapper 函数，返回包装了变换结果的 Promise。
	 *
	 * @param <R>    变换后的结果类型
	 * @param mapper 对内部值进行变换的函数
	 * @return 包装了变换结果的 Promise
	 */
	@Override
	public <R> Promise<R> map(Function<? super T, ? extends R> mapper) {
		return new Promise<R>(mapper.apply(value));
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
		return String.format("{\"type\":\"Promise\",\"value\":\"%s\"}", value.toString());
	}

}
