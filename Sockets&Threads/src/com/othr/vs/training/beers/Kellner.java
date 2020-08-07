package com.othr.vs.training.beers;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Kellner implements Runnable {
    private final Bar bar;
    private final String name;

    public Kellner(String name, Bar bar) {
        this.name = name;
        this.bar = bar;
    }

    @Override
    public void run() {
        while (true) {
            try {
                TimeUnit.MILLISECONDS.sleep(500 + new Random().nextInt(1500));
                System.out.println(name + " wants to put a beer");
                bar.put();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public String getName() {
        return name;
    }
}
