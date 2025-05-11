package fika;

public class Worker extends Thread {
	//Workers have a name, energy, and rate of energy loss
	private final String name;
	private int currentEnergy;
	private final int energyDepletionRate;
	private final ConcurrentResources resources;

	public Worker(String name, ConcurrentResources resources) {
		this.name = name;
		this.resources = resources;
		this.currentEnergy = Utilities.generateRandomNumber(30, 90);
		this.energyDepletionRate = Utilities.generateRandomNumber(500, 1500);
	}

	@Override
	public void run() {
		try {
			//Go to work
			Fika.incrementWorkersAtWork();

			while (true) {
				Thread.sleep(energyDepletionRate / Fika.getTimeScale());
				this.currentEnergy--;

				synchronized (resources.workersInQueue) {
					if (resources.workersInQueue.contains(this)) {
						System.out.println(name + " is taking a break with energy " + currentEnergy);

						//Go home if he has no more energy
						if (currentEnergy <= 0) {
							System.out.println(name + " went home.");
							Fika.decrementWorkersAtWork();
							resources.workersInQueue.remove(this);
							return;
						}
					} else if (currentEnergy < 30 && currentEnergy > 0) {
						resources.workersInQueue.add(this);
					} else {
						System.out.println(name + " is working with energy " + currentEnergy);
					}
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void addEnergy(int energy) {
		this.currentEnergy += energy;
	}

	public int getCurrentEnergy() {
		return currentEnergy;
	}

	public String getWorkerName() {
		return name;
	}
}
