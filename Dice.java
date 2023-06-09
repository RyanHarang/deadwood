import java.lang.Math;

public class Dice {
    private static int result;

    // constructor
    public Dice() {
    }

    // returns a number between 1 and 6
    public static int roll() {
        result = 1 + (int) (6 * Math.random());
        return result;
    }

    // generates a random number between 1 and 6, adds a given
    // integer and returns the result
    public static int roll(int practiceChips) {
        result = 1 + (int) (6 * Math.random());
        return result + practiceChips;
    }
}