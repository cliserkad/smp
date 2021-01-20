package test.java;

import static com.xarql.smp.SimpleEncoder.encode;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import com.xarql.smp.BadTemplateException;
import com.xarql.smp.TemplateParser;
import com.xarql.smp.UnsafeException;

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
