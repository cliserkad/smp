package com.xarql.util;

public class NamedObjectList<T extends NamedObject> extends BestList<T> {
    private static final long serialVersionUID = 1L;

    private StringList names;

    @Override
    public boolean add(T object) {
        boolean addedToNames = names.add(object.getName());
        boolean addedToArray = super.add(object);
        return addedToNames && addedToArray;
    }

    @Override
    public void add(int index, T object) {
        names.add(index, object.getName());
        super.add(index, object);
    }

    @Override
    public T set(int index, T object) {
        names.set(index, object.getName());
        return super.set(index, object);
    }

    @Override
    public void clear() {
        names.clear();
        super.clear();
    }

    @Override
    public T remove(int index) {
        names.remove(index);
        return super.remove(index);
    }

    @Override
    public boolean remove(Object o) {
        boolean removedFromNames = names.remove(o);
        boolean removedFromArray = super.remove(o);
        return removedFromNames && removedFromArray;
    }

    public boolean contains(String target) {
        for(String s : names)
            if(s.equals(target))
                return true;
        return false;
    }

    /**
     * Get an object from this list based on its name
     * 
     * @param target object's supposed name
     * @return T object if found, null otherwise
     */
    public T get(String target) {
        if(contains(target)) {
            for(T object : this)
                if(object.getName().equals(target))
                    return object;
        }
        return null;
    }

}
