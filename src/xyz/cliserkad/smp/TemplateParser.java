package xyz.cliserkad.smp;

import sun.misc.Unsafe;
import xyz.cliserkad.util.Path;
import xyz.cliserkad.util.PlaceHolder;

import java.lang.reflect.Field;

import static xyz.cliserkad.smp.SimpleEncoder.encode;

/**
 * Attempts to instantiate a template and fill it with the values from smp
 * TODO: Rewrite to reduce unsafe usage
 * TODO: Support complex / nested objects
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
		Template out;
		final var data = GenericParser.parse(smp);
		try {
			out = template.getDeclaredConstructor().newInstance();

			for(final Field field : template.getFields()) {
				// a bunch of weird hacks to inject the value directly in the VM
				final var fieldOffset = getUnsafe().objectFieldOffset(field);
				final var obj = data.get(new Path(field.getName()));
				if(field.getType().isArray()) {
					assert obj.getClass().isArray();
					getUnsafe().putObject(out, fieldOffset, obj);
				} else if(field.getType().equals(boolean.class)) {
					getUnsafe().putBoolean(out, fieldOffset, (boolean) obj);
				} else if(field.getType().equals(Boolean.class)) {
					getUnsafe().putBoolean(out, fieldOffset, (Boolean) obj);
				} else if(field.getType().equals(byte.class)) {
					getUnsafe().putByte(out, fieldOffset, (byte) obj);
				} else if(field.getType().equals(Byte.class)) {
					getUnsafe().putByte(out, fieldOffset, (Byte) obj);
				} else if(field.getType().equals(short.class)) {
					getUnsafe().putShort(out, fieldOffset, (short) obj);
				} else if(field.getType().equals(Short.class)) {
					getUnsafe().putShort(out, fieldOffset, (Short) obj);
				} else if(field.getType().equals(char.class)) {
					getUnsafe().putChar(out, fieldOffset, (char) obj);
				} else if(field.getType().equals(Character.class)) {
					getUnsafe().putChar(out, fieldOffset, (Character) obj);
				} else if(field.getType().equals(int.class)) {
					getUnsafe().putInt(out, fieldOffset, (int) obj);
				} else if(field.getType().equals(Integer.class)) {
					getUnsafe().putInt(out, fieldOffset, (Integer) obj);
				} else if(field.getType().equals(float.class)) {
					getUnsafe().putFloat(out, fieldOffset, (float) obj);
				} else if(field.getType().equals(Float.class)) {
					getUnsafe().putFloat(out, fieldOffset, (Float) obj);
				} else if(field.getType().equals(long.class)) {
					getUnsafe().putLong(out, fieldOffset, (long) obj);
				} else if(field.getType().equals(Long.class)) {
					getUnsafe().putLong(out, fieldOffset, (Long) obj);
				} else if(field.getType().equals(double.class)) {
					getUnsafe().putDouble(out, fieldOffset, (double) obj);
				} else if(field.getType().equals(Double.class)) {
					getUnsafe().putDouble(out, fieldOffset, (Double) obj);
				} else if(field.getType().equals(String.class)) {
					getUnsafe().putObject(out, fieldOffset, (String) obj);
				} else if(field.getType().equals(obj.getClass())) {
					System.out.println("Field: " + field.getType());
					System.out.println("Object: " + obj.getClass());
					getUnsafe().putObject(out, fieldOffset, parse(encode(obj), field.getType()));
				} else if(!obj.getClass().equals(PlaceHolder.class)) {
					System.out.println("Unsupported type: " + field.getType());
					System.out.println("Value: " + obj);
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
