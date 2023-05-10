public class Deadwood {
    private int Days;
    private Player[] players;

    // constructor
    public static void main(String[] args) {
        // set days
        // set players
        start();
    }

    // method to start a game
    public static void start() {
        // create an input parser, which will create an input manager
        InpParser inpP = new InpParser();
        XMLParser xml = new XMLParser();
        Board board = new Board(xml.readBoardData());
        SceneDeck deck = new SceneDeck(xml.readCardData());
        inpP.startGame();
    }

    // method to end a game
    public void end() {

    }

    // getters
    public int getDays() {
        return this.Days;
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

    public void addPlayer(Player player) {

    }
}