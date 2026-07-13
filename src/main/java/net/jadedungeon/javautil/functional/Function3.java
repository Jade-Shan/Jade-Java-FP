package net.jadedungeon.javautil.functional;

import java.util.Objects;
import java.util.function.Function;

/**
 * 三参数函数接口。
 * <p>
 * Java 标准库只提供了单参数 {@link Function} 和双参数 {@link java.util.function.BiFunction}，
 * 本接口补充了三参数函数的支持。主要用于 {@link MonadHelper#liftM3} 等场景。
 * </p>
 * <p>
 * 提供了 {@link #andThen(Function)} 方法用于函数组合，
 * 类似于标准库中 {@code Function.andThen()} 的语义。
 * </p>
 *
 * @param <T1> 第一个参数的类型
 * @param <T2> 第二个参数的类型
 * @param <T3> 第三个参数的类型
 * @param <R>  返回值的类型
 */
public interface Function3<T1, T2, T3, R> {

	/**
	 * 将三个参数应用于此函数。
	 *
	 * @param t1 第一个参数
	 * @param t2 第二个参数
	 * @param t3 第三个参数
	 * @return 函数计算结果
	 */
	R apply(T1 t1, T2 t2, T3 t3);

	/**
	 * 返回一个组合函数，先将此函数应用于输入，再将结果应用于 after 函数。
	 *
	 * @param <V>   after 函数的返回值类型
	 * @param after 在此函数之后应用的转换函数
	 * @return 组合后的三参数函数
	 * @throws NullPointerException 如果 after 为 null
	 */
	default <V> Function3<T1, T2, T3, V> andThen(Function<? super R, ? extends V> after) {
		Objects.requireNonNull(after);
		return (T1 t1, T2 t2, T3 t3) -> after.apply(apply(t1, t2, t3));
	}
}
