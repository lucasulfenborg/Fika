package fika;

import java.util.concurrent.ConcurrentLinkedQueue;

public class ConcurrentResources {
    public ConcurrentLinkedQueue<Coffee> readyCoffees = new ConcurrentLinkedQueue<>();
    public ConcurrentLinkedQueue<Worker> workersInQueue = new ConcurrentLinkedQueue<>();
}
