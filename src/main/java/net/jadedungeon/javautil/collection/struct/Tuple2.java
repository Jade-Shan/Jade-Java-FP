package net.jadedungeon.javautil.collection.struct;

import java.util.Objects;

/**
 * 二元组（2-Tuple / Pair）—— 不可变的数据容器。
 * <p>
 * 持有两个不同类型的值，提供了 getter 方法和基于值的
 * {@link #equals(Object)} / {@link #hashCode()} 实现。
 * </p>
 *
 * @param <T1> 第一个元素的类型
 * @param <T2> 第二个元素的类型
 */
public class Tuple2<T1, T2> {
	private final T1 _1;
	private final T2 _2;

	/**
	 * 工厂方法：创建一个二元组。
	 *
	 * @param <T1> 第一个元素的类型
	 * @param <T2> 第二个元素的类型
	 * @param _1   第一个元素
	 * @param _2   第二个元素
	 * @return 新的 Tuple2 实例
	 */
	public static <T1, T2> Tuple2<T1, T2> of(T1 _1, T2 _2) {
		return new Tuple2<>(_1, _2);
	}

	/**
	 * 私有构造函数，请使用 {@link #of(Object, Object)} 工厂方法创建实例。
	 *
	 * @param _1 第一个元素
	 * @param _2 第二个元素
	 */
	private Tuple2(T1 _1, T2 _2) {
		this._1 = _1;
		this._2 = _2;
	}

	/**
	 * 获取第一个元素。
	 *
	 * @return 第一个元素
	 */
	public T1 get_1() {
		return _1;
	}

	/**
	 * 获取第二个元素。
	 *
	 * @return 第二个元素
	 */
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
