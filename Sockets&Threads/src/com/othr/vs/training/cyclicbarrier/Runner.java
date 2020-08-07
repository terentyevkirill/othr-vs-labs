package com.othr.vs.training.cyclicbarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Runner implements Runnable {

    private final String name;
    private final CyclicBarrier barrier;

    public Runner(CyclicBarrier barrier, String name) {
        this.barrier = barrier;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public void run() {
        try {
            System.out.println("Ready: " + this.name + ", waiting for " + (barrier.getParties() - barrier.getNumberWaiting() - 1) + " more");
            barrier.await();
            System.out.println("Go: " + this.name);
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}
