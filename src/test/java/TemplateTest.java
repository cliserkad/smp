package test.java;

import org.junit.jupiter.api.Test;
import xyz.cliserkad.smp.BadTemplateException;
import xyz.cliserkad.smp.TemplateParser;
import xyz.cliserkad.smp.UnsafeException;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static xyz.cliserkad.smp.SimpleEncoder.encode;
import static xyz.cliserkad.smp.SimpleEncoder.prettyPrint;

public class TemplateTest {

	public static void main(final String[] args) throws BadTemplateException, IllegalAccessException, UnsafeException {
		System.out.println(prettyPrint(encode(new Car())));
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
		assertArrayEquals(base.driveModes, parsed.driveModes);
		assertEquals(base.engine.cylinders, parsed.engine.cylinders);
		assertEquals(base.engine.name, parsed.engine.name);
		assertEquals(base.engine.mpg, parsed.engine.mpg);
	}

}
