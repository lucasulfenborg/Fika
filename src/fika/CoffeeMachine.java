package fika;

public class CoffeeMachine extends Thread {
	private final ConcurrentResources resources;
	private final int serveCoffeeTime;
	private final int brewingTime;

	public CoffeeMachine(ConcurrentResources resources) {
		this.resources = resources;
		this.serveCoffeeTime = 1000 / Fika.getTimeScale();
		this.brewingTime = 2000 / Fika.getTimeScale();
	}

	//Method for brewing a coffee
	private Coffee createRandomCoffee() {
		int type = Utilities.generateRandomNumber(0, 2);
		switch (type) {
		case 0: return new BlackCoffee();
		case 1: return new Cappuccino();
		case 2: return new Latte();
		default: return null;
		}
	}

	//Method for serving a coffee
	private void serveCoffee() {
		Coffee coffee = resources.readyCoffees.poll();
		Worker worker = resources.workersInQueue.poll();
		worker.addEnergy(coffee.getEnergy());
		System.out.println(worker.getWorkerName() + " enjoyed a " + coffee.getClass().getSimpleName() +
				" with " + coffee.getEnergy() + " energy");

		if (worker.getCurrentEnergy() < 100) {
			resources.workersInQueue.add(worker);
		}
	}

	private void brewCoffee() {
		resources.readyCoffees.add(createRandomCoffee());
		System.out.println("Drink Created. Coffee Machine has " + resources.readyCoffees.size() + " drinks.");
	}



	//Run method when coffeMachine thread starts
	@Override
	public void run() {
		try {
			while (true) {
				if (Fika.getNumberOfWorkersAtWork() > 0) {

					synchronized (resources.readyCoffees) { 
						//Serve coffee if there is ready coffee and there's people in the queue
						if (!resources.readyCoffees.isEmpty() && !resources.workersInQueue.isEmpty()) {
							Thread.sleep(serveCoffeeTime);
							serveCoffee();
						}

						//Otherwise, brew a coffee if theres under 20 coffees ready
						else if (resources.readyCoffees.size() < 20) {
							Thread.sleep(brewingTime);
							brewCoffee();
						}
					}
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
