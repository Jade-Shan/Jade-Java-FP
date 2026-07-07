package net.jadedungeon.javautil.functional;

import java.util.function.BiFunction;
import java.util.function.Function;

public class Curryable {

	public static <A, B, C> Function<A, Function<B, C>> curry(BiFunction<A, B, C> func) {
		return (A a) -> (B b) -> func.apply(a, b);
	}
	
	public static <A, B, C> BiFunction<A, B, C> uncurry(Function<A, Function<B, C>> func) {
		return (A a, B b) -> func.apply(a).apply(b);
	}
}
