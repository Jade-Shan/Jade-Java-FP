package net.jadedungeon.javautil.collection.solitary;

import java.util.function.Function;

import net.jadedungeon.javautil.functional.Monad;

public class Promise<T> implements Monad<T> {

	private final T value;

	private Promise(T value) {
		this.value = value;
	}

	public static <T> Promise<T> of(T value) {
		return new Promise<T>(value);
	}

	@Override
	public <R> Promise<R> map(Function<? super T, ? extends R> mapper) {
		return new Promise<R>(mapper.apply(value));
	}

	@Override
	public <R, M extends Monad<? extends R>> M flatMap(Function<? super T, M> mapper) {
		return mapper.apply(value);
	}

	@Override
	public String toString() {
		return String.format("{\"type\":\"Promise\",\"value\":\"%s\"}", value.toString());
	}

}
