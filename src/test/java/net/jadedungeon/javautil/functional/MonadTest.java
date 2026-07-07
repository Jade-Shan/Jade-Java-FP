package net.jadedungeon.javautil.functional;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import net.jadedungeon.javautil.collection.solitary.Option;
import net.jadedungeon.javautil.collection.solitary.Some;
import net.jadedungeon.javautil.test.models.Address;
import net.jadedungeon.javautil.test.models.Customer;

public class MonadTest {
	private List<Customer> customers = new ArrayList<Customer>();

	public MonadTest() {
		customers.add(new Customer("U001", "Teo", //
				new Address("Beijin", "Test Street Ad", "21B", "West", "301")));
		customers.add(new Customer("U002", "Jade", //
				new Address("Shanghai", "Test Street Ad", "22B", "West", "302")));
		customers.add(new Customer("U003", "Adda", //
				new Address("Guangzhou", "Test Street Ad", "23B", "West", "303")));
	}

	@Test
	public void testMapFlatMap() {
		LocalDate today = LocalDate.of(2021, 10, 21);
		Option<Integer> year = Some.of(today.getYear());
		Option<Month> month = Some.of(today.getMonth());
		Option<Integer> dayOfMonth = Some.of(today.getDayOfMonth());

		Option<LocalDate> md1 = month.flatMap(//
				m -> dayOfMonth.map(//
						d -> LocalDate.of(2021, m, d)));
		assertEquals("{\"type\":\"Some\",\"value\":\"2021-10-21\"}", md1.toString());

		Option<LocalDate> md2 = MonadHelper.liftM2(month, dayOfMonth, //
				(m, d) -> LocalDate.of(2021, m, d));
		assertEquals("{\"type\":\"Some\",\"value\":\"2021-10-21\"}", md2.toString());

		Option<LocalDate> ymd1 = year.flatMap(//
				y -> month.flatMap(//
						m -> dayOfMonth.map(//
								d -> LocalDate.of(y, m, d))));
		assertEquals("{\"type\":\"Some\",\"value\":\"2021-10-21\"}", ymd1.toString());
		Monad<Object> ymd2 = MonadHelper.liftM3(year, month, dayOfMonth, //
				(y, m, d) -> LocalDate.of(y, m, d));
		assertEquals("{\"type\":\"Some\",\"value\":\"2021-10-21\"}", ymd2.toString());

	}

	@Test
	public void testFlatMapOptional() {
		Option<String> fstr = Option.of("42");

		Option<String> r2 = fstr.flatMap(s -> {
			try {
				return Option.of(Integer.parseInt(s));
			} catch (NumberFormatException e) {
				return Option.empty();
			}
		})//
				.map(i -> i + 1) //
				.map(i -> i + 2) //
				.map(i -> i + 3) //
				.flatMap((i) -> {
					return Option.of("" + i);
				});
		assertEquals("{\"type\":\"Some\",\"value\":\"48\"}", r2.toString());
	}
//
//	@Test
//	public void testFlatMapList() {
//		Seq<Customer> ll = Seq.of(this.customers);
//
//		Seq<String> res1 = ll//
//				.map(Customer::getAddress).map(Address::getStreet);
//		assertNotNull(res1); // "Test Street Ad" * 3
//		assertEquals(3, res1.size());
//		assertEquals("Test Street Ad", res1.head());
//		assertEquals("Test Street Ad", res1.tail().head());
//		assertEquals("Test Street Ad", res1.tail().tail().head());
//
//		Seq<String> res2 = ll.flatMap(c -> {
//			return Seq.<Address>empty().attach(c.getAddress());
//		}).flatMap(a -> {
//			return Seq.<String>empty().attach(a.getStreet());
//		});
//		assertNotNull(res2);
//		assertEquals(3, res2.size());
//		assertEquals("Test Street Ad", res2.head());
//		assertEquals("Test Street Ad", res2.tail().head());
//		assertEquals("Test Street Ad", res2.tail().tail().head());
//	}
//
//	@Test
//	public void testFilter() {
//		Seq<Customer> ll = Seq.of(this.customers);
//		Seq<Customer> r1 = ll.filter(c -> c.getName().equals("Adda"));
//		assertNotNull(r1);
//		assertEquals(1, r1.size());
//		assertEquals("Adda", r1.head().getName());
//		Seq<Customer> r2 = ll.filter(c -> c.getName().equals("Jade"));
//		assertNotNull(r2);
//		assertEquals(1, r2.size());
//		assertEquals("Jade", r2.head().getName());
//		Seq<Customer> r3 = r2.filter(c -> c.getName().equals("Teo"));
//		assertNotNull(r3);
//		assertEquals(0, r3.size());
//	}
//
//	@Test
//	public void testMerge() {
//		Seq<Identity<Customer>> ll = //
//				Seq.<Identity<Customer>>empty()//
//						.attach(Identity.of(customers.get(0))) //
//						.attach(Identity.of(customers.get(1))) //
//						.attach(Identity.of(customers.get(2)));
//
//		Identity<Seq<Customer>> r1 = Seq.merge(ll, (lst) -> {
//			return Identity.of(lst);
//		});
//		assertNotNull(r1);
//	}

}
