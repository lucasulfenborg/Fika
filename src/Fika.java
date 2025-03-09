import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;


public class Fika {

	//Global variables
	public int simulationTime = 20;
	public int timeScale = 10; //e.g. 10 is 10 times faster 
	public int numberOfWorkersWorking = 0;
    public ConcurrentLinkedQueue<Coffee> readyCoffees = new ConcurrentLinkedQueue();
    public ConcurrentLinkedQueue<Worker> workersInQueue = new ConcurrentLinkedQueue<>();

	
    public static void main(String[] args) {
        Fika fika = new Fika();
        fika.startSimulation();
    }
    
    //Setup
    private void startSimulation() {

    	//Spawn the workers
    	Worker Erik  = new Worker("Erik");
    	Worker Simon  = new Worker("Simon");
    	Worker Adrian  = new Worker("Adrian");
    	Worker Nora  = new Worker("Nora");
    	
    	//Spawn the coffee machine
    	coffeeMachine coffeeMachine = new coffeeMachine();
    	
    	//Start the threads
    	coffeeMachine.start();
    	
    	Erik.start();
    	Simon.start();
    	Adrian.start();
    	Nora.start();

    	//Start the simulation time countdown
    	try {
    		Thread.sleep(simulationTime * 1000 / timeScale);
    	} catch (InterruptedException e) {
            e.printStackTrace();
        }

    	//Exit the program
        System.exit(0);
    	
    }

    // Method for generating a random integer
    private int RandomNumber(int min, int max) {
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }
     
    public class Worker extends Thread {
    	String name;
        int currentEnergy;
        int EnergyLoss;
        
        		
        public Worker (String name) {
        	this.name = name;
            this.currentEnergy = RandomNumber(30, 90);
            this.EnergyLoss = RandomNumber(500, 1500);
        }	
        
        @Override 
    	public void run() {
    		try {
    			
    			numberOfWorkersWorking++;
    			
    			while (true) {
					Thread.sleep(this.EnergyLoss / timeScale);
				    //Decrease energy by 1
			        this.currentEnergy --;

			        //Chilling in the coffee room
					if(this.currentEnergy < 30 && workersInQueue.contains(this) ) {
						System.out.println(this.name + " is taking a break with energy level " + this.currentEnergy);
						
						//Go home if no energy
						if (this.currentEnergy <= 0) {
							System.out.println(this.name + " is tired and went home.");
							numberOfWorkersWorking--;
							workersInQueue.remove(this);
							return;
						}
					}
			        
					//Enter coffee room queue if below 30 energy 
					else if ((this.currentEnergy < 30) && (this.currentEnergy > 0)) {
						workersInQueue.add(this);
					}
					
					//Work
					else if (!workersInQueue.contains(this)) {
						System.out.println(this.name + " is working with energy level " + this.currentEnergy);
					}
    			
    		}
    			}
				
			 catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	
    	}
    }
 
    
    public class coffeeMachine extends Thread{
    	int brewingTime = (2000 - 1000) / timeScale; //in milliseconds
    	int timeBetweenServes = 1000 / timeScale;
    	
    	@Override 
    	public void run() {
    		try {
    			while (true) {
    				
    				//Brew coffees only if there are workers
    				if (numberOfWorkersWorking > 0) {

						//Each second check for people in the coffee queue
						Thread.sleep(timeBetweenServes);
						
						//Check if coffee is available and if someone is in the queue
						if ((readyCoffees.size() >= 1) && (workersInQueue.size() >= 1)) {
							
							//Serve a coffee
							Coffee coffeeToDrink = readyCoffees.peek();
							Worker personToServe = workersInQueue.peek();		
							
							readyCoffees.poll();					
							personToServe.currentEnergy += coffeeToDrink.getEnergy();
							System.out.println(personToServe.name + " enjoyed a " + coffeeToDrink.getType() + " with " + coffeeToDrink.getEnergy() + " energy");
							
							//Remove him from the queue
							workersInQueue.poll();
							
							//Add to queue again if not 100 energy yet
							if (personToServe.currentEnergy < 100) {
								workersInQueue.add(personToServe);
							}
						}
						
						
						if (readyCoffees.size() < 20) {
							//Wait another second to brew a coffee
							Thread.sleep(this.brewingTime);
							readyCoffees.add(new Coffee());
							
							//Show that a coffee was added and total amount of ready coffees
							System.out.println("Drink Created. Coffee Machine has " + readyCoffees.size() + " drinks in reserve.");
						}
    				}
    			}
				
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}}
    
    public class Coffee{
    	private String type;
    	private int energy;
    	
    	public Coffee() {
    		String[] typesOfCoffee = {"blackCoffee", "cappuccino", "latte"};
    		
    		//Select a random type of coffee
    		int indexOfCoffeeType = RandomNumber(0, 2); 
    		this.type = typesOfCoffee[indexOfCoffeeType];
    		
    		//Select a random amount of energy based on coffee
    		switch(indexOfCoffeeType) {
    			case 0:
    				this.energy = RandomNumber(15, 20); //blackCoffee energy range
    				break;
    			case 1:
    				this.energy = RandomNumber(20, 30); //cappuccino energy range
    				break;
    			case 2:
    				this.energy = RandomNumber(25, 35);//latte energy range
    				break;
    		}
    	}
    	
    	protected String getType() {
    		return this.type;
    	}
    	
    	protected int getEnergy() {
    		return this.energy;
    	}
    	
    	
    }

    
    
    
}



