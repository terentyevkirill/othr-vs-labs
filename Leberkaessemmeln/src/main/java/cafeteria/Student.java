package cafeteria;

import java.util.Random;

public class Student implements Runnable {
    private KitchenCounter bar;
    public Student(KitchenCounter bar) {
        this.bar = bar;
    }

    @Override
    public void run() {
        Random rnd = new Random();
        while(true) {
            try {
                long eatingTime = rnd.nextInt(4000);
                Thread.sleep(eatingTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            bar.take();
        }
    }
}
