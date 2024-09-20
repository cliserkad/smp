package xyz.cliserkad.util;

import java.util.function.Function;

/**
 * A class that can be one of three types. This is useful for three unrelated types that need to be stored in the same variable.
 */
public sealed interface Union3<TypeA, TypeB, TypeC> extends Union3Extendable<TypeA, TypeB, TypeC> {

	/**
	 * apply 1 of 3 functions to the value, depending on its type.
	 */
	default <ReturnType> ReturnType match(Function<TypeA, ReturnType> funcA, Function<TypeB, ReturnType> funcB, Function<TypeC, ReturnType> funcC) {
		return switch(this) {
			case Union3.A<TypeA, ?, ?> a -> funcA.apply(a.getValue());
			case Union3.B<?, TypeB, ?> b -> funcB.apply(b.getValue());
			case Union3.C<?, ?, TypeC> c -> funcC.apply(c.getValue());
		};
	}

	/**
	 * apply 1 of 3 functions to the value, depending on its type. May throw an exception
	 *
	 * @throws Exception if the called function throws an Exception
	 */
	default <ReturnType> ReturnType matchFallible(CheckedFunction<TypeA, ReturnType> funcA, CheckedFunction<TypeB, ReturnType> funcB, CheckedFunction<TypeC, ReturnType> funcC) throws Exception {
		return switch(this) {
			case Union3.A<TypeA, ?, ?> a -> funcA.apply(a.getValue());
			case Union3.B<?, TypeB, ?> b -> funcB.apply(b.getValue());
			case Union3.C<?, ?, TypeC> c -> funcC.apply(c.getValue());
		};
	}

	final class A<TypeA, TypeB, TypeC> extends UnionMember<TypeA> implements Union3<TypeA, TypeB, TypeC> {

		public A(TypeA value) {
			super(value);
		}

	}

	final class B<TypeA, TypeB, TypeC> extends UnionMember<TypeB> implements Union3<TypeA, TypeB, TypeC> {

		public B(TypeB value) {
			super(value);
		}

	}

	final class C<TypeA, TypeB, TypeC> extends UnionMember<TypeC> implements Union3<TypeA, TypeB, TypeC> {

		public C(TypeC value) {
			super(value);
		}

	}

}
