package me.jiawu.utils;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.SettableFuture;
import org.junit.Test;

/**
 * @author wuzhong on 2017/12/30.
 * @version 1.0
 */
public class FutureTest {

    @Test
    public void test() throws ExecutionException, InterruptedException {

        ListeningExecutorService listeningExecutorService = MoreExecutors.newDirectExecutorService();

        ListenableFuture<Object> submit1 = listeningExecutorService.submit(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                Thread.sleep(300);
                System.out.println("sub 1 exe finish");
                return null;
            }
        });

        ListenableFuture<Object> submit2 = listeningExecutorService.submit(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                Thread.sleep(400);
                return null;
            }
        });

        ListenableFuture<Object> submit3 = listeningExecutorService.submit(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                Thread.sleep(100);
                return null;
            }
        });

        submit1.addListener(new Runnable() {
            @Override
            public void run() {
                System.out.println("1111");
            }
        }, listeningExecutorService);

        submit1.get();

        System.out.println(">>>>");

    }


    SettableFuture<Object> future;

    @Test
    public void test2() throws IOException, InterruptedException {

        ExecutorService executorService = Executors.newSingleThreadExecutor();

        executorService.execute(new Runnable() {
            @Override
            public void run() {

                int i = 0;

                while (true) {

                    System.out.println(i++);

                    future = SettableFuture.create();

                    //future.cancel(true);

                    try {
                        future.get(2, TimeUnit.SECONDS);
                    } catch (InterruptedException | ExecutionException | TimeoutException e) {
                        //e.printStackTrace();
                    }
                }

            }
        });

        while (true) {
            System.out.println(">>");
            if (null != future){
                future.set(true);
                //future.cancel(true);
                Thread.sleep(1500L);
            }

            //System.in.read();



        }

    }

}
