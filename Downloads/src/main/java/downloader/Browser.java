package downloader;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class Browser extends JFrame implements ActionListener {

    private JProgressBar[] progressBars;
    private JButton startButton;

    // Deklaration Ihrer Synchronisations-Hilfsklassen hier:
    private final CountDownLatch start;
    private final CyclicBarrier target;

    public Browser(int downloads) {
        super("Mein Download-Browser");

        // Initialisierung Ihrer Synchronisations-Hilfsklassen hier:
        start = new CountDownLatch(1);
        target = new CyclicBarrier(5 + 1);  //  +1 - waiting thread


        // Aufbau der GUI-Elemente:
        progressBars = new JProgressBar[downloads];
        JPanel rows = new JPanel(new GridLayout(downloads, 1));

        for (int i = 0; i < downloads; i++) {
            JPanel line = new JPanel(new FlowLayout(FlowLayout.LEADING, 0, 10));
            progressBars[i] = new JProgressBar(0, 100);
            progressBars[i].setPreferredSize(new Dimension(500, 20));
            line.add(progressBars[i]);
            rows.add(line);


            // neue Download-Threads erzeugen und starten
            // ggf. müssen Synchronisations-Objekte im Konstruktor übergeben werden!!
            // balken ist ebenfalls zu übergeben!
            new Thread(new Download(progressBars[i], start, target)).start();


        }

        startButton = new JButton("Downloads starten");
        startButton.addActionListener(this);

        this.add(rows, BorderLayout.CENTER);
        this.add(startButton, BorderLayout.SOUTH);


        pack();
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        new Browser(5);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Blockierte Threads jetzt laufen lassen:
        start.countDown();

        startButton.setEnabled(false);
        startButton.setSelected(false);
        startButton.setText("Downloads laufen...");

        // Auf Ende aller Download-Threads warten ... erst dann die Beschriftung ändern
        // Achtung, damit die Oberflaeche "reaktiv" bleibt dies in einem eigenen Runnable ausfuehren!
        new Thread(() -> {
            try {
                target.await();
            } catch (InterruptedException | BrokenBarrierException ex) {
                ex.printStackTrace();
            }
            startButton.setText("Ende");
        }).start();

    }

}
