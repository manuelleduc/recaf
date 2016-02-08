package recaf.iter;

import java.util.Iterator;

import recaf.core.AbstractJavaImpl;
import recaf.core.functional.ED;
import recaf.core.functional.K0;
import recaf.core.functional.SD;

public class Iter<R> extends AbstractJavaImpl<R> {

	@SuppressWarnings("serial")
	private static final class Yield extends RuntimeException {
		final Object value;
		final K0 k;

		public Yield(Object value, K0 k) {
			this.value = value;
			this.k = k;
		}
		
		@Override
		public synchronized Throwable fillInStackTrace() {
			return this;
		}
	}

	public Iterable<R> Method(SD<R> body) {
		return new Iterable<R>() {

			boolean exhausted = false;
			R current = null;
			K0 k = () -> {
				body.accept(r -> {
					exhausted = true;
				} , () -> {
					exhausted = true;
				},  (s) -> {
					exhausted = true;
				},  () -> {
					exhausted = true;
				} , exc -> {
					throw new RuntimeException(exc);
				});
			};

			@Override
			public Iterator<R> iterator() {
				return new Iterator<R>() {

					@SuppressWarnings("unchecked")
					@Override
					public boolean hasNext() {
						if (exhausted) {
							return false;
						}
						try {
							k.call();
							return false;
						} catch (Yield y) {
							current = (R) y.value;
							k = y.k;
							return true;
						}
					}

					@Override
					public R next() {
						return current;
					}

				};
			}
		};
	}

	@Override
	public SD<R> Return(ED<R> e) {
		throw new AssertionError("Cannot return value from iterator.");
	}

	public <U> SD<R> Yield(ED<U> exp) {
		return (rho, sigma, brk, contin, err) -> {
			exp.accept(v -> {
				throw new Yield(v, sigma);
			} , err);
		};
	}

	public <U> SD<R> YieldFrom(ED<Iterable<U>> exp) {
		return For(exp, e -> Yield(Exp(() -> e)));
	}
}
