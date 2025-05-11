package fika;

public abstract class Coffee {
	private final int energy;

	//Constructor that takes in the amount of energy the coffe will give as parameter
	public Coffee(int energy) {
		this.energy = energy;
	}

	public int getEnergy() {
		return energy;
	}
}
