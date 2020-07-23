package cafeteria.repeat;

import java.util.Random;

// aktive Klasse
public class Student implements Runnable {
    private String name;
    private final Theke theke;

    public Student(String name, Theke theke) {
        this.name = name;
        this.theke = theke;
    }

    @Override
    public void run() {
        Random rnd = new Random();
        while (true) {
            try {
                Thread.sleep(3000 + rnd.nextInt(4000));
                System.out.println(this.name + " möchte ein Leberkässemmel kaufen");
                theke.nehmen();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                '}';
    }
}
