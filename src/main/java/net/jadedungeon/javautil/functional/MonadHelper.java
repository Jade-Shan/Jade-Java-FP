package net.jadedungeon.javautil.functional;

import java.util.function.BiFunction;

public class MonadHelper {

	@SuppressWarnings("unchecked")
	static <T1, T2, S, R extends Monad<S>> //
	R liftM2(Monad<T1> m1, Monad<T2> m2, //
			BiFunction<? super T1, ? super T2, ? extends S> op) //
	{
		return m1.flatMap(t1 -> (R) (m2.map(t2 -> op.apply(t1, t2))//
		));
	}

	@SuppressWarnings("unchecked")
	static <T1, T2, T3, S, R extends Monad<S>> //
	R liftM3(Monad<T1> m1, Monad<T2> m2, Monad<T3> m3, //
			Function3<? super T1, ? super T2, ? super T3, ? extends S> op) //
	{
		return m1.flatMap(t1 -> m2.flatMap(t2 -> //
		(R) (m3.map(t3 -> op.apply(t1, t2, t3))//
		)));
	}

	@SuppressWarnings("unchecked")
	static <T1, T2, T3, T4, S, R extends Monad<S>> //
	R liftM4(Monad<T1> m1, Monad<T2> m2, Monad<T3> m3, Monad<T4> m4, //
			Function4<T1, T2, T3, T4, S> op) //
	{
		return m1.flatMap(t1 -> m2.flatMap(t2 -> m3.flatMap(t3 -> //
		(R) (m4.map(t4 -> op.apply(t1, t2, t3, t4))//
		))));
	}

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
