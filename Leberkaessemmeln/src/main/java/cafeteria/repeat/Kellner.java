package cafeteria.repeat;

import java.util.Random;

// aktive Klasse
public class Kellner implements Runnable {
    private String name;
    private final Theke theke;

    public Kellner(String name, Theke theke) {
        this.name = name;
        this.theke = theke;
    }

    @Override
    public void run() {
        Random rnd = new Random();
        while (true) {
            try {
                Thread.sleep(1000 + rnd.nextInt(2000));
                System.out.println(this.name + " möchte ein Leberkässemmel liegen");
                theke.liegen();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String toString() {
        return "Kellner{" +
                "name='" + name + '\'' +
                '}';
    }
}
