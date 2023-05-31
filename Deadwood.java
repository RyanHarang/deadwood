import java.util.ArrayList;

public class Deadwood {
    private static int days;
    private static int numActiveScenes;
    private static Player[] players;
    private static LocationManager locationManager;
    // private static CurrencyManager currencyManager;
    // private static CastingOffice castingOffice;
    private static Board board;
    private static SceneDeck deck;
    private static InpParser inpP;
    private static Player activePlayer;
    private static int activeTurn;

    // method to start a game
    /*
     * public static void start() {
     * // creating an input parser, which creates an input manager
     * // parser acts as the control, manager acts as the view
     * inpP = new InpParser();
     * // calling startgame in InpParser
     * // initializes days and players
     * inpP.startGame();
     * days = inpP.getDays();
     * players = inpP.getPlayers();
     * 
     * // creating an XMLParser which creates the board and scene deck
     * XMLParser xml = new XMLParser();
     * deck = new SceneDeck(xml.readCardData());
     * // board gets created by parser, each room has no scene to start
     * board = new Board(xml.readBoardData());
     * castingOffice = CastingOffice.getCastingOffice();
     * currencyManager = CurrencyManager.getCurrencyManager();
     * // playerActions = new PlayerActions();
     * // loop for setting an initial scene at each room
     * 
     * locationManager = new LocationManager(players, Board.roomByName("trailer"));
     * // CurrencyManager.setLocMan(locationManager);
     * numActiveScenes = 10;
     * // gameLoop();
     * }
     */

    // method to end a game
    /*
     * public static void end() {
     * // calculates score, determines winner
     * int score = 0;
     * ArrayList<Player> winners = new ArrayList<Player>();
     * // first loop finds highest score
     * for (Player player : players) {
     * if (player.getScore() >= score) {
     * score = player.getScore();
     * }
     * }
     * // second loop adds all players who achieved this score to winners
     * for (Player player : players) {
     * inpP.pass(player.getName() + " scored " + player.getScore() + " points.");
     * if (player.getScore() == score) {
     * winners.add(player);
     * inpP.pass("This was a winning score.");
     * }
     * }
     * // close the scanner
     * inpP.end();
     * }
     */

    /*
     * public static void gameLoop() {
     * while (days > 0) {
     * // start at 2 because 0 and 1 are office and trailer
     * for (int i = 2; i < board.getRooms().length; i++) {
     * board.getRooms()[i].setScene(deck.getScene());
     * }
     * dayLoop();
     * days--;
     * }
     * end();
     * }
     */

    /*
     * public static void dayLoop() {
     * 
     * while (numActiveScenes > 1) {
     * for (Player p : players) {
     * ////////////
     * // view.updatePlayer(p);
     * ///////////////
     * boolean validAction = false;
     * Room loc = LocationManager.getPlayerLocation(p);
     * inpP.pass("");
     * inpP.pass("Current Player [" + p.toString() + "]");
     * inpP.pass("Current Location [" + loc.toString() + "]");
     * if (loc.getScene() != null) {
     * inpP.pass("Current Scene  [" + loc.getScene().toString() + "]");
     * }
     * while (!validAction) {
     * char action = inpP.handleAction();
     * switch (action) {
     * case ('m'):
     * if (p.getRole() == null) {
     * PlayerActions.playerMove(p, locationManager, board, inpP, castingOffice,
     * currencyManager);
     * validAction = true;
     * } else {
     * inpP.pass("You can't move while you have a role.");
     * }
     * break;
     * case ('a'):
     * if (p.getRole() != null) {
     * if (PlayerActions.playerAct(p)) {
     * numActiveScenes--;
     * }
     * validAction = true;
     * } else {
     * inpP.pass("You need to take a role before you can act.");
     * }
     * break;
     * case ('r'):
     * if (p.getRole() != null) {
     * if (PlayerActions.playerRehearse(p)) {
     * validAction = true;
     * }
     * } else {
     * inpP.pass("You need to take a role before you can rehearse.");
     * }
     * break;
     * case ('u'):
     * if (LocationManager.getPlayerLocation(p).equals(Board.roomByName("office")))
     * {
     * boolean validUpgrade = PlayerActions.playerUpgrade(p, inpP, castingOffice,
     * locationManager, currencyManager);
     * if (!validUpgrade) {
     * inpP.pass("You can't upgrade to that rank just yet!");
     * } else {
     * if (inpP.moveAfterUpgrade()) {
     * PlayerActions.playerMove(p, locationManager, board, inpP, castingOffice,
     * currencyManager);
     * }
     * validAction = true;
     * }
     * 
     * } else {
     * inpP.pass("You must be in the Casting Office to upgrade.");
     * }
     * break;
     * case ('t'):
     * if (PlayerActions.playerTakeRole(inpP, p,
     * LocationManager.getPlayerLocation(p))) {
     * validAction = true;
     * } else {
     * 
     * }
     * break;
     * }
     * }
     * if (numActiveScenes == 1) {
     * endDay();
     * }
     * }
     * }
     * }
     */

    public static void endDay() {
        LocationManager.returnTrailers();
        adjustDays();
        System.out.println("Day has ended");
        if (days == 0) {
            System.out.println("Game has ended");
        }
        numActiveScenes = 10;
    }

    public static void updateRole(Role role) {
        activePlayer.setRole(role);
        role.setOccupant(activePlayer);
        System.out.println("Active player: " + activePlayer.toString() + " has chosen " + role.toString());
    }

    // method to be called when the end turn button is clicked, will need further
    // funcionality but for now just started by making it continue to the next
    // player instantly
    public static void endTurn() {
        int count = players.length;
        activeTurn++;
        activePlayer = players[activeTurn % count];
        // has role
        if (activePlayer.getRole() != null) {
            activePlayer.setCanAct(true);

            Room room = LocationManager.getPlayerLocation(activePlayer);
            int budget = room.getScene().getBudget();
            boolean canRehearse = (activePlayer.getPracticeChips() >= budget - 1) ? false : true;
            activePlayer.setCanRehearse(canRehearse);
            activePlayer.setCanMove(false);
        }
        // has no role
        else {
            activePlayer.setCanAct(false);
            activePlayer.setCanMove(true);
            activePlayer.setCanRehearse(false);

            if (LocationManager.getPlayerLocation(activePlayer).getName().equals("office")) {
                activePlayer.setCanUpgrade(true);
            }
        }
    }

    // used to take a list of strings as input and create players
    public static void initializePlayers(ArrayList<String> names) {
        int numPlayers = names.size();
        players = new Player[numPlayers];
        days = 4;
        numActiveScenes = 10;

        for (int i = 0; i < numPlayers; i++) {
            players[i] = new Player(names.get(i));
            players[i] = (new Player(names.get(i)));
            if (numPlayers < 4) {
                days = 3;
            }
            if (numPlayers == 5) {
                players[i].setCredits(2);
            } else if (numPlayers == 6) {
                players[i].setCredits(4);
            } else if (numPlayers > 6) {
                players[i].setRank(2);
            }
        }

        XMLParser xml = new XMLParser();
        deck = new SceneDeck(xml.readCardData());
        board = new Board(xml.readBoardData());
        locationManager = new LocationManager(players, Board.roomByName("trailer"));

        // distribute scenes to every room
        for (int i = 2; i < board.getRooms().length; i++) {
            board.getRooms()[i].setScene(deck.getScene());
        }

        System.out.println("Names: " + names.toString());
        // initialize player one as the active player
        // sets active turn to the index of the array of the player with turn
        activeTurn = 0;
        activePlayer = players[0];

    }

    public static int getNumActiveScenes() {
        return numActiveScenes;
    }

    // getters
    public static Player getActivePlayer() {
        return activePlayer;
    }

    // setters
    public static void adjustDays() {
        days--;
    }

    public static void decrementScenes() {
        System.out.println("numScenes decremented");
        numActiveScenes--;
    }
}