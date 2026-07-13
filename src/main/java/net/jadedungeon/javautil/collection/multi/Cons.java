package net.jadedungeon.javautil.collection.multi;

/**
 * Cons —— 非空 Seq 节点。
 * <p>
 * Cons 是 {@link Seq} 的非空实现，由 head（当前元素）和 tail（剩余列表）组成。
 * 这是函数式编程中经典的 Cons Cell 结构，名称源自 Lisp 的 {@code cons} 操作。
 * </p>
 * <p>
 * 不可变：head 和 tail 在构造时确定，之后不会改变。
 * 长度在构造时缓存（O(1) 获取）。
 * </p>
 *
 * @param <T> 元素类型
 */
public class Cons<T> extends Seq<T> {
	/** 列表长度（缓存值，O(1) 获取） */
	private final int length;
	/** 当前节点的元素值 */
	private final T head;
	/** 剩余列表引用 */
	private final Seq<T> tail;

	/**
	 * 构造一个 Cons 节点。
	 *
	 * @param head 当前节点的元素值
	 * @param tail 剩余列表
	 */
	Cons(T head, Seq<T> tail) {
		this.head = head;
		this.tail = tail;
		this.length = tail.size() + 1;
	}

	/**
	 * Cons 永远不为空。
	 *
	 * @return 始终返回 false
	 */
	@Override
	public boolean isEmpty() {
		return false;
	}

	/**
	 * 返回列表长度（O(1)，构造时缓存）。
	 *
	 * @return 列表元素数量
	 */
	@Override
	public int size() {
		return length;
	}

	/**
	 * 返回当前节点的元素值。
	 *
	 * @return head 元素
	 */
	@Override
	public T eval() {
		return head();
	}

	/**
	 * 返回当前节点的元素值。
	 *
	 * @return head 元素
	 */
	@Override
	public T head() {
		return head;
	}

	/**
	 * 返回剩余列表（即去掉 head 后的尾部）。
	 *
	 * @return tail 列表
	 */
	@Override
	public Seq<T> tail() {
		return tail;
	}

	/**
	 * 返回列表的字符串表示，格式为 {@code List [elem1, elem2, ...]}。
	 */
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer("List [");
		return toStringAcc(this, sb).toString();
	}

	/**
	 * toString 的递归辅助方法。
	 *
	 * @param lst 当前正在序列化的列表
	 * @param sb  累积的字符串缓冲区
	 * @return 含序列化结果的缓冲区
	 */
	private StringBuffer toStringAcc(Seq<T> lst, StringBuffer sb) {
		if (lst.isEmpty()) {
			sb.append("]");
			return sb;
		} else {
			sb.append(lst.head().toString());
			if (!lst.tail().isEmpty()) {
				sb.append(", ");
			}
			return toStringAcc(lst.tail(), sb);
		}

	}

}
