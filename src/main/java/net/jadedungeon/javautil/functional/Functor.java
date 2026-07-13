package net.jadedungeon.javautil.functional;

import java.util.function.Function;

/**
 * 函子（Functor）—— 函数式编程的核心抽象之一。
 * <p>
 * Functor 表示一个可映射的容器类型，它持有一个值或一组值，
 * 并提供一个 {@link #map(Function)} 方法，允许在不取出内部值的情况下
 * 对其进行变换。这与 Haskell 中的 Functor typeclass 语义相同。
 * </p>
 *
 * <p>本库中的主要实现：</p>
 * <ul>
 *   <li>{@link Monad} —— 扩展了 flatMap 能力的 Functor</li>
 *   <li>{@link net.jadedungeon.javautil.collection.solitary.Option} —— Maybe/Optional 容器</li>
 *   <li>{@link net.jadedungeon.javautil.collection.multi.Seq} —— 不可变链表</li>
 * </ul>
 *
 * @param <T> 容器内元素的类型
 */
public interface Functor<T> {

	/**
	 * 将容器内的值通过 mapper 函数进行变换，返回包装了新值的新容器。
	 * <p>
	 * 此方法遵循 Functor 定律：
	 * </p>
	 * <ul>
	 *   <li><b>恒等律</b>：{@code map(Function.identity())} 返回等价的容器</li>
	 *   <li><b>组合律</b>：{@code map(f).map(g)} 等价于 {@code map(f.andThen(g))}</li>
	 * </ul>
	 *
	 * @param <R>    变换后的结果类型
	 * @param mapper 对内部值进行变换的函数
	 * @return 包装了变换后值的新 Functor
	 */
	public <R> Functor<? extends R> map(Function<? super T, ? extends R> mapper);

//	public T unPack();
}
