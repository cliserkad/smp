package test.java;

import static com.xarql.smp.SimpleEncoder.encode;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import com.xarql.smp.Verifier;

public class EncodeTest {

	public static final String EXPECTED = "{year:1998;model:\"Honda Accord\";engine:{cylinders:6;name:\"VTEC\";mpg:14.834;};type:'c';driveModes:['p','r','n','d','l',];}";

	@Test
	public void testCar() throws IllegalAccessException {
		assertEquals(EXPECTED, encode(new Car()));
		assert Verifier.verifyOrPrint(encode(new Car()));
	}

}
