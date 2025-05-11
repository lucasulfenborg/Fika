package fika;

import java.util.concurrent.ConcurrentLinkedQueue;

//A concurrentLinkedQueue as a thread safe coffee queue
public class ConcurrentResources {
    public ConcurrentLinkedQueue<Coffee> readyCoffees = new ConcurrentLinkedQueue<>();
    public ConcurrentLinkedQueue<Worker> workersInQueue = new ConcurrentLinkedQueue<>();
}
