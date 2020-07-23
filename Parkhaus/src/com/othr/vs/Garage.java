package com.othr.vs;

import java.util.Deque;
import java.util.LinkedList;

public class Garage {
    private int capacity;
    private Deque<Car> cars = new LinkedList<>();
    private final Object monitor = new Object();

    public Garage(int capacity) {
        this.capacity = capacity;
    }

    public void driveIn(Car car) {
        synchronized (monitor) {
            while (cars.size() == capacity) {
                try {
                    System.out.println("    Warten an Schranke: " + car.toString());
                    monitor.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            cars.addLast(car);
            System.out.println("Einfahrt: " + car.toString());
            monitor.notifyAll();
        }
    }

    public Car driveOut() {
        synchronized (monitor) {
            while (cars.isEmpty()) {
                try {
                    monitor.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Car car = cars.removeFirst();
            System.out.println("Ausfahrt: " + car.toString());
            monitor.notifyAll();
            return car;
        }
    }
}
