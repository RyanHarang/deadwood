import java.util.ArrayList;

public class Deadwood {
    private static int days;
    private static Player[] players;
    private static Dice dice;
    private static int numActiveScenes;
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
        // loop for setting an initial scene at each room
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

    public static void gameLoop(){
        while(days > 0){
            for (int i = 0; i < board.getRooms().length; i++) {
                board.getRooms()[i].setScene(deck.getScene());
            }
            dayLoop();
        }
    }

    // for smaller methods we can break this up, currently represents one game day
    public static void dayLoop() {

        while (numActiveScenes > 1) {
            // for every player?
            for (Player p : players) {
                char action = inpP.handleAction();
                switch (action) {
                    case ('m'): // can you move with a roll? no, you must act
                        playerActions.playerMove(p, locationManager, board, inpP, castingOffice, currencyManager);
                    case ('a'):
                        playerActions.playerAct(p, locationManager, currencyManager);
                    case ('r'):
                        playerActions.playerRehearse(p);
                    case ('u'):
                        playerActions.playerUpgrade(p, inpP, castingOffice, locationManager, currencyManager);
                        inpP.moveAfterUpgrade();
                    case ('t'):
                        playerActions.playerTakeRole(inpP, p, locationManager.getPlayerLocation(p));
                }
                if (numActiveScenes == 1) {
                    endDay();
                }
            }
        }
        days--;
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
}