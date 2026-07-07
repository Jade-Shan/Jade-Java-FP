package net.jadedungeon.javautil.tco;

import java.util.function.Supplier;

public abstract class CallStack<T> {

	/**
	 * eval all recursive call chain
	 * 
	 * @return
	 */
	public abstract T eval();

	/**
	 * factory for create stack frame of stack top (recursive end)
	 * 
	 * @param <T>        result type
	 * @param eagerValue immediately value
	 * @return stack end frame
	 */
	public static <T> StackBottom<T> of(T eagerValue) {
		return new StackBottom<>(eagerValue);
	}

	/**
	 * factory for create middle stack frame
	 * 
	 * @param <T>       result type
	 * @param lazyValue supplier for lazy-eval value
	 * @return middle stack frame
	 */
	public static <T> StackFrame<T> of(Supplier<CallStack<T>> lazyValue) {
		return new StackFrame<>(lazyValue);
	}

	/**
	 * eval this stack frame in all recursive call chain
	 */
	protected abstract CallStack<T> stepForward();

	/**
	 * check is recursive end
	 * 
	 * @return isButtom
	 */
	protected abstract boolean isButtom();

	/**
	 * recursive end stack frame
	 * 
	 * @author qwshan
	 *
	 * @param <T> result type
	 */
	public static class StackBottom<T> extends CallStack<T> {

		private final T value;

		private StackBottom(T value) {
			this.value = value;
		}

		@Override
		public T eval() {
			return value;
		}

		@Override
		public boolean isButtom() {
			return true;
		}

		@Override
		public CallStack<T> stepForward() {
			throw new IllegalStateException("Return has no resume");
		}
	}

	public static class StackFrame<T> extends CallStack<T> {

		private final Supplier<CallStack<T>> lazyValue;

		private StackFrame(Supplier<CallStack<T>> lazyValue) {
			this.lazyValue = lazyValue;
		}

		@Override
		public T eval() {
			CallStack<T> curr = this;
			while (!curr.isButtom()) { // stack to loop
				curr = curr.stepForward();
			}
			return curr.eval();
		}

		@Override
		public boolean isButtom() {
			return false;
		}

		@Override
		public CallStack<T> stepForward() {
			return lazyValue.get();
		}
	}
}