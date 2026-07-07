package net.jadedungeon.javautil.functional;

public interface Monoid<T> extends SemiGroup<T> {
	public T getZero();
}
