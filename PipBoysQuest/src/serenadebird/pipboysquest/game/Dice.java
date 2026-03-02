package serenadebird.pipboysquest.game;

import java.util.Random;

public class Dice {
    private int min = 1;
    private int max = 6;
    private Random random = new Random();

    public Dice() {
    }

    public Dice(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public int roll() {
        return random.nextInt(max - min + 1) + min;
    }

    public int getMin() { return min; }
    public void setMin(int min) { this.min = min; }

    public int getMax() { return max; }
    public void setMax(int max) { this.max = max; }

    public Random getRandom() { return random; }
    public void setRandom(Random random) { this.random = random; }

    @Override
    public String toString() {
        return "Dice{" +
                "min=" + min +
                ", max=" + max +
                '}';
    }
}
