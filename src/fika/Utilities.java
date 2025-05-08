package fika;

import java.util.Random;

public class Utilities {
    private static final Random rand = new Random();

    public static int generateRandomNumber(int min, int max) {
        return rand.nextInt((max - min) + 1) + min;
    }
}
