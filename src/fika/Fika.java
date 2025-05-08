package fika;

public class Fika {
	public static int simulationTime = 99999;
    public static int timeScale = 100;
    public static int numberOfWorkersAtWork = 0;
	
	public static void main(String[] args) {
		ConcurrentResources resources = new ConcurrentResources();

    Worker erik = new Worker("Erik", resources);
    Worker simon = new Worker("Simon", resources);
    Worker adrian = new Worker("Adrian", resources);
    Worker nora = new Worker("Nora", resources);
    CoffeeMachine machine = new CoffeeMachine(resources);

	
	machine.start();
    erik.start();
    simon.start();
    adrian.start();
    nora.start();

    try {
        Thread.sleep(simulationTime * 1000 / timeScale);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }

    System.exit(0);

	}
}