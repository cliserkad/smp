package xyz.cliserkad.util;

import java.util.Objects;

public abstract class TupleBase implements Tuple {

	/**
	 * Compares all corresponding values in the two tuples. Returns false when the two tuples are of different sizes.
	 */
	@Override
	public boolean equals(Object o) {
		if(this == o)
			return true;
		if(!(o instanceof Tuple other))
			return false;
		Object[] myValues = getValues();
		Object[] otherValues = other.getValues();
		if(myValues.length != otherValues.length)
			return false;
		for(int i = 0; i < myValues.length; i++)
			if(!Objects.equals(myValues[i], otherValues[i]))
				return false;
		return true;
	}

	/**
	 * @return <code>Objects.hash(getValues());</code>
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getValues());
	}

	/**
	 * Returns a string in the format<br>
	 * <code>{ a: "a's value"; b: "b's value"; ... }</code>
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("{\n");
		Object[] values = getValues();
		for(int i = 0; i < values.length; i++) {
			sb.append(" ").append((char) ('a' + i)).append(": \"").append(values[i]).append("\";\n");
		}
		sb.append("}");
		return sb.toString();
	}

}
