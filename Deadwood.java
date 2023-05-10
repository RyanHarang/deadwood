public class Deadwood {
    private int Days;
    private Player[] players;
    private Dice dice;
    private int numActiveScenes;
    private LocationManager locationManager;
    private CurrencyManager currencyManager;
    private CastingOffice castingOffice;
    private InpManager inpM;
    private InpParser inpP;



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
    public void decrementScenes(){
        numActiveScenes--;
    }

    //for smaller methods, we can break this up, currently will represent one game day
    public void gameLoop(){

        while(numActiveScenes > 1){
        // for every player?
        inpM.newInput("Would you like to (m)ove, (r)ehearse, (a)ct, (u)pgrade, or (t)ake a roll?");
        char action = inpP.handleInputFirstLetter();
            for(Player p: players){
                switch(action){
                    case('m'): // can you move with a roll?
                    //prompt for new location

                        Room playerLocation = locationManager.getPlayerLocation(p);
                        inpM.newInput("Where would you like to move: "+ playerLocation.neighborsString());
                        
                        locationManager.move(p, location);
                        // if invalid, repeat. if valid, prompt upgrade.
                    case('a'):
                        //where do we handle act
                    case('r'):
                        p.addPracticeChip();
                    case('u'):
                        int rank;
                        Boolean upgradingWithMoney;
                        castingOffice.upgrade(p, rank, upgradingWithMoney, locationManager, currencyManager);
                        // if invalid, repeat, if valid, prompt move.
                    case('t'):
                        //can do this after moving
                        //gotta implement this.
                        // list roles, prompt which one you want to take, p.setRole, 
                }
                if(numActiveScenes == 1){
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

    public void endDay(){
        locationManager.returnTrailers();

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