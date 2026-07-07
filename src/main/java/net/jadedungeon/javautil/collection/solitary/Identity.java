package net.jadedungeon.javautil.collection.solitary;

import java.util.function.Function;

import net.jadedungeon.javautil.functional.Monad;

public class Identity<T> implements Monad<T> {

	private final T value;

	private Identity(T value) {
		this.value = value;
	}

	public static <T> Identity<T> of(T value) {
		return new Identity<T>(value);
	}

	@Override
	public <R> Identity<R> map(Function<? super T, ? extends R> mapper) {
		return new Identity<R>(mapper.apply(value));
	}

	@Override
	public <R, M extends Monad<? extends R>> M flatMap(Function<? super T, M> mapper) {
		return mapper.apply(value);
	}

	@Override
	public String toString() {
		return String.format("{\"type\":\"Identity\",\"value\":\"%s\"}", value.toString());
	}
}
