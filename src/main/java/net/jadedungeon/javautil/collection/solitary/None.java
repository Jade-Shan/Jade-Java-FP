package net.jadedungeon.javautil.collection.solitary;

import java.util.NoSuchElementException;
import java.util.function.Function;

import net.jadedungeon.javautil.functional.Monad;

public class None<T> extends Option<T> {
	static final None<?> NONE = new None<>();

	protected None() {
	}

	@SuppressWarnings("unchecked")
	@Override
	public <R> None<R> map(Function<? super T, ? extends R> mapper) {
		return (None<R>) NONE;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <R, M extends Monad<? extends R>> M flatMap(Function<? super T, M> mapper) {
		return (M) NONE;
	}

	@Override
	public boolean isEmpty() {
		return true;
	}

	@Override
	public T eval() {
		throw new NoSuchElementException("Empty Option type None");
	}

	@Override
	public String toString() {
		return String.format("{\"type\":\"None\"}");
	}
}
