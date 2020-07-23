package cafeteria;

public class Cafeteria {
    public static void main(String[] args) {
        KitchenCounter theke = new KitchenCounter(4);
        new Thread(new Waiter(theke)).start();
        new Thread(new Waiter(theke)).start();
        for (int i = 1; i <= 8; i++) {
            new Thread(new Student(theke)).start();
        }

    }
}
