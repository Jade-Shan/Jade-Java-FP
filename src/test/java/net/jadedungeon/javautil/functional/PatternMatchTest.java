package net.jadedungeon.javautil.functional;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class PatternMatchTest {

	@Test
	public void testInstanceOfExample() {
		Map<String, Object> data = new HashMap<>();
		data.put("key1", "aaa");
		data.put("key2", 111);
		data.put("key3", 222.0);
		for (Object o : data.values()) {
			if (o instanceof String s) {
				System.out.printf("Type is String : %s%n", s);
			} else if (o instanceof Integer n) {
				System.out.printf("Type is String : %d%n", n);
			} else if (o instanceof Double d) {
				System.out.printf("Type is String : %,.2f%n", d);
			}
		}
	}

	@Test
	// @SuppressWarnings("preview")
	public void testPatternMatch() {
		// javac --enable-preview and java --enable-preview
		Map<String, Object> data = new HashMap<>();
		data.put("key1", "aaa");
		data.put("key2", 111);
		data.put("key3", 222.0);
		data.put("key4", "Foo");
		data.put("key5", "Bar");
		for (Object o : data.values()) {
			switch (o) {
			// match null
				case String s  -> System.out.printf("Type is String : %s%n", s);
				case Integer n -> System.out.printf("Type is String : %d%n", n);
				case Double d  -> System.out.printf("Type is String : %,.2f%n", d);
				case null      -> System.out.printf("Oops%n");
				default        -> System.out.printf("Unknow Types");
			}
		}
	}
	
	sealed class Vehicle permits Car, Truck, Motorcycle {
	}

	final class Car extends Vehicle {
		public String vanNum;
	}

	final class Truck extends Vehicle {
		public String operationNum;
	}

	final class Motorcycle extends Vehicle {
		public String regNum;
	}


	// @SuppressWarnings("preview")
	public String getVehicleInfo(Vehicle v) {
		return switch (v) {
			case Car c        -> c.vanNum;
			case Truck t      -> t.operationNum;
			case Motorcycle m -> m.regNum;
			default           -> "";
		};
	}

}
