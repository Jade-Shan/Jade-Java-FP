package net.jadedungeon.javautil.collection.struct;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

public class Right<L, R> implements Either<L, R> {

	private final R value;

	private Right(R value) {
		this.value = value;
	}

	public static <L, R> Right<L, R> of(R right) {
		return new Right<L, R>(right);
	}

	public boolean isRight() {
		return true;
	}

	@Override
	public boolean isLeft() {
		return false;
	}

	@Override
	public boolean isRightPresent() {
		return null != value;
	}

	@Override
	public boolean isLeftPresent() {
		return false;
	}

	@Override
	public Optional<L> left() {
		// throw new NoSuchElementException("Right Type No Left Value");
		return Optional.empty();
	}

	@Override
	public Optional<R> right() {
		return null == this.value ? Optional.empty() : Optional.of(value);
	}

	@Override
	public L leftValue(L defaultValue) {
		return defaultValue;
	}

	@Override
	public R rightValue(R defaultValue) {
		return null != value ? value : defaultValue;
	}

	@Override
	public <T, K> Either<T, K> map(Function<L, ? extends T> leftFunc, Function<R, ? extends K> rightFunc) {
		K newValue = rightFunc.apply(value);
		return new Right<T, K>(newValue);
	}

	@Override
	public boolean isMatch(Predicate<L> leftFunc, Predicate<R> rightFunc) {
		return rightFunc.test(value);
	}

}
