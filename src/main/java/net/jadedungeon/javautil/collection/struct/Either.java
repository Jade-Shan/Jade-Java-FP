package net.jadedungeon.javautil.collection.struct;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

public interface Either<L, R> //
//implements Monad<R> //
{

	public boolean isRight();

	public boolean isLeft();

	public boolean isRightPresent();

	public boolean isLeftPresent();

	public Optional<L> left();

	public Optional<R> right();

	public L leftValue(L defaultValue);

	public R rightValue(R defaultValue);

	public <T, K> Either<T, K> map(Function<L, ? extends T> leftFunc, Function<R, ? extends K> rightFunc);

	public boolean isMatch(Predicate<L> leftFunc, Predicate<R> rightFunc);

}
