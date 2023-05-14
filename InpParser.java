import java.util.ArrayList;

public class InpParser {
    private int days;
    private Player[] players;
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

        // while loop to prevent forward progress until a valid player count is entered
        while (!validPC) {
            // prompt for player count user inputManager
            manager.newOutput("Welcome to Deadwood! Please enter the number of players to play with [2-8]: ");
            input = manager.newInput();
            // check if input is an integer
            // if it is check if it falls between [2-8]
            // if not an integer throw an error message and prompt for input again
            try {
                playerCount = Integer.parseInt(input);
                if (2 <= playerCount && playerCount <= 8) {
                    validPC = true;
                    manager.newOutput("You have selected to play with " + playerCount + " players.");
                } else {
                    manager.newOutput("Deadwood requires a [2-8] players to play! Please try again");
                }

            } catch (Exception e) {
                manager.newOutput("That's not a number! Please try again. Valid player counts are [2-8]");
            }
        }

        // once a valid player count is chosen, a name, must be given to each player
        // currently nothing stopping players from having matching names...
        for (int i = 1; i <= playerCount; i++) {
            manager.newOutput("Enter a name for Player " + i);
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

    public void end() {
        manager.newOutput("Game over.");
        manager.end();
    }

    // forces user to input a valid single character action selector
    public char handleAction() {
        char ret;
        String input = "";
        boolean validInput = false;
        while (!validInput) {
            manager.newOutput("Would you like to (m)ove, (r)ehearse, (a)ct, (u)pgrade, or (t)ake a roll? m/r/a/u/t");
            input = manager.newInput();
            if (input.length() != 1) {
                manager.newOutput("Please enter a single character: m, r, a, u, or t");
            } else {
                if (!(input.equals("m") || input.equals("r") || input.equals("a") || input.equals("u")
                        || input.equals("t"))) {
                    manager.newOutput("Please enter a valid character: m, r, a, u, or t");
                } else {
                    manager.newOutput("You have chosen " + input);
                    validInput = true;
                }
            }
        }
        ret = input.charAt(0);
        return ret;
    }

    // returns the name of the room the user is going to
    public String getDestination(String neighbors) {
        String input = "";
        boolean validDestination = false;

        while (!validDestination) {
            manager.newOutput("Where would you like to move? " + neighbors);
            input = manager.newInput();
            String[] options = neighbors.split(", ");

            for (String room : options) {
                if (room.equalsIgnoreCase(input)) {
                    validDestination = true;
                    break;
                }
            }
            // if user doesn't give a valid destination room
            if (!validDestination) {
                manager.newOutput(
                        "That is not a valid destination. Please choose one of the shown options. " + neighbors);
            }
        }
        return input;
    }

    public int[] upgradeInfo() {
        String input = "";
        int[] ret = new int[2];
        boolean validInfo = false;
        int rank = 0;
        int money = 0;
        while (!validInfo) {
            manager.newOutput("Would you like to upgrade using (m)oney or (c)redits? m/c");
            input = manager.newInput();
            if (input.length() != 1) {
                manager.newOutput("Please enter a single character: m or c");
            } else {
                if (!(input.equals("m") || input.equals("c"))) {
                    manager.newOutput("Please enter a valid character: m or c");
                } else {
                    manager.newOutput("You have chosen " + input);
                    if (input.equals("m")) {
                        money = 1;
                    }
                    manager.newOutput("What rank would you like to upgrade to?");
                    input = manager.newInput();
                    try {
                        rank = Integer.parseInt(input);
                        if (2 <= rank && rank <= 6) {
                            manager.newOutput("You have selected to upgrade to rank " + rank);
                            validInfo = true;
                        } else {
                            manager.newOutput("Select a valid rank to upgrade to [2-6]. Please try again.");
                        }

                    } catch (Exception e) {
                        manager.newOutput("That's not a number! Please try again.");
                        input = manager.newInput();
                    }
                }
            }
        }
        ret[0] = money;
        ret[1] = rank;
        return ret;
    }

    public boolean takingRole() {
        boolean validInfo = false;
        char yes_no = ' ';
        while (!validInfo) {
            manager.newOutput("Would you like to take a role? y/n");
            yes_no = manager.newInput().charAt(0);
            if (yes_no == 'y' || yes_no == 'n') {
                validInfo = true;
            } else {
                manager.newOutput("That's not a y or an n! Please try again.");
            }
        }
        if (yes_no == 'n') {
            return false;
        } else {
            return true;
        }
    }

    public boolean moveAfterUpgrade() {
        boolean validInfo = false;
        char yes_no = ' ';
        while (!validInfo) {
            manager.newOutput("Would you like to move? y/n");
            yes_no = manager.newInput().charAt(0);
            if (yes_no == 'y' || yes_no == 'n') {
                validInfo = true;
            } else {
                manager.newOutput("That's not a y or an n! Please try again.");
            }
        }
        if (yes_no == 'n') {
            return false;
        } else {
            return true;
        }
    }

    public boolean upgrading() {
        boolean validInfo = false;
        char yes_no = ' ';
        while (!validInfo) {
            manager.newOutput("Would you like to upgrade? y/n");
            yes_no = manager.newInput().charAt(0);
            if (yes_no == 'y' || yes_no == 'n') {
                validInfo = true;
            } else {
                manager.newOutput("That's not a y or an n! Please try again.");
            }
        }
        if (yes_no == 'n') {
            return false;
        } else {
            return true;
        }
    }

    public Role takeRole(ArrayList<Role> availableRoles) {
        boolean validName = false;
        String name = "";
        String output = "What role would you like to take:\n";
        ArrayList<String> names = new ArrayList<String>();
        for (Role r : availableRoles) {
            output += r.toString() + "\n";
            names.add(r.getName());
        }

        while (!validName) {
            manager.newOutput(output);
            name = manager.newInput();
            for (String n : names) {
                if (n.equalsIgnoreCase(name)) {
                    validName = true;
                }
            }
            if (!validName) {
                manager.newOutput("That's not a name! Please try again.");
            }
        }

        for (Role r : availableRoles) {
            if (name.equals(r.getName())) {
                return r;
            }
        }
        return null;
    }

    public void pass(String pass) {
        manager.newOutput(pass);
    }

    public Player[] getPlayers() {
        return players;
    }

    public int getDays() {
        return days;
    }
}
