package com.othr.vs.skibarrier;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

public class ZeitlicheSynchronisation {
    public static void main(String[] args) throws InterruptedException {
        CyclicBarrier schranke = new CyclicBarrier(3);
        Skifahrer ski1 = new Skifahrer(schranke);
        Skifahrer ski2 = new Skifahrer(schranke);
        Skifahrer ski3 = new Skifahrer(schranke);
        Skifahrer ski4 = new Skifahrer(schranke);
        Skifahrer ski5 = new Skifahrer(schranke);
        Skifahrer ski6 = new Skifahrer(schranke);
        for (Skifahrer e : List.of(ski1, ski2, ski3, ski4, ski5, ski6)) {
            TimeUnit.SECONDS.sleep(new Random().nextInt(5));
            new Thread(e).start();
        }
    }
}
