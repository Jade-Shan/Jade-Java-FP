package net.jadedungeon.javautil.functional;

import java.util.Objects;
import java.util.function.Function;

/**
 * 四参数函数接口。
 * <p>
 * Java 标准库未提供四参数函数接口，本接口补充了此能力。
 * 主要用于 {@link MonadHelper#liftM4} 等场景。
 * </p>
 * <p>
 * 提供了 {@link #andThen(Function)} 方法用于函数组合。
 * </p>
 *
 * @param <T1> 第一个参数的类型
 * @param <T2> 第二个参数的类型
 * @param <T3> 第三个参数的类型
 * @param <T4> 第四个参数的类型
 * @param <R>  返回值的类型
 */
public interface Function4<T1, T2, T3, T4, R> {

	/**
	 * 将四个参数应用于此函数。
	 *
	 * @param t1 第一个参数
	 * @param t2 第二个参数
	 * @param t3 第三个参数
	 * @param t4 第四个参数
	 * @return 函数计算结果
	 */
	R apply(T1 t1, T2 t2, T3 t3, T4 t4);

	/**
	 * 返回一个组合函数，先将此函数应用于输入，再将结果应用于 after 函数。
	 *
	 * @param <V>   after 函数的返回值类型
	 * @param after 在此函数之后应用的转换函数
	 * @return 组合后的四参数函数
	 * @throws NullPointerException 如果 after 为 null
	 */
	default <V> Function4<T1, T2, T3, T4, V> andThen(Function<? super R, ? extends V> after) {
		Objects.requireNonNull(after);
		return (T1 t1, T2 t2, T3 t3, T4 t4) -> after.apply(apply(t1, t2, t3, t4));
	}
}
