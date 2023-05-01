public class CurrencyManager {

    // constructor
    public CurrencyManager() {

    }

    // getters
    public int getMoney(Player player) {
        return 0;
    }

    public int getCredits(Player player) {
        return 0;
    }

    public int getRank(Player player) {
        return 0;
    }

    public int getScore(Player player) {
        return 0;
    }

    // method to take players, compare all their scores, and determine the winner
    public Player determineWinner(Player[] players) {
        return null;
    }

    // setters
    public void adjustMoney(int num) {

    }

    public void adjustCredits(int num) {

    }

    public void adjustRank(int num) {

    }

    // method to pay players when scenes are wrapped
    public void wrapPay(Room room) {
        // loop through all players in room
        // for each player, check if main or extra
        // call corresponding pay method for each player
        // unless there are no mains in the entire room
        // in which case nobody gets payed
    }

    // method to be called by wrapPay based on players role type
    private void mainPay(Player player) {

    }

    private void extraPay(Player player) {

    }
}