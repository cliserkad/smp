package test.java;

import org.junit.jupiter.api.Test;
import java.io.IOException;
import static com.xarql.smp.ToSMP.encode;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EncodeTest {

	public static final String EXPECTED = "{year:1998;model:\"Honda Accord\";engine:{cylinders:6;name:\"VTEC\";};}";

	@Test
	public void testCar() throws IllegalAccessException, IOException {
		assertEquals(encode(new Car()), EXPECTED);
	}

}
