package test.java;

import org.junit.jupiter.api.Test;
import xyz.cliserkad.smp.BadTemplateException;
import xyz.cliserkad.smp.TemplateParser;
import xyz.cliserkad.smp.UnsafeException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static xyz.cliserkad.smp.SimpleEncoder.encode;

public class TemplateTest {

	public static void main(final String[] args) throws BadTemplateException, IllegalAccessException, UnsafeException {
		System.out.println(TemplateParser.parse(encode(new Car()), Car.class));
		new TemplateTest().testSimple();
	}

	@Test
	public void testSimple() throws IllegalAccessException, BadTemplateException, UnsafeException {
		final var parsed = TemplateParser.parse(encode(new Car()), Car.class);
		final var base = new Car();
		assertEquals(base.year, parsed.year);
		assertEquals(base.model, parsed.model);
		assertEquals(base.type, parsed.type);
	}

}
