package net.jadedungeon.javautil.functional;

import java.util.function.BiFunction;

/**
 * Monad 辅助工具类，提供 Monad 的提升操作（lifting）。
 * <p>
 * 将普通的多参数函数"提升"到 Monad 上下文中运行。
 * 例如，{@link #liftM2(Monad, Monad, BiFunction)} 可以将一个普通的
 * {@code BiFunction<T1, T2, S>} 提升为在 Monad 内运行的版本，
 * 自动处理 flatMap/map 的组合逻辑。
 * </p>
 *
 * <p>支持从 2 参数到 5 参数的函数提升（liftM2 ~ liftM5）：</p>
 * <ul>
 *   <li>{@code liftM2} —— 提升双参数函数</li>
 *   <li>{@code liftM3} —— 提升三参数函数，使用 {@link Function3}</li>
 *   <li>{@code liftM4} —— 提升四参数函数，使用 {@link Function4}</li>
 *   <li>{@code liftM5} —— 提升五参数函数，使用 {@link Function5}</li>
 * </ul>
 *
 * <p>该模式是 Haskell 中 Applicative 的替代方案，使用 Monad 的 flatMap 来实现序列化计算。</p>
 */
public class MonadHelper {

	/**
	 * 将双参数函数提升到 Monad 上下文中运行。
	 * <p>
	 * 对 m1 和 m2 中的值应用 op 函数，自动处理 Monad 的展开和包装。
	 * 如果任一 Monad 为空（如 None），结果也为空。
	 * </p>
	 *
	 * @param <T1> 第一个 Monad 的元素类型
	 * @param <T2> 第二个 Monad 的元素类型
	 * @param <S>  函数返回值类型
	 * @param <R>  返回的 Monad 具体类型
	 * @param m1   第一个 Monad
	 * @param m2   第二个 Monad
	 * @param op   要提升的双参数函数
	 * @return 包装了 op 运算结果的 Monad
	 */
	@SuppressWarnings("unchecked")
	static <T1, T2, S, R extends Monad<S>> //
	R liftM2(Monad<T1> m1, Monad<T2> m2, //
			BiFunction<? super T1, ? super T2, ? extends S> op) //
	{
		return m1.flatMap(t1 -> (R) (m2.map(t2 -> op.apply(t1, t2))//
		));
	}

	/**
	 * 将三参数函数提升到 Monad 上下文中运行。
	 *
	 * @param <T1> 第一个 Monad 的元素类型
	 * @param <T2> 第二个 Monad 的元素类型
	 * @param <T3> 第三个 Monad 的元素类型
	 * @param <S>  函数返回值类型
	 * @param <R>  返回的 Monad 具体类型
	 * @param m1   第一个 Monad
	 * @param m2   第二个 Monad
	 * @param m3   第三个 Monad
	 * @param op   要提升的三参数函数
	 * @return 包装了 op 运算结果的 Monad
	 */
	@SuppressWarnings("unchecked")
	static <T1, T2, T3, S, R extends Monad<S>> //
	R liftM3(Monad<T1> m1, Monad<T2> m2, Monad<T3> m3, //
			Function3<? super T1, ? super T2, ? super T3, ? extends S> op) //
	{
		return m1.flatMap(t1 -> m2.flatMap(t2 -> //
		(R) (m3.map(t3 -> op.apply(t1, t2, t3))//
		)));
	}

	/**
	 * 将四参数函数提升到 Monad 上下文中运行。
	 *
	 * @param <T1> 第一个 Monad 的元素类型
	 * @param <T2> 第二个 Monad 的元素类型
	 * @param <T3> 第三个 Monad 的元素类型
	 * @param <T4> 第四个 Monad 的元素类型
	 * @param <S>  函数返回值类型
	 * @param <R>  返回的 Monad 具体类型
	 * @param m1   第一个 Monad
	 * @param m2   第二个 Monad
	 * @param m3   第三个 Monad
	 * @param m4   第四个 Monad
	 * @param op   要提升的四参数函数
	 * @return 包装了 op 运算结果的 Monad
	 */
	@SuppressWarnings("unchecked")
	static <T1, T2, T3, T4, S, R extends Monad<S>> //
	R liftM4(Monad<T1> m1, Monad<T2> m2, Monad<T3> m3, Monad<T4> m4, //
			Function4<T1, T2, T3, T4, S> op) //
	{
		return m1.flatMap(t1 -> m2.flatMap(t2 -> m3.flatMap(t3 -> //
		(R) (m4.map(t4 -> op.apply(t1, t2, t3, t4))//
		))));
	}

	/**
	 * 将五参数函数提升到 Monad 上下文中运行。
	 *
	 * @param <T1> 第一个 Monad 的元素类型
	 * @param <T2> 第二个 Monad 的元素类型
	 * @param <T3> 第三个 Monad 的元素类型
	 * @param <T4> 第四个 Monad 的元素类型
	 * @param <T5> 第五个 Monad 的元素类型
	 * @param <S>  函数返回值类型
	 * @param <R>  返回的 Monad 具体类型
	 * @param m1   第一个 Monad
	 * @param m2   第二个 Monad
	 * @param m3   第三个 Monad
	 * @param m4   第四个 Monad
	 * @param m5   第五个 Monad
	 * @param op   要提升的五参数函数
	 * @return 包装了 op 运算结果的 Monad
	 */
	@SuppressWarnings("unchecked")
	static <T1, T2, T3, T4, T5, S, R extends Monad<S>> //
	R liftM5(Monad<T1> m1, Monad<T2> m2, Monad<T3> m3, Monad<T4> m4, Monad<T5> m5, //
			Function5<T1, T2, T3, T4, T5, S> op) //
	{
		return m1.flatMap(t1 -> m2.flatMap(t2 -> m3.flatMap(t3 -> m4.flatMap(t4 -> //
		(R) (m5.map(t5 -> op.apply(t1, t2, t3, t4, t5))//
		)))));
	}
}
