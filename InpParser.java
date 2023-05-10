<<<<<<< HEAD
import java.util.Scanner;
public class InpParser {

    Scanner s = new Scanner(System.in);
    public InpParser() {
=======
import java.util.ArrayList;

public class InpParser {

    private int days;
    private Player[] players;
>>>>>>> d6ffd90590bd0696bf3f657a1da68678d24cf1a7

    private InpManager manager;

    public InpParser() {
        manager = InpManager.getInstance();
    }

    // method for getting player count and names from players
    public void startGame() {
        ArrayList<String> names = new ArrayList<String>();
        int playerCount = 0;
        boolean validPC = false;
        String input = "";

        // prompt for player count user inputManager
        System.out.println("Welcome to Deadwood! Please enter the number of players to play with [2-8]: ");
        input = manager.newInput();
        // while loop to prevent forward progress until a valid player count is entered
        while (!validPC) {
            // check if input is an integer
            // if it is check if it falls between [2-8]
            // if not an integer throw an error message and prompt for input again
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

        // once a valid player count is chosen, a name, must be given to each player
        // currently nothing stopping players from having matching names...
        for (int i = 1; i <= playerCount; i++) {
            System.out.println("Enter a name for Player " + i);
            input = manager.newInput();
            names.add(input);
        }

        initializePlayers(names);

        // set days based on player count
        if (playerCount <= 3) {
            days = 3;
        } else {
            days = 4;
        }
    }

    // takes an arraylist of names and creates player onjects
    private void initializePlayers(ArrayList<String> names) {
        int count = names.size();
        players = new Player[count];
        // creates a number of player objects with starting values based on player count
        for (int i = 0; i < count; i++) {
            players[i] = (new Player(names.get(i)));
            if (count == 5) {
                players[i].setCredits(2);
            } else if (count == 6) {
                players[i].setCredits(4);
            } else if (count > 6) {
                players[i].setRank(2);
            }
        }
    }

    public Player[] getPlayers() {
        return players;
    }

    public int getDays() {
        return days;
    }
}
