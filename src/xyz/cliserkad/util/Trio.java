package xyz.cliserkad.util;

public class Trio<TypeA, TypeB, TypeC> extends TupleBase implements Tuple {

	public final TypeA a;
	public final TypeB b;
	public final TypeC c;

	public Trio(final TypeA a, final TypeB b, final TypeC c) {
		this.a = a;
		this.b = b;
		this.c = c;
	}

	@Override
	public Object[] getValues() {
		return new Object[] { a, b, c };
	}

}
