package net.jadedungeon.javautil.functional;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import net.jadedungeon.javautil.tco.CallStack;

public class TailCallOptimizationTest {

	private long fact(long n) {
		return n == 0 ? 1 : n * fact(n - 1);
	}

	private long factV2(long n) {
		return factAcc(n, 1);
	}

	private long factAcc(long n, long acc) {
		return n == 0 ? acc : factAcc(n - 1, acc * n);
	}

	private long factToc(long n) {
		return factTcoAcc(n, 1).eval();
	}

	private CallStack<Long> factTcoAcc(long n, long acc) {
		return n == 0 ? //
				CallStack.of(acc) : //
				CallStack.of(() -> factTcoAcc(n - 1, acc * n));
	}

	@Test
	public void testFactResult() {
		assertEquals(fact(5), factV2(5));
		assertEquals(fact(5), factToc(5));
	}
	

	@Test(expected = java.lang.StackOverflowError.class)
	public void testFactStackOverFlow() {
		System.out.println(fact(65535));
	}
	
	

	private long sum(long arg) {
		return sum(arg, 0);
	}

	private long sum(long arg, long acc) {
		return arg == 0 ? acc : sum(arg - 1, acc + arg);
	}

	private long sumTCO(long arg) {
		return sumTcoAcc(arg, 0).eval();
//		CallStack<Integer> stack = sumTCO(arg, 0);
//		Integer result = stack.eval();
//		return result;
	}

	private CallStack<Long> sumTcoAcc(long arg, long acc) {
		return arg == 0 ? //
				CallStack.of(acc) : //
				CallStack.of(() -> sumTcoAcc(arg - 1, acc + arg));
//		if (arg == 0) { //
//			CallStack<Integer> result = CallStack.of(acc); //
//			return result;
//		} else {
//			Supplier<CallStack<Integer>> supplier = //
//					() -> sumTCO(arg - 1, acc + arg);
//			CallStack<Integer> result = CallStack.of(supplier);
//			return result;
//		}
	}

	@Test(expected = java.lang.StackOverflowError.class)
	public void testStackOverFlow() {
		System.out.println(sum(65535));
	}

	@SuppressWarnings("deprecation")
	@Rule
	public ExpectedException expectedEx = ExpectedException.none();

	@Test
	public void testStackOverFlowRule() {
		expectedEx.expect(StackOverflowError.class);
		System.out.println(sum(65535));
	}

	@Test
	public void testTCO() {
		System.out.println(sumTCO(65535));
	}

	@Test
	public void testSumResult() {
		assertEquals(sum(5), sumTCO(5));
	}

}
