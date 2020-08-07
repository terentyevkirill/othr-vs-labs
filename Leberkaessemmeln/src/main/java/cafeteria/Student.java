package cafeteria;

import java.util.Random;

public class Student implements Runnable {
    private KitchenCounter theke;
    public Student(KitchenCounter theke) {
        this.theke = theke;
    }

    @Override
    public void run() {
        Random rnd = new Random();
        while(true) {
            try {
                long eatingTime = 2000 + rnd.nextInt(4000);
                Thread.sleep(eatingTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            theke.take();
        }
    }
}
