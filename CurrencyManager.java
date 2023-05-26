import java.util.ArrayList;
import java.util.Collections;

public class CurrencyManager {
    private static CurrencyManager currencyManager = new CurrencyManager();

    // constructor
    private CurrencyManager() {
        // dice = new Dice();
    }

    public static CurrencyManager getCurrencyManager() {
        return currencyManager;
    }

    // setters
    public static void adjustMoney(int num, Player player) {
        int currentMoney = player.getMoney();
        player.setMoney(currentMoney += num);
    }

    public static void adjustCredits(int num, Player player) {
        int currentCredits = player.getCredits();
        player.setCredits(currentCredits += num);
    }

    public static void wrapPay(Room room) {
        // for each player, check if main or extra and call corresponding pay method
        // unless there are no mains in the entire room in which case nobody gets payed
        ArrayList<Player> onCard = new ArrayList<Player>();
        ArrayList<Player> offCard = new ArrayList<Player>();
        for (Player p : LocationManager.getOccupants(room)) {
            if (p.getRole() != null) {
                if (p.getRole().isMain()) {
                    onCard.add(p);
                } else {
                    offCard.add(p);
                }
            }
        }
        Collections.sort(onCard, Collections.reverseOrder());
        if (onCard.size() != 0) {
            mainPay(room.getScene(), onCard);
            extraPay(offCard);
        }

    }

    // method to be called by wrapPay based on players role type
    private static void mainPay(SceneCard sceneCard, ArrayList<Player> onCard) {
        ArrayList<Integer> diceList = new ArrayList<Integer>();
        for (int i = 0; i < sceneCard.getBudget(); i++) {
            diceList.add(Dice.roll());
        }
        Collections.sort(diceList);
        int numPlayers = onCard.size();
        int count = 0;
        for (int i : diceList) {
            adjustMoney(i, onCard.get(count % numPlayers));
            count++;
        }
    }

    private static void extraPay(ArrayList<Player> offCard) {
        for (Player p : offCard) {
            adjustMoney(p.getRole().getRank(), p);
        }
    }
}