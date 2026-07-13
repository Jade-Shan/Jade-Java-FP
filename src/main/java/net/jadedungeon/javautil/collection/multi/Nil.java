package net.jadedungeon.javautil.collection.multi;

import java.util.NoSuchElementException;

/**
 * Nil —— 空 Seq 节点。
 * <p>
 * 采用单例模式：所有空 Seq 共享同一个 {@link #NIL} 对象，
 * 通过 {@code Seq.empty()} 或直接引用 {@code Nil.NIL} 来获取。
 * </p>
 * <p>
 * 对空列表调用 {@link #head()} 或 {@link #eval()} 将抛出异常，
 * 调用 {@link #tail()} 将抛出 {@link UnsupportedOperationException}。
 * </p>
 *
 * @param <T> 元素类型（未实际持有元素）
 */
public class Nil<T> extends Seq<T> {

	/** Nil 的全局单例 */
	public static final Nil<?> NIL = new Nil<>();

	/**
	 * 私有构造函数，外部通过 {@link #NIL} 单例获取实例。
	 */
	private Nil() {
	}

	/**
	 * Nil 永远为空。
	 *
	 * @return 始终返回 true
	 */
	@Override
	public boolean isEmpty() {
		return true;
	}

	/**
	 * 空列表的长度为 0。
	 *
	 * @return 始终返回 0
	 */
	@Override
	public int size() {
		return 0;
	}

	/**
	 * 尝试从空列表中获取值，始终抛出异常。
	 *
	 * @return 永远不会正常返回
	 * @throws NoSuchElementException 因为 Nil 不含任何元素
	 */
	@Override
	public T eval() {
		throw new NoSuchElementException("Empty Option type None");
	}

	/**
	 * 尝试获取空列表的 head，始终抛出异常。
	 *
	 * @return 永远不会正常返回
	 * @throws NoSuchElementException 因为空列表没有 head
	 */
	@Override
	public T head() {
		throw new NoSuchElementException("head of empty list");
	}

	/**
	 * 尝试获取空列表的 tail，始终抛出异常。
	 *
	 * @return 永远不会正常返回
	 * @throws UnsupportedOperationException 因为空列表没有 tail
	 */
	@Override
	public Seq<T> tail() {
		throw new UnsupportedOperationException("tail of empty list");
	}

	@Override
	public String toString() {
		return "Nil";
	}

}
