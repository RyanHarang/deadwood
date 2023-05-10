import java.util.ArrayList;

public class InpParser {

    private int days;
    private ArrayList<String> names;

    private InpManager manager;

    public InpParser() {
        manager = InpManager.getInstance();
    }

    // this method will set the player count and day count in deadwood
    // potentially easiest to return an array formatted something like
    // [playerCount, days] and have deadwood use the return to initialize it's own
    // variables
    //
    // Instead for now I've decided to make private variables that will all be set
    // and then deadwood can retrieve them via getters
    public void startGame() {
        names = new ArrayList<String>();
        int playerCount = 0;
        boolean validPC = false;
        String input = "";

        System.out
                .println("Welcome to deadwood! Please enter the number of players you would like to play with [2-8]: ");
        input = manager.newInput();
        while (!validPC) {
            try {
                playerCount = Integer.parseInt(input);
                if (2 <= playerCount && playerCount <= 8) {
                    validPC = true;
                    System.out.println("You have seleved to play with " + playerCount + " players.");
                } else {
                    System.out.println("Deadwood requires a [2-8] players to play! Please try again");
                }

            } catch (Exception e) {
                System.out.println("That's not a number! Please try again. Valid player counts are [2-8]");
                input = manager.newInput();
            }
        }

        for (int i = 1; i <= playerCount; i++) {
            System.out.println("Enter a name for Player " + i);
            input = manager.newInput();
            names.add(input);
        }

        if (playerCount <= 3) {
            days = 3;
        } else {
            days = 4;
        }
    }

    public ArrayList<String> getNames() {
        return names;
    }

    public int getDays() {
        return days;
    }

    public void handleInput(String inp) {

    }
}
