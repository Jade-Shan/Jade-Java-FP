package net.jadedungeon.javautil.collection.solitary;

import java.util.function.Function;

import net.jadedungeon.javautil.functional.Monad;

public abstract class Option<T> implements Monad<T> {

	abstract public boolean isEmpty();

	abstract protected T eval();

	@SuppressWarnings("unchecked")
	public static <T> None<T> empty() {
		return (None<T>) None.NONE;
	}

	public static <T> Option<T> of(T valueOrNull) {
		if (null == valueOrNull) {
			return empty();
		} else {
			return Some.<T>of(valueOrNull);
		}
	}

	abstract public <R> Option<R> map(Function<? super T, ? extends R> mapper);

	abstract public <R, M extends Monad<? extends R>> M flatMap(Function<? super T, M> mapper);
	
	
}
