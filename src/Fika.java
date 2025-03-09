import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;


public class Fika {

	//Global variables
	//SimulationTime and timeScale should be changed for testing scenarios
	public int simulationTime = 20; //Duration of the simulation
	public int timeScale = 1; //e.g. 10 is 10 times faster 
	
	
	public int numberOfWorkersAtWork = 0;
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
    	CoffeeMachine CoffeeMachine = new CoffeeMachine();
    	
    	//Start the threads 
    	//Makes the coffeeMachine start brewing coffee and serving workers while there are workers
    	CoffeeMachine.start();
    	
    	//Make the workers start working
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

    	
    	//After the simulation time has ended, the program will exit
        System.exit(0);
    	
    }

    // Method for generating a random integer
    private int generateRandomNumber(int min, int max) {
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }
     
    public class Worker extends Thread {
    	String name;
        int currentEnergy;
        int energyDepletionRate;
        
        		
        public Worker (String name) {
        	this.name = name;
        	//Set the starting energy of the worker to a random number from 30-90
            this.currentEnergy = generateRandomNumber(30, 90);
            //Set the speed of which energy will decrease
            this.energyDepletionRate = generateRandomNumber(500, 1500);
        }	
        
        
        //the run() method runs when the thread is started
        @Override 
    	public void run() {
    		try {
    			//Increment the amount of workers at work with one
    			//To keep track that people are at work
    			numberOfWorkersAtWork++;
    			
    			//the main loop of the thread
    			while (true) {
    				// Pause a given amount of time at the start of each iteration
    				//Makes it iterate as fast as the given energy depletion rate
					Thread.sleep(this.energyDepletionRate / timeScale);
				    //Decrease energy by 1
			        this.currentEnergy --;

			        synchronized (workersInQueue) {
				        //Check if the worker is in the queue
						if(workersInQueue.contains(this) ) {
							System.out.println(this.name + " is taking a break with energy level " + this.currentEnergy);
							
							//Go home if no energy
							if (this.currentEnergy <= 0) {
								System.out.println(this.name + " is tired and went home.");
								numberOfWorkersAtWork--;
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
    			}
				
			 catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	
    	}
    }
 
    //Define the coffee machine class which mainly handles brewing and serving coffees
    public class CoffeeMachine extends Thread{
    	//Define brewing time and time to wait before serving workers. 
    	//which makes it brew a coffee every 2 seconds and takes 1 second to check for/serve a customer
    	int timeBetweenServes = 1000 / timeScale;
    	int brewingTime = (2000 - timeBetweenServes) / timeScale; 

    	//This code starts when the thread is started
    	@Override 
    	public void run() {
    		try {
    			//A loop that keeps iterating
    			while (true) {
    				
    				//Brew coffees only if there are workers
    				if (numberOfWorkersAtWork > 0) {

						//Each second check for people in the coffee queue
						Thread.sleep(timeBetweenServes);
						
						synchronized (readyCoffees) {
							//Check if coffee is available and if someone is in the queue
							if ((readyCoffees.size() >= 1) && (workersInQueue.size() >= 1)) {
								
								//Serve a coffee
								Coffee coffeeToDrink = readyCoffees.peek();
								Worker personToServe = workersInQueue.peek();		
								
								readyCoffees.poll();					
								personToServe.currentEnergy += coffeeToDrink.getEnergy();
								System.out.println(personToServe.name + " enjoyed a " + coffeeToDrink.getClass().getSimpleName() + " with " + coffeeToDrink.getEnergy() + " energy");
								
								//Remove the Worker from the queue
								workersInQueue.poll();
								
								//Add the worker back to the queue if it hasn't 100 energy
								if (personToServe.currentEnergy < 100) {
									workersInQueue.add(personToServe);
								}
							}
						}
						
						//Brew a coffee if there's under 20 coffees ready
						if (readyCoffees.size() < 20) {
							//Brew a coffee
							Thread.sleep(this.brewingTime);
							readyCoffees.add(createRandomCoffee());
							
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
    
    //Definition of the coffee class. It contains an integer for the amount of energy that it will give
    abstract class Coffee{
    	private int energy;
    	
    	public Coffee(int energy) {
            this.energy = energy;
        }

    	//Getter for accessing the energy from outside the class
    	protected int getEnergy() {
    		return this.energy;
    	}
    	
    }
    
    //Each type of coffee is a subtype of coffee, inheriting "energy" and setting it to a random value within its own energy range
    class BlackCoffee extends Coffee {

		public BlackCoffee() {
			super(generateRandomNumber(15, 20)); 
		}
    }
    
    class Cappuccino extends Coffee {

		public Cappuccino() {
			super(generateRandomNumber(20, 30));
		}
    }
		
    class Latte extends Coffee {

		public Latte() {
			super(generateRandomNumber(25, 33));
		}
    }
    
    //Method that returns a random coffee
    public Coffee createRandomCoffee() {
        
    	//Generate a random number from 0-2, each representing a type of coffee, and then return an instance of that coffee.
        int coffeeType = generateRandomNumber(0, 2);  

        switch (coffeeType) {
            case 0:
                return new BlackCoffee();  
            case 1:
                return new Cappuccino(); 
            case 2:
                return new Latte(); 
            default:
                return null;
        }
    }
		

 }
    
    

    
    
    




