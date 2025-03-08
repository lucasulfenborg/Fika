import java.util.Random;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.Stack;

public class Fika {

	//Global variables
	public int timeScale = 10;
	public Stack<Coffee> readyCoffees = new Stack<>();
	public int numberOfPersonalAtWork = 0;
	public 
	
    static void main(String[] args) {
        Fika fika = new Fika();
        fika.startSimulation();
    }
    
    //Startup
    private void startSimulation() {
    	
    	

    	//genererar personalen (snott namnen från uppgift 3)
    	Personal Erik  = new Personal("Erik");
    	Personal Simon  = new Personal("Simon");
    	Personal Adrian  = new Personal("Adrian");
    	Personal Nora  = new Personal("Nora");
    	
    	//Skapa kaffemaskin
    	coffeeMachine coffeeMachine = new coffeeMachine();
    	
    	coffeeMachine.start();
    	Erik.start();
    	Simon.start();
    	
    	


    }

    // Metod för att generera ett slumpmässigt nummer mellan min och max som blir värden i andra variabler
    private int RandomNumber(int min, int max) {
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }

    
    
    public class Personal extends Thread {
    	String name;
        int currentEnergy;
        int EnergyLoss;
        
        		
        public Personal(String name) {
        	this.name = name;
            this.currentEnergy = RandomNumber(30, 90);
            this.EnergyLoss = RandomNumber(500, 1500);
        }	
        
        @Override 
    	public void run() {
    		try {
    			
    			numberOfPersonalAtWork++;
    			
    			while (true) {
					Thread.sleep(this.EnergyLoss / timeScale);
					
				    //sänker energinivån av arbetare med currentEnergy
					if (this.currentEnergy > 0) {
			        	this.currentEnergy--;
			        }
					
					//Go to coffee room when 30 energy
					if(this.currentEnergy < 30) {
						System.out.println(this.name + " is taking a break with energy " + this.currentEnergy);
						
						//if not in coffee queue and under 100 energy -> join it
						
					}
    				
					
					//Go home if no energy
					if (this.currentEnergy <= 0) {
						System.out.println(this.name + " is tired and went home.");
						numberOfPersonalAtWork--;
						return;
					}
					
					else if (this.currentEnergy > 30){
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
    	int second = 1000; 
    	int BrewingTime = 2000; //in milliseconds
    	
    	@Override 
    	public void run() {
    		try {
    			while (true) {
    				    				
    				if (numberOfPersonalAtWork > 0) {

						//Each second check for people in the coffee queue
						Thread.sleep(this.second / timeScale);
						
						
						//Every other second a random coffee is brewed
						Thread.sleep((this.BrewingTime - this.second) / timeScale);
						readyCoffees.push(new Coffee());
						
						//System.out.println(readyCoffees);
    				}
    			}
				
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}}


    
    
    
    
    
    public class Coffee{
    	int BlackCoffee;
    	int Cappuccino;
    	int Latte;
    	int BrewingTime = 2;
    	
    	public Coffee() {				
    		BlackCoffee  = RandomNumber(15, 20);
    		Cappuccino = RandomNumber(20, 30);
    		Latte = RandomNumber(25, 35);
    	}
    }

    
    
    
}
    
//Todo
//Slumpa typ av kaffe (gör det i konstruktorn)
//Få till så att personal kan dricka kaffe och stå i kö för det

//Optimera kod ex länka istället för att göra kopior av instanser i listor
//Kommentarer till engelska
//Tydligare variabelnamn
    



