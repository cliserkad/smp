package xyz.cliserkad.util;

public class NamedObjectList<T extends NamedObject> extends BestList<T> {

	private static final long serialVersionUID = 1L;

	private StringList names;

	private StringList getNames() {
		if(names == null)
			names = new StringList();
		return names;
	}

	@Override
	public boolean add(final T object) {
		final var addedToNames = getNames().add(object.getName());
		final var addedToArray = super.add(object);
		return addedToNames && addedToArray;
	}

	@Override
	public void add(final int index, final T object) {
		getNames().add(index, object.getName());
		super.add(index, object);
	}

	@Override
	public T set(final int index, final T object) {
		getNames().set(index, object.getName());
		return super.set(index, object);
	}

	@Override
	public void clear() {
		getNames().clear();
		super.clear();
	}

	@Override
	public T remove(final int index) {
		getNames().remove(index);
		return super.remove(index);
	}

	@Override
	public boolean remove(final Object o) {
		final var removedFromNames = getNames().remove(o);
		final var removedFromArray = super.remove(o);
		return removedFromNames && removedFromArray;
	}

	public boolean contains(final String target) {
		for(final String s : getNames()) {
			if(s.equals(target)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Get an object from this list based on its name
	 *
	 * @param target object's supposed name
	 * @return T object if found, null otherwise
	 */
	public T get(final String target) {
		if(contains(target)) {
			for(final T object : this) {
				if(object.getName().equals(target)) {
					return object;
				}
			}
		}
		return null;
	}

}
