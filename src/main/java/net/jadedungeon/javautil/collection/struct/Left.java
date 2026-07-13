package net.jadedungeon.javautil.collection.struct;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Left —— Either 的左侧实现。
 * <p>
 * 表示 Either 的左分支值。按照 Haskell 惯例，Left 通常用于表示
 * 错误或异常路径（"Left is wrong"），但也可以用於任何需要区分两种类型的场景。
 * </p>
 *
 * @param <L> 左侧值的类型
 * @param <R> 右侧值的类型（未实际持有）
 */
public class Left<L, R> implements Either<L, R> {

	private final L value;

	/**
	 * 受保护的构造函数，请使用 {@link #of(Object)} 工厂方法创建实例。
	 *
	 * @param value 左侧值
	 */
	protected Left(L value) {
		this.value = value;
	}

	/**
	 * 工厂方法：创建一个包含指定左侧值的 Left 实例。
	 *
	 * @param <L>   左侧值的类型
	 * @param <R>   右侧值的类型
	 * @param left  要包装的左侧值
	 * @return 新的 Left 实例
	 */
	public static <L, R> Left<L, R> of(L left) {
		return new Left<L, R>(left);
	}

	/**
	 * Left 不是 Right。
	 *
	 * @return 始终返回 false
	 */
	public boolean isRight() {
		return false;
	}

	/**
	 * 判断是否为 Left。
	 *
	 * @return 始终返回 true
	 */
	@Override
	public boolean isLeft() {
		return true;
	}

	/**
	 * Left 不包含 Right 值。
	 *
	 * @return 始终返回 false
	 */
	@Override
	public boolean isRightPresent() {
		return false;
	}

	/**
	 * 判断左侧值是否存在（非 null）。
	 *
	 * @return 如果内部值不为 null 返回 true
	 */
	@Override
	public boolean isLeftPresent() {
		return null != value;
	}

	/**
	 * 以 Optional 形式获取左侧值。
	 *
	 * @return 包含左侧值的 Optional（如果为 null 则为空）
	 */
	@Override
	public Optional<L> left() {
		return null == this.value ? Optional.empty() : Optional.of(value);
	}

	/**
	 * Left 不包含右侧值，返回空 Optional。
	 *
	 * @return 始终返回空 Optional
	 */
	@Override
	public Optional<R> right() {
		// throw new NoSuchElementException("Left Type No Right Value");
		return Optional.empty();
	}

	/**
	 * 获取左侧值，如果为 null 则返回默认值。
	 *
	 * @param defaultValue 默认值
	 * @return 左侧值或默认值
	 */
	@Override
	public L leftValue(L defaultValue) {
		return null != value ? value : defaultValue;
	}

	/**
	 * Left 不包含右侧值，始终返回默认值。
	 *
	 * @param defaultValue 默认值
	 * @return 默认值
	 */
	@Override
	public R rightValue(R defaultValue) {
		return defaultValue;
	}

	/**
	 * 对 Left 的内部值应用 leftFunc 进行变换（rightFunc 不会被调用）。
	 *
	 * @param <T>       新的左侧类型
	 * @param <K>       新的右侧类型
	 * @param leftFunc  应用于 Left 值的变换函数
	 * @param rightFunc 不会被调用的右侧变换函数
	 * @return 包装了变换结果的 Left
	 */
	@Override
	public <T, K> Either<T, K> map(Function<L, ? extends T> leftFunc, Function<R, ? extends K> rightFunc) {
		T newValue = leftFunc.apply(value);
		return new Left<T, K>(newValue);
	}

	/**
	 * 使用 leftFunc 谓词对 Left 值进行判断。
	 *
	 * @param leftFunc  应用于 Left 值的谓词
	 * @param rightFunc 不会被调用的右侧谓词
	 * @return 谓词判定结果
	 */
	@Override
	public boolean isMatch(Predicate<L> leftFunc, Predicate<R> rightFunc) {
		return leftFunc.test(value);
	}
}
