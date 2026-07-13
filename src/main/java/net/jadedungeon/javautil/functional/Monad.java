package net.jadedungeon.javautil.functional;

import java.util.function.Function;

/**
 * 单子（Monad）—— 函数式编程中最核心的类型类。
 * <p>
 * Monad 在 {@link Functor} 的基础上增加了 {@link #flatMap(Function)} 方法，
 * 使得可以将多个 Monad 操作串联起来，避免嵌套的容器类型（如 {@code Option<Option<T>>}）。
 * 这与 Haskell 中的 Monad typeclass 语义一致。
 * </p>
 *
 * <p>Monad 定律：</p>
 * <ul>
 *   <li><b>左单位元律</b>：{@code Monad.of(x).flatMap(f)} 等价于 {@code f.apply(x)}</li>
 *   <li><b>右单位元律</b>：{@code m.flatMap(Monad::of)} 等价于 {@code m}</li>
 *   <li><b>结合律</b>：{@code m.flatMap(f).flatMap(g)} 等价于 {@code m.flatMap(x -> f.apply(x).flatMap(g))}</li>
 * </ul>
 *
 * <p>本库中的主要实现：</p>
 * <ul>
 *   <li>{@link net.jadedungeon.javautil.collection.solitary.Option} —— 可空值容器</li>
 *   <li>{@link net.jadedungeon.javautil.collection.multi.Seq} —— 不可变持久化链表</li>
 *   <li>{@link net.jadedungeon.javautil.collection.solitary.Identity} —— Identity Monad</li>
 *   <li>{@link net.jadedungeon.javautil.collection.solitary.Promise} —— 值包装器</li>
 * </ul>
 *
 * @param <T> 容器内元素的类型
 */
public interface Monad<T> extends Functor<T> {

	/**
	 * 将容器内的值通过 mapper 函数进行变换，返回包装了新值的新 Monad。
	 *
	 * @param <R>    变换后的结果类型
	 * @param mapper 对内部值进行变换的函数
	 * @return 包装了变换后值的新 Monad
	 */
	<R> Monad<? extends R> map(Function<? super T, ? extends R> mapper);

	/**
	 * 将容器内的值通过 mapper 函数进行变换，并将结果扁平化。
	 * <p>
	 * 与 {@link #map(Function)} 不同的是，mapper 函数的返回值本身也是一个 Monad，
	 * flatMap 会将其"展平"而不是产生嵌套的 Monad。
	 * </p>
	 *
	 * @param <R>    变换后的元素类型
	 * @param <M>    返回的 Monad 具体类型
	 * @param mapper 将内部值映射到另一个 Monad 的函数
	 * @return 扁平化后的新 Monad
	 */
	<R, M extends Monad<? extends R>> M flatMap(Function<? super T, M> mapper);

}
