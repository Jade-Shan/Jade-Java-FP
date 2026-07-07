package net.jadedungeon.javautil.collection.struct;

import java.util.Objects;

public class Tuple2<T1, T2> {
	private final T1 _1;
	private final T2 _2;

	public static <T1, T2> Tuple2<T1, T2> of(T1 _1, T2 _2) {
		return new Tuple2<>(_1, _2);
	}

	private Tuple2(T1 _1, T2 _2) {
		this._1 = _1;
		this._2 = _2;
	}

	public T1 get_1() {
		return _1;
	}

	public T2 get_2() {
		return _2;
	}

	@Override
	public int hashCode() {
		return Objects.hash(_1, _2);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		@SuppressWarnings("unchecked")
		Tuple2<T1, T2> other = (Tuple2<T1, T2>) obj;
		return Objects.equals(_1, other._1) && Objects.equals(_2, other._2);
	}

	@Override
	public String toString() {
		return String.format("Tuple5 [%s, %s, %s]", _1, _2);
	}
}
