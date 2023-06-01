import java.util.ArrayList;

public class Deadwood {
    private static int days;
    private static int numActiveScenes;
    private static Player[] players;
    private static Board board;
    private static SceneDeck deck;
    private static Player activePlayer;
    private static int activeTurn;

    public static String[][] iconNames = {
        {"b1.png", "b2.png", "b3.png", "b4.png", "b5.png", "b6.png"},
        {"c1.png", "c2.png", "c3.png", "c4.png", "c5.png", "c6.png"},
        {"g1.png", "g2.png", "g3.png", "g4.png", "g5.png", "g6.png"}, 
        {"o1.png", "o2.png", "o3.png", "o4.png", "o5.png", "o6.png"}, 
        {"p1.png", "p2.png", "p3.png", "p4.png", "p5.png", "p6.png"}, 
        {"r1.png", "r2.png", "r3.png", "r4.png", "r5.png", "r6.png"}, 
        {"v1.png", "v2.png", "v3.png", "v4.png", "v5.png", "v6.png"}, 
        {"y1.png", "y2.png", "y3.png", "y4.png", "y5.png", "y6.png"}, };



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
            players[i].setIconIndex(i);
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
        LocationManager.initialize(players, Board.roomByName("trailer"));

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

    public static void updatePlayerIcon(int rank) {
        int index = activePlayer.getIconIndex();
        activePlayer.setIconName(iconNames[index][activePlayer.getRank() - 1]);
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

    public static void setDeck(SceneDeck deck) {
        Deadwood.deck = deck;
    }

    public static void setBoard(Board board) {
        Deadwood.board = board;
    }
    public static Player[] getPlayers(){
        return players;
    }
}