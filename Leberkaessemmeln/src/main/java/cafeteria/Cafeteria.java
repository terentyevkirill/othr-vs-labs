package cafeteria;

public class Cafeteria {
    public static void main(String[] args) {
        KitchenCounter bar = new KitchenCounter(4);
        new Thread(new Waiter(bar)).start();
        new Thread(new Waiter(bar)).start();
        for (int i = 1; i <= 8; i++) {
            new Thread(new Student(bar)).start();
        }

    }
}
