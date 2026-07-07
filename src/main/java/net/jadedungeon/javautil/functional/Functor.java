package net.jadedungeon.javautil.functional;

import java.util.function.Function;

public interface Functor<T> {

	public <R> Functor<? extends R> map(Function<? super T, ? extends R> mapper);

//	public T unPack();
}
