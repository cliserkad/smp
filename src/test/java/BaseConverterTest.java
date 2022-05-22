package test.java;

import com.xarql.util.BaseConverter;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BaseConverterTest {

    public static final int INNER_TEST_ITERATIONS = 128;

    @Test
    public void testLowestInRange() throws InterruptedException {
        final ExecutorService threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        for (var a = 2; a <= BaseConverter.MAX_SUPPORTED_BASE; a++) {
            final int base = a;
            threadPool.execute(() -> {
                // test the lowest 128 numbers in the supported range
                for (int num = 0; num < INNER_TEST_ITERATIONS; num++) {
                    assertEquals(num, BaseConverter.toNumber(BaseConverter.toString(num, base), base));
                }
            });
        }
        threadPool.shutdown();
        threadPool.awaitTermination(1000, TimeUnit.SECONDS);
    }

    @Test
    public void testHighestInRangeTest() throws InterruptedException {
        final ExecutorService threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        for (var a = 2; a <= BaseConverter.MAX_SUPPORTED_BASE; a++) {
            final int base = a;
            threadPool.execute(() -> {
                // test the highest 128 numbers in the supported range
                for(int num = Integer.MAX_VALUE; num > Integer.MAX_VALUE - INNER_TEST_ITERATIONS; num--) {
                    assertEquals(num, BaseConverter.toNumber(BaseConverter.toString(num, base), base));
                }
            });
        }
        threadPool.shutdown();
        threadPool.awaitTermination(1000, TimeUnit.SECONDS);
    }

}
