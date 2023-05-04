public class Deadwood {
    private int Days;
    private Player[] players;

    // constructor
    public static void main(String[] args) {
        // set days
        // set players
    }

    // method to start a game
    public void start(int numPlayers) {
        InpManager inpM = new InpManager();
        InpParser inpP = new InpParser();
        XMLParser xml = new XMLParser();
        inpM.startGame();
        // xml.createDeck();
        // xml.createBoard();
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