package net.jadedungeon.javautil.collection.multi;

public class Cons<T> extends Seq<T> {
	private final int length;
	private final T head;
	private final Seq<T> tail;

	Cons(T head, Seq<T> tail) {
		this.head = head;
		this.tail = tail;
		this.length = tail.size() + 1;
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public int size() {
		return length;
	}

	@Override
	public T eval() {
		return head();
	}

	@Override
	public T head() {
		return head;
	}

	@Override
	public Seq<T> tail() {
		return tail;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer("List [");
		return toStringAcc(this, sb).toString();
	}

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
