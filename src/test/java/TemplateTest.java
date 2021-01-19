package test.java;

import com.xarql.smp.BadTemplateException;
import com.xarql.smp.TemplateParser;
import com.xarql.smp.UnsafeException;
import com.xarql.smp.Verifier;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static com.xarql.smp.SimpleEncoder.encode;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TemplateTest {

	public static void main(String[] args) throws BadTemplateException, IllegalAccessException, UnsafeException {
		System.out.println(TemplateParser.parse(encode(new Car()), Car.class));
		new TemplateTest().testSimple();
	}

	@Test
	public void testSimple() throws IllegalAccessException, BadTemplateException, UnsafeException {
		final Car parsed = TemplateParser.parse(encode(new Car()), Car.class);
		final Car base = new Car();
		assertEquals(base.year, parsed.year);
		assertEquals(base.model, parsed.model);
		assertEquals(base.type, parsed.type);
	}

}
