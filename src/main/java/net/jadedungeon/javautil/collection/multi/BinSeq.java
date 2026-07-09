package net.jadedungeon.javautil.collection.multi;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Stream;

import net.jadedungeon.javautil.collection.struct.Either;

public class BinSeq<L, R> {
	private final Seq<L> leftSeq;
	private final Seq<R> rightSeq;

	public BinSeq() {
		this.leftSeq = Seq.empty();
		this.rightSeq = Seq.empty();
	}

	public BinSeq(Seq<L> leftSeq, Seq<R> rightSeq) {
		this.leftSeq = leftSeq == null ? Seq.empty() : leftSeq;
		this.rightSeq = rightSeq == null ? Seq.empty() : rightSeq;
	}

	public Seq<L> getLeft() {
		return this.leftSeq;
	}

	public Seq<R> getRight() {
		return this.rightSeq;
	}

	public BinSeq<L, R> appendLeft(L value) {
		if (null != value) {
			Seq<L> newSeq = this.leftSeq.attach(value);
			return new BinSeq<L, R>(newSeq, this.rightSeq);
		} else {
			return this;
		}
	}

	public BinSeq<L, R> appendRight(R value) {
		if (null != value) {
			Seq<R> newSeq = this.rightSeq.attach(value);
			return new BinSeq<L, R>(this.leftSeq, newSeq);
		} else {
			return this;
		}
	}

	public BinSeq<L, R> appendLeft(Optional<L> value) {
		if (null != value && value.isPresent()) {
			Seq<L> newSeq = this.leftSeq.attach(value.get());
			return new BinSeq<L, R>(newSeq, this.rightSeq);
		} else {
			return this;
		}
	}

	public BinSeq<L, R> appendRight(Optional<R> value) {
		if (null != value && value.isPresent()) {
			Seq<R> newSeq = this.rightSeq.attach(value.get());
			return new BinSeq<L, R>(this.leftSeq, newSeq);
		} else {
			return this;
		}
	}

	public BinSeq<L, R> append(Either<L, R> o) {
		if (o.isLeftPresent()) {
			return appendLeft(o.left());
		} else if (o.isRightPresent()) {
			return appendRight(o.right());
		} else {
			return this;
		}
	}

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

	public BinSeq<L, R> appendLeft(Seq<L> seq) {
		if (null == seq || seq.isEmpty()) {
			return this;
		} else {
			Seq<L> newSeq = appendElems(this.leftSeq, seq);
			return new BinSeq<L, R>(newSeq, this.rightSeq);
		}
	}

	public BinSeq<L, R> appendRight(Seq<R> seq) {
		if (null == seq || seq.isEmpty()) {
			return this;
		} else {
			Seq<R> newSeq = appendElems(this.rightSeq, seq);
			return new BinSeq<L, R>(this.leftSeq, newSeq);
		}
	}

	public BinSeq<L, R> append(BinSeq<L, R> other) {
		Seq<L> newLeft = appendElems(this.leftSeq, other.leftSeq);
		Seq<R> newRight = appendElems(this.rightSeq, other.rightSeq);
		return new BinSeq<L, R>(newLeft, newRight);
	}

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

