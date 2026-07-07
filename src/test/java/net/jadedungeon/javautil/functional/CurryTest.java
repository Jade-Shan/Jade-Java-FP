package net.jadedungeon.javautil.functional;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

import org.junit.Test;

public class CurryTest {
	
	private BiFunction<Integer, Integer, Integer> addFun1 = //
			(Integer a, Integer b) -> a + b;
	private Function<Integer, Function<Integer, Integer>> addFun2 = //
			(Integer a) -> (Integer b) -> a + b;

	@Test
	public void testCurrying() {
		int r1 = Curryable.curry(addFun1).apply(3).apply(2);
		assertEquals(5, r1);
		int r2 = Curryable.uncurry(addFun2).apply(3, 2);
		assertEquals(5, r2);

		Class<? extends String> clazz = "Hello World".getClass();
		System.out.println(clazz);
	}

	Function<File, Consumer<Consumer<PrintWriter>>> withPrintWriter = (file) -> (Consumer<PrintWriter> op) -> {
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(file);
			op.accept(writer);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != writer)
				writer.close();
		}
	};

	@Test
	public void testCurryingWrite() {
		List<String> sl = new ArrayList<>();
		System.out.println(sl);
		withPrintWriter.apply( //
				new File("/tmp/data.txt") //
		).accept( //
				(writer) -> writer.println(new java.util.Date()));

		Consumer<Consumer<PrintWriter>> dataFileWriter = withPrintWriter.apply(new File("/tmp/data.txt"));
		// ...........
		dataFileWriter.accept((writer) -> writer.println(new java.util.Date()));

	}

}
