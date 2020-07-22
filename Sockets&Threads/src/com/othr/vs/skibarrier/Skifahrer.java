package com.othr.vs.skibarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Skifahrer implements Runnable {
    CyclicBarrier schranke;

    public Skifahrer(CyclicBarrier schranke) {
        this.schranke = schranke;
    }

    @Override
    public void run() {
        try {
            System.out.println("Anstellen");
            schranke.await();
            System.out.println("Liftfahren");
        } catch (InterruptedException | BrokenBarrierException e) {
            // ignore
        }
    }
}
