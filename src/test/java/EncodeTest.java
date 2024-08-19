package test.java;

import org.junit.jupiter.api.Test;
import xyz.cliserkad.smp.Verifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static xyz.cliserkad.smp.SimpleEncoder.encode;

public class EncodeTest {

	public static final String EXPECTED = "{year:1998;model:\"Honda Accord\";engine:{cylinders:6;name:\"VTEC\";mpg:14.834;};type:'c';driveModes:['p','r','n','d','l',];}";

	@Test
	public void testCar() throws IllegalAccessException {
		assertEquals(EXPECTED, encode(new Car()));
		assert Verifier.verifyOrPrint(encode(new Car()));
	}

}
