package net.jadedungeon.javautil.functional;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;

import org.junit.Test;

import net.jadedungeon.javautil.collection.multi.Seq;
import net.jadedungeon.javautil.collection.solitary.Identity;
import net.jadedungeon.javautil.collection.solitary.Option;
import net.jadedungeon.javautil.collection.solitary.Promise;
import net.jadedungeon.javautil.test.models.Address;
import net.jadedungeon.javautil.test.models.Customer;

public class FunctorTest {
	private List<Customer> customers = new ArrayList<Customer>();

	public FunctorTest() {
		customers.add(new Customer("U001", "Teo", //
				new Address("Beijin", "Test Street Ad", "21B", "West", "301")));
		customers.add(new Customer("U002", "Jade", //
				new Address("Shanghai", "Test Street Ad", "22B", "West", "302")));
		customers.add(new Customer("U003", "Adda", //
				new Address("Guangzhou", "Test Street Ad", "23B", "West", "303")));
	}
	
	public static <R, T, U> Optional<R> liftM2(Optional<T> a, Optional<U> b, BiFunction<T, U, R> mapper) {
		return a.flatMap((T t) -> b.map((U u) -> mapper.apply(t, u)));
	}
	
	@FunctionalInterface
	interface Function3 <R, T, U, V> {
		public R apply(T t, U u, V v);
	}
	
	public static <R, T, U, V> Optional<R> liftM3(Optional<T> a, Optional<U> b, Optional<V> c,  Function3<R, T, U, V> mapper) {
		return a.flatMap((T t) -> b.flatMap((U u) -> c.map((V v) -> mapper.apply(t, u, v))));
	}

	@Test
	public void testMapIdentity() {
		Identity<String> idString = Identity.of("abcdef");
		Identity<Integer> idInt = idString.map(String::length);
		assertEquals("{\"type\":\"Identity\",\"value\":\"6\"}", idInt.toString());

		Identity<String> idStreet = Identity.of(customers.get(0)) //
				.map(Customer::getAddress) //
				.map(Address::getStreet) //
				.map((String s) -> s.substring(0, 3)) //
				.map(String::toLowerCase); //
		assertEquals("{\"type\":\"Identity\",\"value\":\"tes\"}", idStreet.toString());

		String lowStreet = customers.get(0) //
				.getAddress() //
				.getStreet() //
				.substring(0, 3) //
				.toLowerCase();//
		assertEquals("tes", lowStreet);

		Optional<Integer> year = Optional.of(2022);
		Optional<Month> month = Optional.of(Month.AUGUST);
		Optional<Integer> dayOfMonth = Optional.of(9);
		Optional<LocalDate> day = month.flatMap((Month m) -> dayOfMonth.map((Integer d) -> LocalDate.of(2022, m, d)));
		System.out.println(day);
		Optional<LocalDate> day2 = year.flatMap((y) -> month.flatMap((Month m) -> dayOfMonth.map((Integer d) -> LocalDate.of(y, m, d))));
		System.out.println(day2);
	}

	@Test
	public void testMapPromise() {
		Promise<Customer> customer = Promise.of(customers.get(0));

		Promise<String> pmStreet = customer.map(Customer::getAddress) //
				.map(Address::getStreet) //
				.map((String s) -> s.substring(0, 3)) //
				.map(String::toLowerCase);//
//				.map(String::getBytes);
		assertEquals("{\"type\":\"Promise\",\"value\":\"tes\"}", pmStreet.toString());
	}

	@Test
	public void testMapOption() {
		Option<String> fstr = Option.of("42");

		Option<Integer> r1 = fstr.map(s -> Integer.parseInt(s))//
				.map(i -> i + 1) //
				.map(i -> i + 2) //
				.map(i -> i + 3);
		assertEquals("{\"type\":\"Some\",\"value\":\"48\"}", r1.toString());

		Option<Option<Integer>> r2 = Option.of("abc")//
				.map(s -> {
					try {
						return Option.of(Integer.parseInt(s));
					} catch (NumberFormatException e) {
						return Option.empty();
					}
				})
//		// .map(i -> i + 3) // The operator + is undefined for
//		// the argument type(s) FOptional<capture#29-of ? extends Object>
		;
		assertEquals("{\"type\":\"Some\",\"value\":\"{\"type\":\"None\"}\"}", r2.toString());
	}

	@Test
	public void testMapSeq() {
		Seq<Customer> seqCustomers = Seq.of(customers);

		Seq<String> seqStreets = seqCustomers //
				.map(Customer::getAddress) //
				.map(Address::getStreet);
		assertEquals("List [Test Street Ad, Test Street Ad, Test Street Ad]", seqStreets.toString());
	}

}
