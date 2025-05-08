package fika;

public class CoffeeMachine extends Thread {
    private final ConcurrentResources resources;
    private final int timeBetweenServes;
    private final int brewingTime;

    public CoffeeMachine(ConcurrentResources resources) {
        this.resources = resources;
        this.timeBetweenServes = 1000 / Fika.timeScale;
        this.brewingTime = (2000 - timeBetweenServes) / Fika.timeScale;
    }

    @Override
    public void run() {
        try {
            while (true) {
                if (Fika.numberOfWorkersAtWork > 0) {
                    Thread.sleep(timeBetweenServes);

                    synchronized (resources.readyCoffees) {
                        if (!resources.readyCoffees.isEmpty() && !resources.workersInQueue.isEmpty()) {
                            Coffee coffee = resources.readyCoffees.poll();
                            Worker worker = resources.workersInQueue.poll();
                            worker.addEnergy(coffee.getEnergy());
                            System.out.println(worker.getWorkerName() + " enjoyed a " + coffee.getClass().getSimpleName() +
                                    " with " + coffee.getEnergy() + " energy");

                            if (worker.getCurrentEnergy() < 100) {
                                resources.workersInQueue.add(worker);
                            }
                        }
                    }

                    if (resources.readyCoffees.size() < 20) {
                        Thread.sleep(brewingTime);
                        resources.readyCoffees.add(resources.createRandomCoffee());
                        System.out.println("Drink Created. Coffee Machine has " + resources.readyCoffees.size() + " drinks.");
                    }
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
