package com.othr.vs.training.beers;

import java.util.concurrent.TimeUnit;

public class Oktoberfest {

    public static void main(String[] args) throws InterruptedException {
        Bar bar = new Bar(10);
        for (int i = 0; i < 4; i++) {
            Kellner k = new Kellner("Kellner-" + (i + 1), bar);
            Thread t = new Thread(k);
            t.setName(k.getName());
            t.setDaemon(true);
            t.start();
        }
        for (int i = 0; i < 20; i++) {
            Kunde k = new Kunde("Kunde-" + (i + 1), bar);
            Thread t = new Thread(k);
            t.setName(k.getName());
            t.setDaemon(true);
            t.start();
        }

        TimeUnit.SECONDS.sleep(30);
        System.out.println("End of simulation");
    }
}
