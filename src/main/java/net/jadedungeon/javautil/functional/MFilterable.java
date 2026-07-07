package net.jadedungeon.javautil.functional;

import java.util.function.Predicate;

public interface MFilterable<T> extends Monad<T> {
	
	public MFilterable<T> filter(Predicate<T> pdt);

}
