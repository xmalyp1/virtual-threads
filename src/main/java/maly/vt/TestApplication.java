package maly.vt;

import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

public class TestApplication {
    private static final int SLEEP_MILLIS = 4000;
    public static void main(String[] args){
        long start = System.currentTimeMillis();
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 0; i < 100; i++) {
                int finalI = i;
                executor.submit(() -> pinnedSleep(finalI));
            }
        }
        System.out.printf("Execution time: %f seconds%n",(System.currentTimeMillis()-start)/1000.00);
    }

    private static synchronized void pinnedSleep(int index){
        sleep(index);
    }
    private static void sleep(int index) {
        try {
            Thread.sleep(SLEEP_MILLIS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }finally{
            System.out.println(String.format("Finished - %d index of tread id %d",index,Thread.currentThread().threadId()));
        }
    }
}
