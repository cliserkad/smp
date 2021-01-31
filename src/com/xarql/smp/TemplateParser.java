package com.xarql.smp;

import java.lang.reflect.Field;
import com.xarql.util.Path;
import sun.misc.Unsafe;

/**
 * Attempts to instantiate a template and fill it with the values from an smp
 * String
 */
@SuppressWarnings("unchecked")
public class TemplateParser {

	private static Unsafe unsafe = null;

	public static Unsafe getUnsafe() throws UnsafeException {
		if(unsafe == null) {
			unsafe = unsafeHack();
		}
		return unsafe;
	}

	public static <Template> Template parse(final String smp, final Class<Template> template) throws BadTemplateException, UnsafeException {
		Template out = null;
		final var data = GenericParser.parse(smp);
		try {
			out = template.getDeclaredConstructor().newInstance();

			for(final Field field : template.getFields()) {
				// a bunch of weird hacks to inject the value directly in the VM
				final var fieldOffset = getUnsafe().objectFieldOffset(field);
				final var obj = data.get(new Path(field.getName()));
				if(obj.getClass().isArray()) {
					getUnsafe().getAndSetObject(out, fieldOffset, obj);
				} else {
					if(obj instanceof Byte) {
						getUnsafe().putByte(out, fieldOffset, (byte) obj);
					} else if(obj instanceof Short) {
						getUnsafe().putShort(out, fieldOffset, (short) obj);
					} else if(obj instanceof Character) {
						getUnsafe().putChar(out, fieldOffset, (char) obj);
					} else if(obj instanceof Integer) {
						getUnsafe().putInt(out, fieldOffset, (int) obj);
					} else if(obj instanceof Float) {
						getUnsafe().putFloat(out, fieldOffset, (float) obj);
					} else if(obj instanceof Long) {
						getUnsafe().putLong(out, fieldOffset, (long) obj);
					} else if(obj instanceof Double) {
						getUnsafe().putDouble(out, fieldOffset, (double) obj);
					} else {
						getUnsafe().putObject(out, fieldOffset, obj);
					}
				}
			}
		} catch(final ReflectiveOperationException e) {
			throw new BadTemplateException(template, e);
		}
		return out;
	}

	private static Unsafe unsafeHack() throws UnsafeException {
		try {
			final var instance = Unsafe.class.getDeclaredField("theUnsafe");
			instance.setAccessible(true);
			return (Unsafe) instance.get(null);
		} catch(final ReflectiveOperationException e) {
			throw new UnsafeException(e);
		}
	}

}
