package downloader;


import javax.swing.JProgressBar;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

// aktive Klasse
public class Download implements Runnable {

    private final JProgressBar progressBar;
    // weitere Attribute zur Synchronisation hier definieren
    private final CountDownLatch start;
    private final CyclicBarrier target;

    public Download(JProgressBar progressBar, CountDownLatch start, CyclicBarrier target) {
        this.progressBar = progressBar;
        this.start = start;
        this.target = target;
    }

    @Override
    public void run() {
        // wait for button click
        try {
            start.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Random rnd = new Random();
        for (int progress = 0; progress <= 100; progress++) {
            progressBar.setValue(progress);
            try {
                long waitingTime = rnd.nextInt(300);
                Thread.sleep(waitingTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // wait for the slowest
        try {
            target.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }


    /*  hier die Methode definieren, die jeweils den Balken weiterschiebt
     *  Mit balken.getMaximum() bekommt man den Wert fuer 100 % gefuellt
     *  Mit balken.setValue(...) kann man den Balken einstellen (wieviel gefuellt) dargestellt wird
     *  Setzen Sie den value jeweils und legen Sie die Methode dann für eine zufaellige Zeit schlafen
     */


}
