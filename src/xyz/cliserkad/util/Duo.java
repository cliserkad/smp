package xyz.cliserkad.util;

import java.io.NotSerializableException;
import java.io.Serial;
import java.io.Serializable;

import static xyz.cliserkad.util.SerialVersionUIDGenerator.generateSerialVersionUID;

/**
 * A class that holds two objects of any types. May cause NotSerializableException
 *
 * @see DuoSerial Guaranteed Serializable Duo
 */
public class Duo<TypeA, TypeB> extends TupleBase implements Tuple, Serializable {

	@Serial
	private static final long serialVersionUID = generateSerialVersionUID(Duo.class);

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

	public DuoSerial<? extends Serializable, ? extends Serializable> toSerializable() throws NotSerializableException {
		if(a instanceof Serializable serializableA && b instanceof Serializable serializableB)
			return new DuoSerial<>(serializableA, serializableB);
		else if(!(a instanceof Serializable))
			throw new NotSerializableException(a.getClass().getName());
		else // b will not be serializable here
			throw new NotSerializableException(b.getClass().getName());
	}

	public boolean isSerializable() {
		return a instanceof Serializable && b instanceof Serializable;
	}

}
