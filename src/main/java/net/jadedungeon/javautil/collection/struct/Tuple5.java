package net.jadedungeon.javautil.collection.struct;

import java.util.Objects;

/**
 * 五元组（5-Tuple）—— 不可变的数据容器。
 * <p>
 * 持有五个不同类型的值，提供了 getter 方法和基于值的
 * {@link #equals(Object)} / {@link #hashCode()} 实现。
 * </p>
 *
 * @param <T1> 第一个元素的类型
 * @param <T2> 第二个元素的类型
 * @param <T3> 第三个元素的类型
 * @param <T4> 第四个元素的类型
 * @param <T5> 第五个元素的类型
 */
public class Tuple5<T1, T2, T3, T4, T5> {
	private final T1 _1;
	private final T2 _2;
	private final T3 _3;
	private final T4 _4;
	private final T5 _5;

	/**
	 * 工厂方法：创建一个五元组。
	 *
	 * @param <T1> 第一个元素的类型
	 * @param <T2> 第二个元素的类型
	 * @param <T3> 第三个元素的类型
	 * @param <T4> 第四个元素的类型
	 * @param <T5> 第五个元素的类型
	 * @param _1   第一个元素
	 * @param _2   第二个元素
	 * @param _3   第三个元素
	 * @param _4   第四个元素
	 * @param _5   第五个元素
	 * @return 新的 Tuple5 实例
	 */
	public static <T1, T2, T3, T4, T5> Tuple5<T1, T2, T3, T4, T5> of(T1 _1, T2 _2, T3 _3, T4 _4, T5 _5) {
		return new Tuple5<>(_1, _2, _3, _4, _5);
	}

	/**
	 * 私有构造函数，请使用 {@link #of(Object, Object, Object, Object, Object)} 工厂方法。
	 *
	 * @param _1 第一个元素
	 * @param _2 第二个元素
	 * @param _3 第三个元素
	 * @param _4 第四个元素
	 * @param _5 第五个元素
	 */
	private Tuple5(T1 _1, T2 _2, T3 _3, T4 _4, T5 _5) {
		super();
		this._1 = _1;
		this._2 = _2;
		this._3 = _3;
		this._4 = _4;
		this._5 = _5;
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

	/**
	 * 获取第三个元素。
	 *
	 * @return 第三个元素
	 */
	public T3 get_3() {
		return _3;
	}

	/**
	 * 获取第四个元素。
	 *
	 * @return 第四个元素
	 */
	public T4 get_4() {
		return _4;
	}

	/**
	 * 获取第五个元素。
	 *
	 * @return 第五个元素
	 */
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
