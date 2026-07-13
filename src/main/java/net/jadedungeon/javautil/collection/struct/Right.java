package net.jadedungeon.javautil.collection.struct;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Right —— Either 的右侧实现。
 * <p>
 * 表示 Either 的右分支值。按照 Haskell 惯例，Right 通常用于表示
 * 成功或正常路径的值（"Right is right"），但也可以用於任何需要区分两种类型的场景。
 * </p>
 *
 * @param <L> 左侧值的类型（未实际持有）
 * @param <R> 右侧值的类型
 */
public class Right<L, R> implements Either<L, R> {

	private final R value;

	/**
	 * 私有构造函数，请使用 {@link #of(Object)} 工厂方法创建实例。
	 *
	 * @param value 右侧值
	 */
	private Right(R value) {
		this.value = value;
	}

	/**
	 * 工厂方法：创建一个包含指定右侧值的 Right 实例。
	 *
	 * @param <L>   左侧值的类型
	 * @param <R>   右侧值的类型
	 * @param right 要包装的右侧值
	 * @return 新的 Right 实例
	 */
	public static <L, R> Right<L, R> of(R right) {
		return new Right<L, R>(right);
	}

	/**
	 * 判断是否为 Right。
	 *
	 * @return 始终返回 true
	 */
	public boolean isRight() {
		return true;
	}

	/**
	 * Right 不是 Left。
	 *
	 * @return 始终返回 false
	 */
	@Override
	public boolean isLeft() {
		return false;
	}

	/**
	 * 判断右侧值是否存在（非 null）。
	 *
	 * @return 如果内部值不为 null 返回 true
	 */
	@Override
	public boolean isRightPresent() {
		return null != value;
	}

	/**
	 * Right 不包含 Left 值。
	 *
	 * @return 始终返回 false
	 */
	@Override
	public boolean isLeftPresent() {
		return false;
	}

	/**
	 * Right 不包含左侧值，返回空 Optional。
	 *
	 * @return 始终返回空 Optional
	 */
	@Override
	public Optional<L> left() {
		// throw new NoSuchElementException("Right Type No Left Value");
		return Optional.empty();
	}

	/**
	 * 以 Optional 形式获取右侧值。
	 *
	 * @return 包含右侧值的 Optional（如果为 null 则为空）
	 */
	@Override
	public Optional<R> right() {
		return null == this.value ? Optional.empty() : Optional.of(value);
	}

	/**
	 * Right 不包含左侧值，始终返回默认值。
	 *
	 * @param defaultValue 默认值
	 * @return 默认值
	 */
	@Override
	public L leftValue(L defaultValue) {
		return defaultValue;
	}

	/**
	 * 获取右侧值，如果为 null 则返回默认值。
	 *
	 * @param defaultValue 默认值
	 * @return 右侧值或默认值
	 */
	@Override
	public R rightValue(R defaultValue) {
		return null != value ? value : defaultValue;
	}

	/**
	 * 对 Right 的内部值应用 rightFunc 进行变换（leftFunc 不会被调用）。
	 *
	 * @param <T>       新的左侧类型
	 * @param <K>       新的右侧类型
	 * @param leftFunc  不会被调用的左侧变换函数
	 * @param rightFunc 应用于 Right 值的变换函数
	 * @return 包装了变换结果的 Right
	 */
	@Override
	public <T, K> Either<T, K> map(Function<L, ? extends T> leftFunc, Function<R, ? extends K> rightFunc) {
		K newValue = rightFunc.apply(value);
		return new Right<T, K>(newValue);
	}

	/**
	 * 使用 rightFunc 谓词对 Right 值进行判断。
	 *
	 * @param leftFunc  不会被调用的左侧谓词
	 * @param rightFunc 应用于 Right 值的谓词
	 * @return 谓词判定结果
	 */
	@Override
	public boolean isMatch(Predicate<L> leftFunc, Predicate<R> rightFunc) {
		return rightFunc.test(value);
	}

}
