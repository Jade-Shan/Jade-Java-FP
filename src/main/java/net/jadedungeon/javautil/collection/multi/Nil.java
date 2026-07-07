package net.jadedungeon.javautil.collection.multi;

import java.util.NoSuchElementException;

public class Nil<T> extends Seq<T> {

	public static final Nil<?> NIL = new Nil<>();

	private Nil() {
	}

	@Override
	public boolean isEmpty() {
		return true;
	}

	@Override
	public int size() {
		return 0;
	}

	@Override
	public T eval() {
		throw new NoSuchElementException("Empty Option type None");
	}

	@Override
	public T head() {
		throw new NoSuchElementException("head of empty list");
	}

	@Override
	public Seq<T> tail() {
		throw new UnsupportedOperationException("tail of empty list");
	}

	@Override
	public String toString() {
		return "Nil";
	}

}
