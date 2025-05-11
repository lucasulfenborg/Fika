package fika;



public class Fika extends Thread{
	private static int simulationTime = 20;
	private static int timeScale = 1;
	private static int numberOfWorkersAtWork = 0;

	public static void main(String[] args) throws InterruptedException {

		Fika fika = new Fika();
		fika.startSimulation();

	}


	private void startSimulation() throws InterruptedException {
		ConcurrentResources resources = new ConcurrentResources();

		//Create instances of workers
		Worker erik = new Worker("Erik", resources);
		Worker simon = new Worker("Simon", resources);
		Worker adrian = new Worker("Adrian", resources);
		Worker nora = new Worker("Nora", resources);

		//Start their threads (make them go to work)
		erik.start();
		simon.start();
		adrian.start();
		nora.start();

		//Make a coffeemachine and start it
		CoffeeMachine machine = new CoffeeMachine(resources);
		machine.start();

		//Go trough simulation time before exiting the pr0ogram
		Thread.sleep((simulationTime / timeScale) * 1000);
		System.exit(0);
	}

	//Getters for important variables
	public static int getSimulationTime() {
		return simulationTime;
	}

	public static int getTimeScale() {
		return timeScale;
	}

	public static int getNumberOfWorkersAtWork() {
		return numberOfWorkersAtWork;
	}

	public static void incrementWorkersAtWork() {
		numberOfWorkersAtWork++;
	}

	public static void decrementWorkersAtWork() {
		numberOfWorkersAtWork--;
	}
}