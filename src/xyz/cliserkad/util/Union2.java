package xyz.cliserkad.util;

import java.util.function.Function;

/**
 * A class that can be one of 2 types. see Union3 for more details
 */
public sealed interface Union2<TypeA, TypeB> extends Union2Extendable<TypeA, TypeB> {

	default <ReturnType> ReturnType match(Function<TypeA, ReturnType> funcA, Function<TypeB, ReturnType> funcB) {
		return switch(this) {
			case Union2.A<TypeA, ?> a -> funcA.apply(a.getValue());
			case Union2.B<?, TypeB> b -> funcB.apply(b.getValue());
		};
	}

	default <ReturnType> ReturnType matchFallible(CheckedFunction<TypeA, ReturnType> funcA, CheckedFunction<TypeB, ReturnType> funcB) throws Exception {
		return switch(this) {
			case Union2.A<TypeA, ?> a -> funcA.apply(a.getValue());
			case Union2.B<?, TypeB> b -> funcB.apply(b.getValue());
		};
	}

	final class A<TypeA, TypeB> extends UnionMember<TypeA> implements Union2<TypeA, TypeB> {

		public A(TypeA value) {
			super(value);
		}

	}

	final class B<TypeA, TypeB> extends UnionMember<TypeB> implements Union2<TypeA, TypeB> {

		public B(TypeB value) {
			super(value);
		}

	}

}
