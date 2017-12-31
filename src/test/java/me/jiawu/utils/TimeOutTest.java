package me.jiawu.utils;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.google.common.util.concurrent.SimpleTimeLimiter;
import org.junit.Test;

/**
 * @author wuzhong on 2017/12/30.
 * @version 1.0
 */
public class TimeOutTest {

    @Test
    public void test() throws InterruptedException, ExecutionException {

        SimpleTimeLimiter simpleTimeLimiter = SimpleTimeLimiter.create(Executors.newCachedThreadPool());

        try {
            Long aLong = simpleTimeLimiter.callUninterruptiblyWithTimeout(() -> {
                return getValueFromServer();
            }, 1, TimeUnit.SECONDS);
            System.out.println(aLong);
        } catch (TimeoutException exc) {
            //exc.printStackTrace();
            System.out.println(11);
        }

    }

    private long getValueFromServer() {
        try {
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return System.currentTimeMillis();
    }

}
