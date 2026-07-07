package net.jadedungeon.javautil.collection.struct;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

public class Left<L, R> implements Either<L, R> {

	private final L value;

	protected Left(L value) {
		this.value = value;
	}

	public static <L, R> Left<L, R> of(L left) {
		return new Left<L, R>(left);
	}

	public boolean isRight() {
		return false;
	}

	@Override
	public boolean isLeft() {
		return true;
	}

	@Override
	public boolean isRightPresent() {
		return false;
	}

	@Override
	public boolean isLeftPresent() {
		return null != value;
	}

	@Override
	public Optional<L> left() {
		return null == this.value ? Optional.empty() : Optional.of(value);
	}

	@Override
	public Optional<R> right() {
		// throw new NoSuchElementException("Left Type No Right Value");
		return Optional.empty();
	}

	@Override
	public L leftValue(L defaultValue) {
		return null != value ? value : defaultValue;
	}

	@Override
	public R rightValue(R defaultValue) {
		return defaultValue;
	}

	@Override
	public <T, K> Either<T, K> map(Function<L, ? extends T> leftFunc, Function<R, ? extends K> rightFunc) {
		T newValue = leftFunc.apply(value);
		return new Left<T, K>(newValue);
	}

	@Override
	public boolean isMatch(Predicate<L> leftFunc, Predicate<R> rightFunc) {
		return leftFunc.test(value);
	}
}
