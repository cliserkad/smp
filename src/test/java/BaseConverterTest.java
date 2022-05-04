package test.java;

import com.xarql.util.BaseConverter;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BaseConverterTest {

    @Test
    public void testLowestInRange() {
        final ExecutorService threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        for (var a = 2; a <= BaseConverter.MAX_SUPPORTED_BASE; a++) {
            final int base = a;
            threadPool.execute(() -> {
                // test the lowest 1024 numbers in the supported range
                for (int num = 0; num < 1024; num++) {
                    assertEquals(num, BaseConverter.toNumber(BaseConverter.toString(num, base), base));
                }
            });
        }
    }

    @Test
    public void testHighestInRangeTest() {
        final ExecutorService threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        for (var a = 2; a <= BaseConverter.MAX_SUPPORTED_BASE; a++) {
            final int base = a;
            threadPool.execute(() -> {
                // test the highest 1024 numbers in the supported range
                for(int num = Integer.MAX_VALUE; num > Integer.MAX_VALUE - 1024; num--) {
                    assertEquals(num, BaseConverter.toNumber(BaseConverter.toString(num, base), base));
                }
            });
        }
    }

}
