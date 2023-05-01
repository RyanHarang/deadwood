import java.lang.Math;

public class Dice {

    private int result;

    // constructor
    public Dice() {
    }

    // returns a number between 1 and 6
    public int roll() {
        result = 1 + (int) (6 * Math.random());
        return result;
    }

    // generates a random number between 1 and 6 and adds a given
    // integer, returning the result
    public int roll(int practiceChips) {
        result = 1 + (int) (6 * Math.random());
        return result + practiceChips;
    }
}