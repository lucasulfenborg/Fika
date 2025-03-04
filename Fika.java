import java.util.Random;

public class Fika {

    public static void main(String[] args) {
        Fika fika = new Fika();
        fika.startSimulation();
    }

    // Metod för att generera ett slumpmässigt nummer mellan min och max som blir värden i andra variabler
    private int RandomNumber(int min, int max) {
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }

    
    
    public class Personal {
        int TotalEnergy;
        int EnergyLoss;
        
        		
        public Personal() {
            TotalEnergy = RandomNumber(30, 90);
            EnergyLoss = RandomNumber(500, 1500);
        }		
    }
    //sänker energinivån av arbetare med 1
    public void decreaseEnergy() {
        if (TotalEnergy > 0) {
        	TotalEnergy--;
        }
    }
    
    public class CoffeMachine {
    	int BrewingTime = 2;
    }

    public class Coffe{
    	int BlackCoffee;
    	int Cappuccino;
    	int Latte;
    	int BrewingTime = 2;
    	
    	public Coffe() {				
    		BlackCoffee  = RandomNumber(15, 20);
    		Cappuccino = RandomNumber(20, 30);
    		Latte = RandomNumber(25, 35);
    	}
    }

    
    //startar simulationen
    private void startSimulation() {
    	
    	
    	
    	
    	//genererar personalen (snott namnen från uppgift 3)
    	Personal Erik  = new Personal();
    	Personal Simon  = new Personal();
    	Personal Adrian  = new Personal();
    	Personal Nora  = new Personal();
    	//genererar kaffet
    	CoffeMachine BlackCoffee = new CoffeMachine();
    	CoffeMachine Cappuccino = new CoffeMachine();
    	CoffeMachine Latte = new CoffeMachine();

    	System.out.println("Test test 123, InitialEnergy: " + Erik.TotalEnergy +  " " + Erik.EnergyLoss);
    	
    	
    	
    	
    	
    	/*
    	 * for (int i = 0; i < 20; i++) {
    		
    		System.out.println( "Erik is working with energy level " + Erik.TotalEnergy );
    	}
    	*/
    	
    	 
    	
    }
    }


/* vad som ska skapas:
///få sekunder att fungera
///en kö till kaffemaskinen där alla kan stå i
///en personlig for sats för personalen där EnergyLoss är i och repiteras
///ett sätt att gå hem och att känna av det
///ett sätt öfr kaffemaskinen att skapa kaffet med 2 sek tid mellan.
///en drink reserve för kaffemaskinen
*/


