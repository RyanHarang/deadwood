public class Deadwood {
    private int days;
    private Player[] players;
    private Dice dice;
    private int numActiveScenes;
    private LocationManager locationManager;
    private CurrencyManager currencyManager;
    private CastingOffice castingOffice;
    private Board board;
    private SceneDeck deck;

    // constructor
    public static void main(String[] args) {
        // set days
        // set players
    }

    // method to start a game
    public static void start() {
    }

    public void start(int numPlayers) {
        // creating an input parser, which creates an input manager
        // parser acts as the control, manager acts as the view
        InpParser inpP = new InpParser();
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

        // create players using the arrayList of names?
        // which class should create player objects for the game?
        //
        // currently handled by input parser which converts a list of names into players
    }

    // method to end a game
    public void end() {
        // calculates score, determines winner
    }

    // getters
    public int getDays() {
        return this.days;
    }

    public int getNumPlayers() {
        return this.players.length;
    }

    public Player[] getPlayers() {
        return this.players;
    }

    // setters
    public void adjustDays(int num) {

    }

    public void decrementScenes() {
        numActiveScenes--;
    }

    // for smaller methods, we can break this up, currently will represent one game
    // day
    public void gameLoop() {

        while (numActiveScenes > 1) {
            // for every player?
            String move;
            for (Player p : players) {
                switch (action) {
                    case ("move"): // can you move with a roll?
                        // prompt for new location
                        Room location;
                        locationManager.move(p, location);
                        // if invalid, repeat. if valid, prompt upgrade.
                    case ("act"):
                        // where do we handle act
                    case ("rehearse"):
                        p.addPracticeChip();
                    case ("upgrade"):
                        int rank;
                        Boolean upgradingWithMoney;
                        castingOffice.upgrade(p, rank, upgradingWithMoney, locationManager, currencyManager);
                        // if invalid, repeat, if valid, prompt move.
                    case ("take roll"):
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

    public void endDay() {
        locationManager.returnTrailers();

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