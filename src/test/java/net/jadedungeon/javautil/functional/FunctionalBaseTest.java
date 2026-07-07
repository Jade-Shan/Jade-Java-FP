package net.jadedungeon.javautil.functional;

import java.util.function.Supplier;

import org.junit.Test;

public class FunctionalBaseTest {

	String reverse(String str) {
		return new StringBuffer(str).reverse().toString();
	}

	@Test
	public void testSideEffect() {
		String s = "hello, world";
		String s1 = reverse(s);
		System.out.println(s);
		System.out.println(s1);
	}

	static boolean compute(String str) {
		System.out.println("executing...");
		// expensive computation here
		return str.contains("a");
	}

	static String eagerMatch(boolean b1, boolean b2) {
		return b1 && b2 ? "match" : "incompatible!";
	}

	static String lazyMatch(Supplier<Boolean> a, Supplier<Boolean> b) {
		return a.get() && b.get() ? "match" : "incompatible!";
	}

	@Test
	public void testLazy() {
		System.out.print(eagerMatch(compute("bb"), compute("aa")));
		System.out.print(lazyMatch(() -> compute("bb"), () -> compute("aa")));

	}

}
