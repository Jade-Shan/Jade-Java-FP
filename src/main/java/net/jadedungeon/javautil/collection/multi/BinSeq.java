package net.jadedungeon.javautil.collection.multi;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Stream;

import net.jadedungeon.javautil.collection.struct.Either;

/**
 * BinSeq —— 双序列容器。
 * <p>
 * 根据 {@link Either} 的左右分支将元素分别归入左侧列表（{@code Seq<L>}）和
 * 右侧列表（{@code Seq<R>}）。常用于将混合数据流按照成功/失败、
 * 正确/错误等维度进行分类收集。
 * </p>
 *
 * <p>使用示例：</p>
 * <pre>{@code
 * BinSeq<String, Integer> bin = new BinSeq<>();
 * bin = bin.appendLeft("error1");
 * bin = bin.appendRight(42);
 * }</pre>
 *
 * <p>不可变：所有 append 操作返回新的 BinSeq 实例。</p>
 *
 * @param <L> 左侧元素的类型
 * @param <R> 右侧元素的类型
 */
public class BinSeq<L, R> {
	private final Seq<L> leftSeq;
	private final Seq<R> rightSeq;

	/**
	 * 创建一个空的 BinSeq。
	 */
	public BinSeq() {
		this.leftSeq = Seq.empty();
		this.rightSeq = Seq.empty();
	}

	/**
	 * 用给定的左右 Seq 创建 BinSeq。null 参数会被替换为空 Seq。
	 *
	 * @param leftSeq  左侧序列
	 * @param rightSeq 右侧序列
	 */
	public BinSeq(Seq<L> leftSeq, Seq<R> rightSeq) {
		this.leftSeq = leftSeq == null ? Seq.empty() : leftSeq;
		this.rightSeq = rightSeq == null ? Seq.empty() : rightSeq;
	}

	/**
	 * 获取左侧序列。
	 *
	 * @return 左侧 Seq
	 */
	public Seq<L> getLeft() {
		return this.leftSeq;
	}

	/**
	 * 获取右侧序列。
	 *
	 * @return 右侧 Seq
	 */
	public Seq<R> getRight() {
		return this.rightSeq;
	}

	/**
	 * 向左侧序列附加一个元素。如果值为 null，返回当前实例不变。
	 *
	 * @param value 要附加到左侧的元素
	 * @return 新的 BinSeq（或当前实例，如果 value 为 null）
	 */
	public BinSeq<L, R> appendLeft(L value) {
		if (null != value) {
			Seq<L> newSeq = this.leftSeq.attach(value);
			return new BinSeq<L, R>(newSeq, this.rightSeq);
		} else {
			return this;
		}
	}

	/**
	 * 向右侧序列附加一个元素。如果值为 null，返回当前实例不变。
	 *
	 * @param value 要附加到右侧的元素
	 * @return 新的 BinSeq（或当前实例，如果 value 为 null）
	 */
	public BinSeq<L, R> appendRight(R value) {
		if (null != value) {
			Seq<R> newSeq = this.rightSeq.attach(value);
			return new BinSeq<L, R>(this.leftSeq, newSeq);
		} else {
			return this;
		}
	}

	/**
	 * 向左侧序列附加一个 {@link Optional} 值。如果值为空或为 null，返回当前实例不变。
	 *
	 * @param value 要附加的 Optional 值
	 * @return 新的 BinSeq（或当前实例，如果值不存在）
	 */
	public BinSeq<L, R> appendLeft(Optional<L> value) {
		if (null != value && value.isPresent()) {
			Seq<L> newSeq = this.leftSeq.attach(value.get());
			return new BinSeq<L, R>(newSeq, this.rightSeq);
		} else {
			return this;
		}
	}

	/**
	 * 向右侧序列附加一个 {@link Optional} 值。如果值为空或为 null，返回当前实例不变。
	 *
	 * @param value 要附加的 Optional 值
	 * @return 新的 BinSeq（或当前实例，如果值不存在）
	 */
	public BinSeq<L, R> appendRight(Optional<R> value) {
		if (null != value && value.isPresent()) {
			Seq<R> newSeq = this.rightSeq.attach(value.get());
			return new BinSeq<L, R>(this.leftSeq, newSeq);
		} else {
			return this;
		}
	}

	/**
	 * 根据 {@link Either} 的类型自动附加到对应侧。
	 * <ul>
	 *   <li>如果是 Left，附加到左侧</li>
	 *   <li>如果是 Right，附加到右侧</li>
	 *   <li>否则返回当前实例不变</li>
	 * </ul>
	 *
	 * @param o Either 值
	 * @return 新的 BinSeq（或当前实例）
	 */
	public BinSeq<L, R> append(Either<L, R> o) {
		if (o.isLeftPresent()) {
			return appendLeft(o.left());
		} else if (o.isRightPresent()) {
			return appendRight(o.right());
		} else {
			return this;
		}
	}

	/**
	 * 向左侧序列批量附加 {@link List} 中的元素。
	 *
	 * @param list 要附加的元素列表
	 * @return 新的 BinSeq（或当前实例，如果 list 为 null）
	 */
	public BinSeq<L, R> appendLeft(List<L> list) {
		if (null != list) {
			Seq<L> newSeq = Seq.empty();
			for (int i = 0; i < list.size(); i++) {
				newSeq = newSeq.attach(list.get(i));
			}
			return new BinSeq<L, R>(newSeq, this.rightSeq);
		} else {
			return this;
		}
	}

	/**
	 * 向右侧序列批量附加 {@link List} 中的元素。
	 *
	 * @param list 要附加的元素列表
	 * @return 新的 BinSeq（或当前实例，如果 list 为 null）
	 */
	public BinSeq<L, R> appendRight(List<R> list) {
		if (null != list) {
			Seq<R> newSeq = Seq.empty();
			for (int i = 0; i < list.size(); i++) {
				newSeq = newSeq.attach(list.get(i));
			}
			return new BinSeq<L, R>(this.leftSeq, newSeq);
		} else {
			return this;
		}
	}

	/**
	 * 将 other Seq 的元素追加到 self Seq，保持元素顺序。
	 * <p>
	 * 由于 Seq 的 {@code attach} 是在头部插入的，此方法先将 other 的元素
	 * 逆序放入临时 List，再按正确顺序构建结果。
	 * </p>
	 *
	 * @param <T>  元素类型
	 * @param self 原始 Seq
	 * @param other 要追加的 Seq
	 * @return 合并后的 Seq
	 */
	private static <T> Seq<T> appendElems(Seq<T> self, Seq<T> other) {
		if (null == other) {
			return self;
		}
		//
		List<T> list = new LinkedList<T>();
		while (!other.isEmpty()) {
			list.add(other.head());
			other = other.tail();
		}
		if (list.size() < 1) {
			return self;
		} else {
			Seq<T> newSeq = self;
			for (int i = list.size() - 1; i > -1; i--) {
				newSeq = newSeq.attach(list.get(i));
			}
			return newSeq;
		}
	}

	/**
	 * 向左侧序列追加一个 Seq 中的所有元素。
	 *
	 * @param seq 要追加的 Seq
	 * @return 新的 BinSeq（或当前实例，如果 seq 为 null 或空）
	 */
	public BinSeq<L, R> appendLeft(Seq<L> seq) {
		if (null == seq || seq.isEmpty()) {
			return this;
		} else {
			Seq<L> newSeq = appendElems(this.leftSeq, seq);
			return new BinSeq<L, R>(newSeq, this.rightSeq);
		}
	}

	/**
	 * 向右侧序列追加一个 Seq 中的所有元素。
	 *
	 * @param seq 要追加的 Seq
	 * @return 新的 BinSeq（或当前实例，如果 seq 为 null 或空）
	 */
	public BinSeq<L, R> appendRight(Seq<R> seq) {
		if (null == seq || seq.isEmpty()) {
			return this;
		} else {
			Seq<R> newSeq = appendElems(this.rightSeq, seq);
			return new BinSeq<L, R>(this.leftSeq, newSeq);
		}
	}

	/**
	 * 将另一个 BinSeq 合并到当前实例（左右分别合并）。
	 *
	 * @param other 要合并的 BinSeq
	 * @return 合并后的新 BinSeq
	 */
	public BinSeq<L, R> append(BinSeq<L, R> other) {
		Seq<L> newLeft = appendElems(this.leftSeq, other.leftSeq);
		Seq<R> newRight = appendElems(this.rightSeq, other.rightSeq);
		return new BinSeq<L, R>(newLeft, newRight);
	}

	/**
	 * 将包含 {@link Either} 的 Stream 按左右分支拆分（unzip）为 BinSeq。
	 * <p>
	 * Stream 中的 Left 值归入左侧列表，Right 值归入右侧列表。
	 * 支持通过 errHandl 处理异常；如果 errHandl 为 null，异常将直接抛出。
	 * </p>
	 *
	 * @param <L>      左侧元素类型
	 * @param <R>      右侧元素类型
	 * @param stream   包含 Either 的流
	 * @param errHandl 异常处理器（可为 null）
	 * @return 拆分后的 BinSeq
	 * @throws Exception 如果处理过程中发生异常且 errHandl 为 null
	 */
	public static <L, R> BinSeq<L, R> unzip(Stream<Either<L, R>> stream, // nl
			Consumer<Exception> errHandl) throws Exception // nl
	{
		BinSeq<L, R> result = new BinSeq<L, R>();
		try {
			Seq<L> newLeft = Seq.empty();
			Seq<R> newRight = Seq.empty();
			stream.forEach(rec -> {
				if (rec.isLeftPresent()) {
					newLeft.attach(rec.left().get());
				} else if (rec.isRightPresent()) {
					newRight.attach(rec.right().get());
				}
			});
			return new BinSeq<L, R>(newLeft, newRight);
		} catch (Exception e) {
			if (null != errHandl) {
				errHandl.accept(e);
			} else {
				throw e;
			}
		}
		return result;
	}

}
