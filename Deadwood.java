public class Deadwood {
    private int Days;
    private Player[] players;
    private Dice dice;
    private LocationManager locationManager;
    private CurrencyManager currencyManager;

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
        Board board = new Board(xml.readBoardData());
        SceneDeck deck = new SceneDeck(xml.readCardData());
        inpM.startGame();
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
    public void act(Player player){
        Room room = locationManager.getPlayerLocation(player);
        if(player.getRole().isMain()){
            if(actOnCard(player, room.getScene().getBudget())){
                room.removeShot();
            }
        }
        else{
            actOffCard(player, room.getScene().getBudget());
        }
        if(room.getShots() == 0){
            currencyManager.wrapPay(room);
        }
    }
    public boolean actOnCard(Player player, int roomBudget){
        int roll = dice.roll(player.getPracticeChips());
        //success
        if(roll >= roomBudget){
            currencyManager.adjustCredits(2, player);
            return true;
        }
        //falure - prompt failure with model
        return false;
    }
    public void actOffCard(Player player, int roomBudget){
        int roll = dice.roll(player.getPracticeChips());
        //success
        if(roll >= roomBudget){
            currencyManager.adjustCredits(1, player);
            currencyManager.adjustMoney(1, player);
        }
        //falure - prompt failure with model
        else{
            currencyManager.adjustMoney(1, player);
        }
    }
}