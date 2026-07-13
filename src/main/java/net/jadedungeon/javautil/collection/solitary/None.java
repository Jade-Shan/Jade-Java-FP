package net.jadedungeon.javautil.collection.solitary;

import java.util.NoSuchElementException;
import java.util.function.Function;

import net.jadedungeon.javautil.functional.Monad;

/**
 * None —— Option 的"无值"状态。
 * <p>
 * 采用单例模式：所有 None 实例共享同一个 {@link #NONE} 对象，
 * 通过 {@code Option.empty()} 或直接引用 {@code None.NONE} 来获取。
 * </p>
 * <p>
 * 调用 {@link #eval()} 将抛出 {@link NoSuchElementException}，
 * 这与 Haskell 中尝试对 Nothing 求值的语义一致。
 * </p>
 *
 * @param <T> 元素类型（未实际持有值）
 */
public class None<T> extends Option<T> {
	/** None 的全局单例 */
	static final None<?> NONE = new None<>();

	/**
	 * 受保护的构造函数，外部通过 {@link #NONE} 单例获取实例。
	 */
	protected None() {
	}

	/**
	 * 对于 None，map 操作始终返回 None。
	 *
	 * @param <R>    变换后的结果类型
	 * @param mapper 变换函数（不会被实际调用）
	 * @return None 单例
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <R> None<R> map(Function<? super T, ? extends R> mapper) {
		return (None<R>) NONE;
	}

	/**
	 * 对于 None，flatMap 操作始终返回 None。
	 *
	 * @param <R>    变换后的元素类型
	 * @param <M>    返回的 Monad 具体类型
	 * @param mapper 变换函数（不会被实际调用）
	 * @return None 单例（强制转型为调用方期望的 Monad 类型）
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <R, M extends Monad<? extends R>> M flatMap(Function<? super T, M> mapper) {
		return (M) NONE;
	}

	/**
	 * None 永远为空。
	 *
	 * @return 始终返回 true
	 */
	@Override
	public boolean isEmpty() {
		return true;
	}

	/**
	 * 尝试从 None 中获取值，始终抛出异常。
	 *
	 * @return 永远不会正常返回
	 * @throws NoSuchElementException 因为 None 不含任何值
	 */
	@Override
	public T eval() {
		throw new NoSuchElementException("Empty Option type None");
	}

	@Override
	public String toString() {
		return String.format("{\"type\":\"None\"}");
	}
}
