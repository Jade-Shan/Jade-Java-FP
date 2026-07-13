package net.jadedungeon.javautil.collection.solitary;

import java.util.function.Function;

import net.jadedungeon.javautil.functional.Monad;

/**
 * Option 类型 —— 显式的可选值容器（Maybe Monad）。
 * <p>
 * Option 表示一个可能存在也可能不存在的值，是处理 null 安全的方式。
 * 它有两个子类：
 * </p>
 * <ul>
 *   <li>{@link Some} —— 包含一个非 null 值</li>
 *   <li>{@link None} —— 表示无值（单例模式，等价于 null 的安全替代）</li>
 * </ul>
 *
 * <p>使用示例：</p>
 * <pre>{@code
 * Option<String> opt = Option.of(maybeNullString);
 * opt.map(String::toUpperCase)
 *    .flatMap(s -> Option.of(processString(s)));
 * }</pre>
 *
 * <p>这与 Haskell 中的 Maybe 类型、Java 中的 {@link java.util.Optional} 语义相似，
 * 但 Option 同时也是一个 Monad，可以与库中其他 Monad 类型无缝组合。</p>
 *
 * @param <T> 容器内元素的类型
 */
public abstract class Option<T> implements Monad<T> {

	/**
	 * 判断此 Option 是否为空（即是否为 None）。
	 *
	 * @return 如果为 None 返回 true，否则返回 false
	 */
	abstract public boolean isEmpty();

	/**
	 * 获取 Option 中包装的值。
	 * <p>
	 * 对于 None 实例，此方法会抛出异常；
	 * 对于 Some 实例，返回其内部持有的值。
	 * </p>
	 *
	 * @return 内部持有的值
	 * @throws java.util.NoSuchElementException 如果此 Option 为 None
	 */
	abstract protected T eval();

	/**
	 * 创建一个空的 Option（None 单例）。
	 *
	 * @param <T> 元素类型
	 * @return 空的 Option 实例（None 单例）
	 */
	@SuppressWarnings("unchecked")
	public static <T> None<T> empty() {
		return (None<T>) None.NONE;
	}

	/**
	 * 根据给定值创建 Option。
	 * <p>
	 * 如果值为 null，返回 None 单例；否则返回包装该值的 Some。
	 * </p>
	 *
	 * @param <T>         元素类型
	 * @param valueOrNull 可能为 null 的值
	 * @return 如果值为 null 则为 None，否则为 Some
	 */
	public static <T> Option<T> of(T valueOrNull) {
		if (null == valueOrNull) {
			return empty();
		} else {
			return Some.<T>of(valueOrNull);
		}
	}

	/**
	 * 对内部值应用 mapper 函数进行变换。对于 None 返回 None。
	 *
	 * @param <R>    变换后的结果类型
	 * @param mapper 对内部值进行变换的函数
	 * @return 如果当前为 Some，返回包装变换结果的 Some；如果为 None，返回 None
	 */
	abstract public <R> Option<R> map(Function<? super T, ? extends R> mapper);

	/**
	 * 对内部值应用 mapper 函数，并将结果扁平化。对于 None 返回 None。
	 *
	 * @param <R>    变换后的元素类型
	 * @param <M>    返回的 Monad 具体类型
	 * @param mapper 将内部值映射到另一个 Monad 的函数
	 * @return 如果当前为 Some，返回 mapper 的结果；如果为 None，返回 None
	 */
	abstract public <R, M extends Monad<? extends R>> M flatMap(Function<? super T, M> mapper);


}
