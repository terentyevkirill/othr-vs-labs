package cafeteria;

import java.util.Random;

public class Waiter implements Runnable {
    private KitchenCounter theke;
    public Waiter(KitchenCounter theke) {
        this.theke = theke;
    }

    @Override
    public void run() {
        Random rnd = new Random();
        while (true) {
            try {
                long cookingTime = 1000 + rnd.nextInt(1000);
                Thread.sleep(cookingTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            theke.put();
        }
    }
}
