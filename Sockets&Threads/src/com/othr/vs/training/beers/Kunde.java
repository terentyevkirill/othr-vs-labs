package com.othr.vs.training.beers;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Kunde implements Runnable {
    private final Bar bar;
    private final String name;

    public Kunde(String name, Bar bar) {
        this.name = name;
        this.bar = bar;
    }

    @Override
    public void run() {
        while (true) {
            try {
                TimeUnit.MILLISECONDS.sleep(3000 + new Random().nextInt(5000));
                System.out.println(name + " wants to take a beer");
                bar.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public String getName() {
        return name;
    }
}
