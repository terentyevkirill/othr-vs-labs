package cafeteria.repeat;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// passive Klasse
public class Theke {
    private final int kapazitaet;
    private final Lock lock = new ReentrantLock();
    private final Condition kellner = lock.newCondition();
    private final Condition studenten = lock.newCondition();
    private int bunCounter;

    public Theke(int kapazitaet) {
        this.kapazitaet = kapazitaet;
    }

    public void liegen() {
        try {
            lock.lock();
            while (bunCounter == kapazitaet) {
                System.out.println("Kellner warten auf liegen (bunCounter: " + bunCounter + ")");
                kellner.await();
            }
            bunCounter++;
            System.out.println("Leberkässemmel gelegt (bunCounter: " + bunCounter + ")");
            studenten.signal(); 
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void nehmen() {
        try {
            lock.lock();
            while (bunCounter == 0) {
                System.out.println("Studenten warten auf nehmen (bunCounter: " + bunCounter + ")");
                studenten.await();
            }
            bunCounter--;
            System.out.println("Leberkässemmel verkauft (bunCounter: " + bunCounter + ")");
            kellner.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

}
