package net.jadedungeon.javautil.collection.struct;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Either —— 可区分联合类型（Disjoint Union / Tagged Union）。
 * <p>
 * Either 表示一个值只能是两种类型之一：{@link Left} 或 {@link Right}。
 * 这与 Haskell 中的 Either 类型语义一致，通常用于：
 * </p>
 * <ul>
 *   <li>表示可能失败的计算结果（Right 为成功值，Left 为错误信息）</li>
 *   <li>表示两种不同类型的可选值</li>
 * </ul>
 *
 * <p>提供了 {@link #map(Function, Function)} 操作对左右两侧分别变换，
 * 以及 {@link #isMatch(Predicate, Predicate)} 进行条件判断。</p>
 *
 * @param <L> 左侧值的类型
 * @param <R> 右侧值的类型
 */
public interface Either<L, R> //
//implements Monad<R> //
{

	/**
	 * 判断是否为 Right 实例。
	 *
	 * @return 如果是 Right 返回 true
	 */
	public boolean isRight();

	/**
	 * 判断是否为 Left 实例。
	 *
	 * @return 如果是 Left 返回 true
	 */
	public boolean isLeft();

	/**
	 * 判断是否为非空的 Right 实例。
	 *
	 * @return 如果是 Right 且内部值不为 null，返回 true
	 */
	public boolean isRightPresent();

	/**
	 * 判断是否为非空的 Left 实例。
	 *
	 * @return 如果是 Left 且内部值不为 null，返回 true
	 */
	public boolean isLeftPresent();

	/**
	 * 尝试以 Optional 形式获取左侧值。
	 *
	 * @return 包含左侧值的 Optional（Right 时为空）
	 */
	public Optional<L> left();

	/**
	 * 尝试以 Optional 形式获取右侧值。
	 *
	 * @return 包含右侧值的 Optional（Left 时为空）
	 */
	public Optional<R> right();

	/**
	 * 获取左侧值，如果不存在则返回默认值。
	 *
	 * @param defaultValue 当左侧值不存在时的默认值
	 * @return 左侧值或默认值
	 */
	public L leftValue(L defaultValue);

	/**
	 * 获取右侧值，如果不存在则返回默认值。
	 *
	 * @param defaultValue 当右侧值不存在时的默认值
	 * @return 右侧值或默认值
	 */
	public R rightValue(R defaultValue);

	/**
	 * 对 Either 的两侧分别应用不同的映射函数（Bimap）。
	 * <p>
	 * 如果是 Left，对内部值应用 leftFunc 并包装为新的 Left；
	 * 如果是 Right，对内部值应用 rightFunc 并包装为新的 Right。
	 * </p>
	 *
	 * @param <T>       新的左侧类型
	 * @param <K>       新的右侧类型
	 * @param leftFunc  应用于 Left 值的变换函数
	 * @param rightFunc 应用于 Right 值的变换函数
	 * @return 变换后的新 Either
	 */
	public <T, K> Either<T, K> map(Function<L, ? extends T> leftFunc, Function<R, ? extends K> rightFunc);

	/**
	 * 同时对左右两侧应用谓词进行匹配判断。
	 * <p>
	 * 如果是 Left 则用 leftFunc 判定，如果是 Right 则用 rightFunc 判定。
	 * </p>
	 *
	 * @param leftFunc  应用于 Left 值的谓词
	 * @param rightFunc 应用于 Right 值的谓词
	 * @return 谓词匹配的结果
	 */
	public boolean isMatch(Predicate<L> leftFunc, Predicate<R> rightFunc);

}
