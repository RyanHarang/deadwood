import java.util.ArrayList;

public class PlayerActions {

    public PlayerActions() {

    }

    public static ArrayList<Role> availableRoles(Player p, Room location) {
        ArrayList<Role> offCardList = null;
        ArrayList<Role> onCardList = null;
        // list possible roles, get role the user wants and give player that role
        offCardList = location.getRoles();
        if (location.getScene() != null) {
            onCardList = location.getScene().getRoles();
        }
        // get roles available to take
        ArrayList<Role> availableRoles = new ArrayList<Role>();
        if (offCardList != null || onCardList != null) {
            if (offCardList != null) {
                for (Role r : offCardList) {
                    if (!r.isOccupied()) {
                        if (r.getRank() <= p.getRank()) {
                            availableRoles.add(r);
                        }
                    }
                }
            }
            if (onCardList != null) {
                for (Role r : onCardList) {
                    if (!r.isOccupied()) {
                        if (r.getRank() <= p.getRank()) {
                            availableRoles.add(r);
                        }
                    }
                }
            }
        }
        return availableRoles;
    }

    public static boolean playerAct(Player p) {
        p.setCanAct(false);
        p.setCanRehearse(false);
        Room room = LocationManager.getPlayerLocation(p);
        if (p.getRole().isMain()) {
            if (actOnCard(p, room.getScene().getBudget())) {
                room.removeShot();
            }
        } else {
            if (actOffCard(p, room.getScene().getBudget())) {
                room.removeShot();
            }
        }
        if (room.getShots() == 0) {
            CurrencyManager.wrapPay(room);
            for (Player pl : LocationManager.getOccupants(room)) {
                System.out.println("Scene has wrapped");
                pl.setRole(null);
                pl.setPracticeChip();
                pl.setCanMove(true);
                pl.setCanAct(false);
                pl.setCanRehearse(false);
            }
            room.setScene(null);
            return true; // scene has wrapped
        }
        return false; // scene not wrapped
    }

    private static boolean actOnCard(Player player, int roomBudget) {
        int roll = Dice.roll(player.getPracticeChips());
        // success
        if (roll >= roomBudget) {
            CurrencyManager.adjustCredits(2, player);
            return true;
        }
        return false;
    }

    private static boolean actOffCard(Player player, int roomBudget) {
        int roll = Dice.roll(player.getPracticeChips());
        // success
        if (roll >= roomBudget) {
            CurrencyManager.adjustCredits(1, player);
            CurrencyManager.adjustMoney(1, player);
            return true;
        }
        // falure - prompt failure with view
        else {
            CurrencyManager.adjustMoney(1, player);
            return false;
        }
    }

    public static boolean playerRehearse(Player p) {
        Room room = LocationManager.getPlayerLocation(p);
        int budget = room.getScene().getBudget();
        if (p.getPracticeChips() >= budget - 1) {
            return false;
        } else {
            p.addPracticeChip();
            p.setCanRehearse(false);
            p.setCanAct(false);
            return true;
        }
    }
}