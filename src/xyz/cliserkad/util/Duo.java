package xyz.cliserkad.util;

public class Duo<TypeA, TypeB> extends TupleBase implements Tuple {

	public final TypeA a;
	public final TypeB b;

	public Duo(final TypeA a, final TypeB b) {
		this.a = a;
		this.b = b;
	}

	@Override
	public Object[] getValues() {
		return new Object[] { a, b };
	}

}
