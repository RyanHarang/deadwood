import java.util.ArrayList;

public class Deadwood {
    private static int days;
    private static Player[] players;
    private Dice dice;
    private static int numActiveScenes;
    private static LocationManager locationManager;
    private static CurrencyManager currencyManager;
    private static CastingOffice castingOffice;

    private static Board board;
    private static SceneDeck deck;
    private static InpParser inpP;

    public static void main(String[] args) {
        start();
    }

    // method to start a game
    public static void start() {
        // creating an input parser, which creates an input manager
        // parser acts as the control, manager acts as the view
        inpP = new InpParser();
        // calling startgame in InpParser
        // initializes days and players
        inpP.startGame();
        days = inpP.getDays();
        players = inpP.getPlayers();

        // creating an XMLParser which creates the board and scene deck
        XMLParser xml = new XMLParser();
        deck = new SceneDeck(xml.readCardData());
        // board gets created by parser, each room has no scene to start
        board = new Board(xml.readBoardData());
        // loop for setting an initial scene at each room
        for (int i = 0; i < board.getRooms().length; i++) {
            board.getRooms()[i].setScene(deck.getScene());
        }
        gameLoop();
    }

    // method to end a game
    public static void end() {

        // need to add part that closes scanner in InpManager after everything else

        // calculates score, determines winner
        int score = 0;
        ArrayList<Player> winners = new ArrayList<Player>();
        // first loop finds highest score
        for (Player player : players) {
            if (player.getScore() >= score) {
                score = player.getScore();
            }
        }
        // second loop adds all players who achieved this score to winners
        for (Player player : players) {
            System.out.println(player.getName() + " scored " + player.getScore() + " points.");
            if (player.getScore() == score) {
                winners.add(player);
                System.out.println("This was a winning score.");
            }
        }
    }

    // getters
    public static int getDays() {
        return days;
    }

    public static int getNumPlayers() {
        return players.length;
    }

    public static Player[] getPlayers() {
        return players;
    }

    // setters
    public static void adjustDays() {
        days--;
    }

    public static void decrementScenes() {
        numActiveScenes--;
    }

    // for smaller methods we can break this up, currently represents one game day
    public static void gameLoop() {

        while (numActiveScenes > 1) {
            // for every player?
            char action = inpP.handleAction();
            for (Player p : players) {
                switch (action) {
                    case ('m'): // can you move with a roll? no, you must act
                        // prompt for new location

                        Room playerLocation = locationManager.getPlayerLocation(p);
                        Room location = board.roomByName(inpP.getDestination(playerLocation.neighborsString()));
                        // in theory, the way getDestination is implemented in InpManager should force a
                        // valid room name to be returned

                        locationManager.move(p, location);
                        // must then check: scene card face up or down?
                        // open roles on scene or room?
                        // does player want to take a role?
                        // if invalid, repeat. if valid, prompt upgrade.
                    case ('a'):
                        // where do we handle act
                    case ('r'):
                        p.addPracticeChip();
                    case ('u'):
                        int[] upgrade = inpP.upgradeInfo();
                        int rank = upgrade[1];
                        Boolean upgradingWithMoney = false;
                        if (upgrade[0] == 1) {
                            upgradingWithMoney = true;
                        }
                        castingOffice.upgrade(p, rank, upgradingWithMoney, locationManager, currencyManager);
                        // if invalid, repeat, if valid, prompt move.
                    case ('t'):
                        // can do this after moving
                        // gotta implement this.
                        // list roles, prompt which one you want to take, p.setRole,
                }
                if (numActiveScenes == 1) {
                    endDay();
                }

                // output naming current player, prompt all actions
                // if move, can upgrade after move
                // if act
                // if rehearse
                // if upgrade, can move after upgrade
                // if all scenes except one (maybe use decrement scenes, we can change that)
                //
            }

        }

    }

    public static void endDay() {
        locationManager.returnTrailers();
        // inpP.startDay();

    }

    public void act(Player player) {
        Room room = locationManager.getPlayerLocation(player);
        if (player.getRole().isMain()) {
            if (actOnCard(player, room.getScene().getBudget())) {
                room.removeShot();
            }
        } else {
            actOffCard(player, room.getScene().getBudget());
        }
        if (room.getShots() == 0) {
            currencyManager.wrapPay(room);
        }
    }

    public boolean actOnCard(Player player, int roomBudget) {
        int roll = dice.roll(player.getPracticeChips());
        // success
        if (roll >= roomBudget) {
            currencyManager.adjustCredits(2, player);
            return true;
        }
        // falure - prompt failure with model
        return false;
    }

    public void actOffCard(Player player, int roomBudget) {
        int roll = dice.roll(player.getPracticeChips());
        // success
        if (roll >= roomBudget) {
            currencyManager.adjustCredits(1, player);
            currencyManager.adjustMoney(1, player);
        }
        // falure - prompt failure with model
        else {
            currencyManager.adjustMoney(1, player);
        }
    }
}