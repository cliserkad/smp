package xyz.cliserkad.util;

import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.function.Supplier;

public sealed interface Result<SuccessType, FailureType extends Exception> {

	sealed class Success<SuccessType, FailureType extends Exception> implements Result<SuccessType, FailureType> {

		public final SuccessType value;

		public Success(SuccessType value) {
			this.value = Objects.requireNonNull(value);
		}

		private Success() {
			this.value = null;
		}

		static final class VoidSuccess<FailureType extends Exception> extends Success<Void, FailureType> {

			public static final VoidSuccess<?> VOID = new VoidSuccess<>();

			private VoidSuccess() {
				super();
			}

		}

	}

	final class Failure<SuccessType, FailureType extends Exception> extends UnionMember<FailureType> implements Result<SuccessType, FailureType> {

		public Failure(FailureType error) {
			super(error);
		}

		public void except() throws FailureType {
			throw getValue();
		}

	}

	default Result<Void, ?> onSuccess(Runnable runnable) {
		return switch(this) {
			case Success<?, ?> success -> of(runnable);
			case Failure<?, ?> f -> new Failure<>(f.getValue());
		};
	}

	static <T> Result<Void, ?> of(Consumer<T> consumer, T value) {
		try {
			consumer.accept(value);
			return Success.VoidSuccess.VOID;
		} catch(Exception e) {
			return new Failure<>(e);
		}
	}

	static Result<Void, ?> of(Runnable runnable) {
		try {
			runnable.run();
			return Success.VoidSuccess.VOID;
		} catch(Exception e) {
			return new Failure<>(e);
		}
	}

	static <T> Result<T, ?> of(Callable<T> callable) {
		try {
			return new Success<>(callable.call());
		} catch(Exception e) {
			return new Failure<>(e);
		}
	}

	static <T> Result<T, ?> fromSupplier(Supplier<T> supplier) {
		try {
			return new Success<>(supplier.get());
		} catch(Exception e) {
			return new Failure<>(e);
		}
	}

}
