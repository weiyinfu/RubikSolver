package cn.weiyinfu.rubik.legacy;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class JavaConcurrent {
    public static void main(String[] args) throws InterruptedException {
        AtomicInteger s = new AtomicInteger(0);
        var x = Executors.newFixedThreadPool(100);
        var c = new CountDownLatch(1000);
        for (int i = 0; i < 1000; i++) {
            x.submit(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                var y = s.addAndGet(1);
                System.out.println(y);
                c.countDown();
            });
        }
        x.shutdown();
        c.await();
        System.out.println(s.get() + "====");
    }
}
