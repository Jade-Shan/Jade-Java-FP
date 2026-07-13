package net.jadedungeon.javautil.functional;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * 柯里化（Currying）工具类。
 * <p>
 * 提供 {@link #curry(BiFunction)} 和 {@link #uncurry(Function)} 两个静态方法，
 * 用于在双参数函数与柯里化函数之间互转。
 * </p>
 *
 * <p>柯里化是将一个接受多个参数的函数转换为一系列接受单个参数的函数链。
 * 例如，将 {@code BiFunction<A, B, C>} 转换为 {@code Function<A, Function<B, C>>}。</p>
 */
public class Curryable {

	/**
	 * 柯里化：将双参数函数转换为嵌套的单参数函数。
	 *
	 * @param <A>  第一个参数的类型
	 * @param <B>  第二个参数的类型
	 * @param <C>  返回值的类型
	 * @param func 原始的双参数函数
	 * @return 柯里化后的嵌套函数，先接收 A 再接收 B，最终返回 C
	 */
	public static <A, B, C> Function<A, Function<B, C>> curry(BiFunction<A, B, C> func) {
		return (A a) -> (B b) -> func.apply(a, b);
	}

	/**
	 * 反柯里化：将嵌套的单参数函数还原为双参数函数。
	 *
	 * @param <A>  第一个参数的类型
	 * @param <B>  第二个参数的类型
	 * @param <C>  返回值的类型
	 * @param func 柯里化后的嵌套函数
	 * @return 还原后的双参数函数
	 */
	public static <A, B, C> BiFunction<A, B, C> uncurry(Function<A, Function<B, C>> func) {
		return (A a, B b) -> func.apply(a).apply(b);
	}
}
