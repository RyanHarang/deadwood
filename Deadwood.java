import java.util.ArrayList;

public class Deadwood {
    private static int days;
    private static int numActiveScenes;
    private static Player[] players;
    private static Dice dice;
    private static LocationManager locationManager;
    private static CurrencyManager currencyManager;
    private static CastingOffice castingOffice;
    private static PlayerActions playerActions;
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
        castingOffice = CastingOffice.getCastingOffice();
        currencyManager = CurrencyManager.getCurrencyManager();
        playerActions = new PlayerActions();
        // loop for setting an initial scene at each room

        locationManager = new LocationManager(players, board.roomByName("trailer"));
        CurrencyManager.setLocMan(locationManager);
        numActiveScenes = 10;
        gameLoop();
    }

    // method to end a game
    public static void end() {
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
            inpP.pass(player.getName() + " scored " + player.getScore() + " points.");
            if (player.getScore() == score) {
                winners.add(player);
                inpP.pass("This was a winning score.");
            }
        }
        inpP.end();
    }

    public static void gameLoop() {
        while (days > 0) {
            // start at 2 because 0 and 1 are office and trailer
            for (int i = 2; i < board.getRooms().length; i++) {
                board.getRooms()[i].setScene(deck.getScene());
            }
            dayLoop();
            days--;
        }
        end();
    }

    // for smaller methods we can break this up, currently represents one game day
    public static void dayLoop() {

        while (numActiveScenes > 1) {
            for (Player p : players) {
                // print player name
                boolean validAction = false;
                Room loc = locationManager.getPlayerLocation(p);
                inpP.pass("");
                inpP.pass("Current Player [" + p.toString() + "]");
                inpP.pass("Current Location [" + loc.toString() + "]");
                if (loc.getScene() != null) {
                    inpP.pass("Current Scene [" + loc.getScene().toString() + "]");
                }
                while (!validAction) {
                    char action = inpP.handleAction();
                    switch (action) {
                        case ('m'): // can you move with a roll? no, you must act
                            if (p.getRole() == null) {
                                playerActions.playerMove(p, locationManager, board, inpP, castingOffice,
                                        currencyManager);
                                validAction = true;
                            } else {
                                inpP.pass("You can't move while you have a role.");
                            }
                            break;
                        case ('a'):
                            if (p.getRole() != null) {
                                if (playerActions.playerAct(p, locationManager, currencyManager, inpP)) {
                                    numActiveScenes--;
                                }
                                validAction = true;
                            } else {
                                inpP.pass("You need to take a role before you can act.");
                            }
                            break;
                        case ('r'):
                            if (p.getRole() != null) {
                                if (playerActions.playerRehearse(p, locationManager, inpP)) {
                                    validAction = true;
                                }
                            } else {
                                inpP.pass("You need to take a role before you can rehearse.");
                            }
                            break;
                        case ('u'):
                            if (locationManager.getPlayerLocation(p).equals(board.roomByName("office"))) {
                                boolean validUpgrade = playerActions.playerUpgrade(p, inpP, castingOffice,
                                        locationManager, currencyManager);
                                if (!validUpgrade) {
                                    inpP.pass("You can't upgrade to that rank just yet!");
                                } else {
                                    if (inpP.moveAfterUpgrade()) {
                                        playerActions.playerMove(p, locationManager, board, inpP, castingOffice,
                                                currencyManager);
                                    }
                                    validAction = true;
                                }

                                // currently ends players turn after failed upgrade

                            } else {
                                inpP.pass("You must be in the Casting Office to upgrade.");
                            }
                            break;
                        case ('t'):
                            if (playerActions.playerTakeRole(inpP, p, locationManager.getPlayerLocation(p))) {
                                validAction = true;
                            } else {

                            }
                            // currently ends players turn after failed attempt to take role

                            break;
                    }
                }
                if (numActiveScenes == 1) {
                    endDay();
                }
            }
        }
    }

    public static void endDay() {
        locationManager.returnTrailers();
        // inpP.startDay();

    }

    public static void act(Player player) {
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

    public static boolean actOnCard(Player player, int roomBudget) {
        int roll = dice.roll(player.getPracticeChips());
        // success
        if (roll >= roomBudget) {
            currencyManager.adjustCredits(2, player);
            return true;
        }
        // falure - prompt failure with model
        return false;
    }

    public static void actOffCard(Player player, int roomBudget) {
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
}