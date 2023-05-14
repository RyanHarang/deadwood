import java.util.ArrayList;
import java.util.Collections;

public class CurrencyManager {
    private static CurrencyManager currencyManager;
    private static LocationManager locationManager;

    // temporary?
    public Dice dice;

    // constructor
    private CurrencyManager() {
        dice = new Dice();
    }

    public static CurrencyManager getCurrencyManager() {
        if (currencyManager == null) {
            currencyManager = new CurrencyManager();
        }
        return currencyManager;
    }

    // method to take players, compare all their scores, and determine the winner
    public Player determineWinner(Player[] players) {
        return null;
    }

    public static void setLocMan(LocationManager locMan) {
        locationManager = locMan;
    }

    // setters
    public void adjustMoney(int num, Player player) {
        int currentMoney = player.getMoney();
        player.setMoney(currentMoney += num);
    }

    public void adjustCredits(int num, Player player) {
        int currentCredits = player.getCredits();
        player.setCredits(currentCredits += num);
    }

    public void adjustRank(int num, Player player) {

    }

    // method to pay players when scenes are wrapped
    public void wrapPay(Room room) {
        // loop through all players in room
        // for each player, check if main or extra
        // call corresponding pay method for each player
        // unless there are no mains in the entire room
        // in which case nobody gets payed
        ArrayList<Player> onCard = new ArrayList<Player>();
        ArrayList<Player> offCard = new ArrayList<Player>();
        for (Player p : locationManager.getOccupants(room)) {
            if (p.getRole().isMain()) {
                onCard.add(p);
            } else {
                offCard.add(p);
            }
        }
        Collections.sort(onCard, Collections.reverseOrder());
        if (onCard.size() != 0) {
            mainPay(room.getScene(), onCard);
            extraPay(offCard);
        }

    }

    // method to be called by wrapPay based on players role type
    private void mainPay(Scene scene, ArrayList<Player> onCard) {
        ArrayList<Integer> diceList = new ArrayList<Integer>();
        for (int i = 0; i < scene.getBudget(); i++) {
            diceList.add(dice.roll());
        }
        Collections.sort(diceList);
        int numPlayers = onCard.size();
        int count = 0;
        for (int i : diceList) {
            adjustMoney(i, onCard.get(count % numPlayers));
            count++;
        }
    }

    private void extraPay(ArrayList<Player> offCard) {
        for (Player p : offCard) {
            adjustMoney(p.getRole().getRank(), p);
        }
    }
}