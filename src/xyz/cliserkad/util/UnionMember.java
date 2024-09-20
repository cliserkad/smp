package xyz.cliserkad.util;

public class UnionMember<Type> implements Union {

	private final Type value;

	public UnionMember(Type value) throws NullPointerException {
		this.value = failIfNull(value);
	}

	public static <ArgumentType> ArgumentType failIfNull(ArgumentType argument) throws NullPointerException {
		if(argument == null)
			throw new NullPointerException("value cannot be null");
		return argument;
	}

	@Override
	public Type getValue() {
		return value;
	}

	/**
	 * Provides access to equals() method of underlying value. If the Object given is a Union, it will be unwrapped to compare underlying values.
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Union wrapper)
			return getValue().equals(wrapper.getValue());
		else
			return getValue().equals(obj);
	}

	/**
	 * Provides access to hashCode() method of underlying value.
	 */
	@Override
	public int hashCode() {
		return getValue().hashCode();
	}

	/**
	 * Provides access to toString() method of underlying value.
	 */
	@Override
	public String toString() {
		return getValue().toString();
	}

}
