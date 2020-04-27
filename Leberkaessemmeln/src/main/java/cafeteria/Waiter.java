package cafeteria;

import java.util.Random;

public class Waiter implements Runnable {
    private KitchenCounter bar;
    public Waiter(KitchenCounter bar) {
        this.bar = bar;
    }

    @Override
    public void run() {
        Random rnd = new Random();
        while (true) {
            try {
                long cookingTime = rnd.nextInt(1000);
                Thread.sleep(cookingTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            bar.put();
        }
    }
}
