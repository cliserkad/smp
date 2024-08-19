package xyz.cliserkad.util;

/**
 * Implementers will have copy() to allow for copying the values of an object while having a different reference. Implementers must have equals() defined, otherwise checkClone will always fail and throw an IllegalStateException.
 *
 * @param <Type>
 * @author Bryan Johnson
 */
public interface Copier<Type> {

	/**
	 * Tests if <code>copy</code> is equal in value but not reference. Specifically,
	 * <code>original != copy &amp;&amp; original.equals(copy)</code>.
	 *
	 * @param original Parent object
	 * @param copy     Child object
	 * @return <code>true</code> if <code>copy</code> is a proper copy of
	 * <code>original</code>
	 */
	static <Any> boolean checkCopy(final Any original, final Any copy) {
		try {
			return checkCopyVerbose(original, copy);
		} catch(final IllegalStateException e) {
			return false;
		}
	}

	static <Any> boolean checkCopyVerbose(final Any original, final Any copy) throws IllegalStateException {
		if(copy != null && original != null && original != copy && original.equals(copy)) {
			return true;
		} else {
			if(copy == null) {
				throw new IllegalStateException("The copied object's reference is null");
			}
			if(original == null) {
				throw new IllegalStateException("The original object's reference is null");
			}
			if(original == copy) {
				throw new IllegalStateException("The original and copied object share the same reference");
			}
			if(!original.equals(copy)) {
				throw new IllegalStateException("The copied object does not equal the original; equals() might be unimplemented");
			}
			return false;
		}
	}

	/**
	 * Creates a <code>Copy</code> container which holds an actual copy of the object this method was called on.
	 *
	 * @return a <code>Copy</code>
	 */
	Copy<Type> copy();

	/**
	 * Provides access to the object that is implementing Copies. Should be defined as <code>return this;</code>.
	 *
	 * @return Implementation
	 */
	Type self();

	/**
	 * Container class that holds the data from <code>Copies.copy()</code>.
	 *
	 * @param <Type> The contained Type
	 * @author Bryan Johnson
	 */
	final class Copy<Type> {

		/**
		 * Holds the Child/copy data
		 */
		public final Type data;

		/**
		 * Ensures each <code>copy</code> is a proper copy. Uses checkCopy to determine when to throw an error.
		 *
		 * @param original Parent object
		 * @param copy     Child object
		 * @throws IllegalStateException when the Copy fails checkClone()
		 */
		public Copy(final Type original, final Type copy) throws IllegalStateException {
			data = copy;
			if(!checkCopy(original, copy)) {
				checkCopyVerbose(original, copy);
			}
		}

		/**
		 * Pass <code>toString()</code> call to underlying object <code>data</code>.
		 *
		 * @return <code>data.toString();</code>
		 */
		@Override
		public String toString() {
			return data.toString();
		}

		/**
		 * Pass <code>equals()</code> call to underlying object <code>data</code>.
		 *
		 * @return <code>data.equals(o);</code>
		 */
		@SuppressWarnings("EqualsWhichDoesntCheckParameterClass") // This method is generic & the underlying object checks the type
		@Override
		public boolean equals(final Object o) {
			return data.equals(o);
		}

		/**
		 * Pass <code>hashCode()</code> call to underlying object <code>data</code>.
		 *
		 * @return <code>data.hashCode();</code>
		 */
		@Override
		public int hashCode() {
			return data.hashCode();
		}

	}

}
