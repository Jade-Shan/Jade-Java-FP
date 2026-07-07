package net.jadedungeon.javautil.collection.multi;

import java.util.Iterator;
import java.util.function.Function;

import net.jadedungeon.javautil.functional.Monad;
import net.jadedungeon.javautil.tco.CallStack;

public abstract class Seq<T> implements Monad<T> {

	public abstract boolean isEmpty();

	public abstract int size();

	public abstract T head();

	public abstract Seq<T> tail();

	protected abstract T eval();

	@SuppressWarnings("unchecked")
	public static <T> Nil<T> empty() {
		return (Nil<T>) Nil.NIL;
	}

	public static <T> Seq<T> of(Iterable<T> items) {
		return ofAcc(items.iterator(), empty());
	}

	private static <T> Seq<T> ofAcc(Iterator<T> ite, Seq<T> list) {
		if (ite.hasNext()) {
			T t = ite.next();
			return ofAcc(ite, list.attach(t));
		} else {
			return list;
		}
	}

//	private static <T, I extends Monad<T>> Seq<T> //
//			unpackInnerElems(Seq<I> from, Seq<T> to) //
//	{
//		if (from.isEmpty()) {
//			return to;
//		} else {
//			T t = from.head().eval();
//			return unpackInnerElems(from.tail(), to.attach(t));
//		}
//	}
//
//	public static <T, I extends Monad<T>, P extends Monad<Seq<T>>> P //
//			merge(Seq<I> from, Function<Seq<T>, P> creater//
//	) {
//		Seq<T> inner = unpackInnerElems(from, empty());
//		return creater.apply(inner);
//	}

	public Seq<T> attach(T element) {
		return null == element ? this : new Cons<T>(element, this);
	}

	public Seq<T> concat(Seq<T> seq) {
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <R> Seq<R> map(Function<? super T, ? extends R> mapper) {
		return (Seq<R>) mapAcc(this, empty(), mapper).eval();
	}

	private <R> CallStack<Seq<R>> mapAcc( //
			Seq<T> lst, Seq<R> tmp, Function<? super T, ? extends R> mapper) //
	{
		if (lst.isEmpty()) {
			return CallStack.of(tmp);
		} else {
			return CallStack.of(() -> {
				R e = mapper.apply(lst.head());
				return mapAcc(lst.tail(), tmp.attach(e), mapper);
			});
		}
	}

	@Override
//	 public <R, M extends Seq<? extends R>> M flatMap(Function<? super T, M> mapper) {
	public <R, M extends Monad<? extends R>> M flatMap(Function<? super T, M> mapper) {
		@SuppressWarnings("unchecked")
		M res = (M) empty(); // MList<R> -> ? extends Monad<? extends R>
		return flatMapAcc(this, res, mapper).eval();
	}

//	private <R, M extends Monad<? extends R>> M flatMapAcc( //
//			Seq<T> lst, M tmp, Function<? super T, M> mapper) //
//	{
//		if (lst.isEmpty()) {
//			return tmp;
//		} else {
//			@SuppressWarnings("unchecked")
//			R e = ((Seq<R>) mapper.apply(lst.head())).eval();
//			@SuppressWarnings("unchecked")
//			Seq<R> tr = (Seq<R>) tmp; // Monad -> MList
//			@SuppressWarnings("unchecked")
//			M tmp2 = (M) tr.attach(e); // MList.attach(element) ; MList -> Monad
//			return flatMapAcc(lst.tail(), tmp2, mapper);
//		}
//	}
	private <R, M extends Monad<? extends R>> CallStack<M> flatMapAcc( //
			Seq<T> lst, M tmp, Function<? super T, M> mapper) //
	{
		if (lst.isEmpty()) {
			return CallStack.of(tmp);
		} else {
			return CallStack.of(() -> {
				@SuppressWarnings("unchecked")
				R e = ((Seq<R>) mapper.apply(lst.head())).eval();
				@SuppressWarnings("unchecked")
				Seq<R> tr = (Seq<R>) tmp; // Monad -> MList
				@SuppressWarnings("unchecked")
				M tmp2 = (M) tr.attach(e); // MList.attach(element) ; MList -> Monad
				return flatMapAcc(lst.tail(), tmp2, mapper);
			});
		}
	}

//	@Override
//	public Seq<T> filter(Predicate<T> pdt) {
//		return filterAcc(this, empty(), pdt);
//	}
//
//	public Seq<T> filterAcc(Seq<T> src, Seq<T> tgt, Predicate<T> pdt) {
//		if (src.isEmpty()) {
//			return tgt;
//		} else if (pdt.test(src.head())) {
//			return filterAcc(src.tail(), tgt.attach(src.head()), pdt);
//		} else {
//			return filterAcc(src.tail(), tgt, pdt);
//		}
//	}

}
