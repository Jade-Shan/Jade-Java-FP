package net.jadedungeon.javautil.functional;

import java.util.function.Function;

public interface Monad<T> extends Functor<T> {

	<R> Monad<? extends R> map(Function<? super T, ? extends R> mapper);

	<R, M extends Monad<? extends R>> M flatMap(Function<? super T, M> mapper);
	
}
