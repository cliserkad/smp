package xyz.cliserkad.util;

import java.io.Serial;
import java.io.Serializable;

import static xyz.cliserkad.util.SerialVersionUIDGenerator.generateSerialVersionUID;

/**
 * A serializable version of the Duo class. Adds type bounds to the generic types, for compile time checks.
 */
public class DuoSerial<TypeA extends Serializable, TypeB extends Serializable> extends Duo<TypeA, TypeB> implements Serializable {

	@Serial
	private static final long serialVersionUID = generateSerialVersionUID(DuoSerial.class);

	public DuoSerial(TypeA a, TypeB b) {
		super(a, b);
	}

}
