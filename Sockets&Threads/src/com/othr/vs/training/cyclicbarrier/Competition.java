package com.othr.vs.training.cyclicbarrier;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

public class Competition {

    public static void main(String[] args) throws InterruptedException {
        CyclicBarrier barrier = new CyclicBarrier(5);
        Runnable r1 = new Runner(barrier, "Alex");
        Runnable r2 = new Runner(barrier, "Ivan");
        Runnable r3 = new Runner(barrier, "Jonas");
        Runnable r4 = new Runner(barrier, "Kyrylo");
        Runnable r5 = new Runner(barrier, "Daniel");
        for (Runnable r :
                List.of(r1, r2, r3, r4, r5)) {
            TimeUnit.SECONDS.sleep(1 + new Random().nextInt(5));
            new Thread(r, ((Runner)r).getName()).start();
        }
    }
}
