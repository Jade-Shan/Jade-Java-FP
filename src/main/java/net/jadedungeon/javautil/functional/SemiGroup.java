package net.jadedungeon.javautil.functional;

/**
 * 半群（SemiGroup）—— 代数结构接口。
 * <p>
 * 半群是一个定义了二元结合运算 {@link #op(Object, Object)} 的集合，
 * 该运算满足结合律：{@code op(op(a, b), c) == op(a, op(b, c))}。
 * </p>
 *
 * <p>扩展接口：{@link Monoid} 在半群的基础上增加了单位元（零元）的概念。</p>
 *
 * @param <T> 半群中元素的类型
 */
public interface SemiGroup<T> {

	/**
	 * 二元结合运算。
	 * <p>
	 * 将两个元素 a 和 b 按结合律合并为一个新元素。
	 * 实现时应满足结合律：{@code op(op(a, b), c) == op(a, op(b, c))}。
	 * </p>
	 *
	 * @param a 第一个操作数
	 * @param b 第二个操作数
	 * @return 合并后的结果
	 */
	public T op(T a, T b);

}
