package net.jadedungeon.javautil.functional;

import java.util.function.Predicate;

/**
 * 可过滤的 Monad（MFilterable）—— 扩展了 Monad 的过滤能力。
 * <p>
 * 在 {@link Monad} 的基础上增加了 {@link #filter(Predicate)} 方法，
 * 允许根据谓词条件保留或丢弃容器中的元素。
 * 这与 Haskell 中 MonadPlus 的 mfilter 语义类似。
 * </p>
 *
 * @param <T> 容器内元素的类型
 */
public interface MFilterable<T> extends Monad<T> {

	/**
	 * 根据谓词条件过滤元素。
	 * <p>
	 * 保留满足谓词条件的元素，丢弃不满足的元素。
	 * 对于单值容器（如 Option），不满足时返回空容器（None）；
	 * 对于多值容器（如 Seq），返回只包含满足条件元素的列表。
	 * </p>
	 *
	 * @param pdt 用于判断元素是否保留的谓词函数
	 * @return 过滤后的新容器
	 */
	public MFilterable<T> filter(Predicate<T> pdt);

}
