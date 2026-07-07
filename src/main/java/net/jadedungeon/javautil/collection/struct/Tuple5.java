package net.jadedungeon.javautil.collection.struct;

import java.util.Objects;

public class Tuple5<T1, T2, T3, T4, T5> {
	private final T1 _1;
	private final T2 _2;
	private final T3 _3;
	private final T4 _4;
	private final T5 _5;

	public static <T1, T2, T3, T4, T5> Tuple5<T1, T2, T3, T4, T5> of(T1 _1, T2 _2, T3 _3, T4 _4, T5 _5) {
		return new Tuple5<>(_1, _2, _3, _4, _5);
	}

	private Tuple5(T1 _1, T2 _2, T3 _3, T4 _4, T5 _5) {
		super();
		this._1 = _1;
		this._2 = _2;
		this._3 = _3;
		this._4 = _4;
		this._5 = _5;
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

	public T4 get_4() {
		return _4;
	}

	public T5 get_5() {
		return _5;
	}

	@Override
	public int hashCode() {
		return Objects.hash(_1, _2, _3, _4, _5);
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
		Tuple5<T1, T2, T3, T4, T5> other = (Tuple5<T1, T2, T3, T4, T5>) obj;
		return Objects.equals(_1, other._1) && Objects.equals(_2, other._2) && Objects.equals(_3, other._3)
				&& Objects.equals(_4, other._4) && Objects.equals(_5, other._5);
	}

	@Override
	public String toString() {
		return String.format("Tuple5 [%s, %s, %s, %s, %s]", _1, _2, _3, _4, _5);
	}

}
