package net.jadedungeon.javautil.collection.solitary;

import java.util.function.Function;

import net.jadedungeon.javautil.functional.Monad;

public final class Some<T> extends Option<T> {

	private final T value;

	@Override
	public <R> Option<R> map(Function<? super T, ? extends R> mapper) {
		return of(mapper.apply(value));
	}

	@Override
	public <R, M extends Monad<? extends R>> M flatMap(Function<? super T, M> mapper) {
		return mapper.apply(value);
	}

	private Some(T value) {
		this.value = value;
	}
	
	public static <T> Some<T> of(T value) {
		return new Some<T>(value);
	}


	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public T eval() {
		return value;
	}

	@Override
	public String toString() {
		return String.format("{\"type\":\"Some\",\"value\":\"%s\"}", value.toString());
	}
}
