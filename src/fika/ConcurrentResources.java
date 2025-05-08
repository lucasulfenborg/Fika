package fika;

import java.util.concurrent.ConcurrentLinkedQueue;

public class ConcurrentResources {
    public ConcurrentLinkedQueue<Coffee> readyCoffees = new ConcurrentLinkedQueue<>();
    public ConcurrentLinkedQueue<Worker> workersInQueue = new ConcurrentLinkedQueue<>();

    public Coffee createRandomCoffee() {
        int type = Utilities.generateRandomNumber(0, 2);
        switch (type) {
            case 0: return new BlackCoffee();
            case 1: return new Cappuccino();
            case 2: return new Latte();
            default: return null;
        }
    }
}
