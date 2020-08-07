package cafeteria;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class KitchenCounter {
    private final int capacity;
    private int bunCounter = 0;
    private final Lock monitor = new ReentrantLock();
    // used for condition when counter is full
    private final Condition waiters = monitor.newCondition();
    // used for condition when counter is empty
    private final Condition students = monitor.newCondition();

    public KitchenCounter(int capacity) {
        this.capacity = capacity;
    }

    public void put() {
        try {
            monitor.lock();
            while (bunCounter == capacity) {    // if counter is full
                System.out.println("put - waiting   bunCounter: " + bunCounter);
                // waiters are awaiting
                waiters.await();
            }
            bunCounter++;
            System.out.println("Bun is cooked. There are now " + bunCounter + " on the counter.");
            students.signal();  // signalize students that new bun is ready
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            monitor.unlock();
        }
    }

    public void take() {
        try {
            monitor.lock();
            while (bunCounter == 0) {   // if the counter is empty
                System.out.println("take - waiting   bunCounter: " + bunCounter);
                // students are waiting
                students.await();
            }
            bunCounter--;
            System.out.println("Bun is sold. There are now " + bunCounter + " on the counter.");
            waiters.signal();   // signalize waiters that bun was sold
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            monitor.unlock();
        }
    }
}
