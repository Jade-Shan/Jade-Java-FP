package net.jadedungeon.javautil.collection.struct;

import java.util.Objects;

public class Tuple3<T1, T2, T3> {
	private final T1 _1;
	private final T2 _2;
	private final T3 _3;

	public static <T1, T2, T3> Tuple3<T1, T2, T3> of(T1 _1, T2 _2, T3 _3) {
		return new Tuple3<>(_1, _2, _3);
	}

	public Tuple3(T1 _1, T2 _2, T3 _3) {
		this._1 = _1;
		this._2 = _2;
		this._3 = _3;
	}

	public T1 get_1() {
		return _1;
	}

	public T2 get_2() {
		return _2;
	}

	public T3 get_3() {
		return _3;
	}

	@Override
	public int hashCode() {
		return Objects.hash(_1, _2, _3);
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
		Tuple3<T1, T2, T3> other = (Tuple3<T1, T2, T3>) obj;
		return Objects.equals(_1, other._1) && Objects.equals(_2, other._2) && Objects.equals(_3, other._3);
	}

	@Override
	public String toString() {
		return String.format("Tuple5 [%s, %s, %s]", _1, _2, _3);
	}
}
