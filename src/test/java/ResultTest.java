package test.java;

import org.junit.jupiter.api.Test;
import xyz.cliserkad.util.Result;

public class ResultTest {

	@Test
	public void testResultVoidThing() {
		Result.of(() -> {
			throw new Exception("hi");
		}).onSuccess(() -> {
			System.out.println("yay it worked");
		});
	}

}
