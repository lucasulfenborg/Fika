package fika;

public abstract class Coffee {
    private final int energy;

    public Coffee(int energy) {
        this.energy = energy;
    }

    public int getEnergy() {
        return energy;
    }
}
