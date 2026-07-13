package net.jadedungeon.javautil.functional;

/**
 * 幺半群（Monoid）—— 代数结构接口。
 * <p>
 * Monoid 在 {@link SemiGroup} 的基础上增加了单位元 {@link #getZero()}，
 * 单位元满足：{@code op(a, getZero()) == a} 且 {@code op(getZero(), a) == a}。
 * </p>
 *
 * <p>典型例子：</p>
 * <ul>
 *   <li>整数加法的 Monoid：零元为 0，操作为 {@code +}</li>
 *   <li>字符串拼接的 Monoid：零元为 {@code ""}，操作为字符串拼接</li>
 *   <li>列表的 Monoid：零元为空列表，操作为列表拼接</li>
 * </ul>
 *
 * @param <T> Monoid 中元素的类型
 */
public interface Monoid<T> extends SemiGroup<T> {

	/**
	 * 返回该 Monoid 的单位元（零元）。
	 * <p>
	 * 单位元与任意元素做 {@link #op(Object, Object)} 运算的结果恒等于该元素本身：
	 * {@code op(a, getZero()) == a} 且 {@code op(getZero(), a) == a}。
	 * </p>
	 *
	 * @return 单位元
	 */
	public T getZero();
}
