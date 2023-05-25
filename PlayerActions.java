import java.util.ArrayList;

public class PlayerActions {
    // private static Dice dice = new Dice();

    public PlayerActions() {

    }

    public static void playerMove(Player p, LocationManager locationManager, Board board, InpParser inpP,
            CastingOffice castingOffice, CurrencyManager currencyManager) {
        // prompt for new location
        Room playerLocation = LocationManager.getPlayerLocation(p);
        Room location = board.roomByName(inpP.getDestination(playerLocation.neighborsString()));
        locationManager.move(p, location);

        // must then check: scene card face up or down?
        // would you like to take a roll?
        if (!(location.getName().equals("office") || location.getName().equals("trailer"))) {
            if (!location.getScene().isFaceUp()) {
                location.getScene().flip();
            }
        }
        if (inpP.takingRole()) {
            playerTakeRole(inpP, p, location);
        }
        // open roles on scene or room?
        // does player want to take a role?
        if (location.getName().equals("office")) {
            if (inpP.upgrading()) {
                boolean validUpgrade = playerUpgrade(p, inpP, castingOffice, locationManager, currencyManager);
                while (!validUpgrade) {
                    if (!validUpgrade) {
                        inpP.pass("You can't upgrade to that rank just yet!");
                    }
                    validUpgrade = playerUpgrade(p, inpP, castingOffice, locationManager, currencyManager);
                }
            }
        }
        // if invalid, repeat. if valid, prompt upgrade.
    }

    public static boolean playerTakeRole(InpParser inpP, Player p, Room location) {
        if (location.getShots() == 0) {
            inpP.pass("There is no scene for you to take a role in!");
            return false;
        }
        if (p.getRole() != null) {
            inpP.pass("You already have a role!");
            return false;
        }
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
            if (availableRoles.size() == 0) {
                inpP.pass("There are no available roles for you to take...");
                return false;
            }
            Role role = inpP.takeRole(availableRoles);
            role.setOccupant(p);
            p.setRole(role);
            return true;
        } else {
            System.out.println("You can't take a role here!");
        }
        return false;
    }

    public static boolean playerAct(Player p) {

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
            //inpP.pass("Scene has wrapped!");
            CurrencyManager.wrapPay(room);
            for (Player pl : LocationManager.getOccupants(room)) {
                pl.setRole(null);
                pl.setPracticeChip();
            }
            return true;// scene has wrapped
        }
        return false;// scene not wrapped
    }

    private static boolean actOnCard(Player player, int roomBudget) {
        int roll = Dice.roll(player.getPracticeChips());
        // success
        if (roll >= roomBudget) {
            CurrencyManager.adjustCredits(2, player);
            //inpP.pass("Your roll and practicechips total to: " + roll + " Act succeeds!");
            return true;
        }
        // falure - prompt failure with view
        //inpP.pass("Your roll and practicechips total to: " + roll + " Act failed!");
        return false;
    }

    private static boolean actOffCard(Player player, int roomBudget) {
        int roll = Dice.roll(player.getPracticeChips());
        // success
        if (roll >= roomBudget) {
            CurrencyManager.adjustCredits(1, player);
            CurrencyManager.adjustMoney(1, player);
            //inpP.pass("Your roll and practicechips total to: " + roll + " Act succeeds!");
            return true;
        }
        // falure - prompt failure with view
        else {
            CurrencyManager.adjustMoney(1, player);
            //inpP.pass("Your roll and practicechips total to: " + roll + " Act failed!");
            return false;
        }
    }

    public static boolean playerUpgrade(Player p, InpParser inpP, CastingOffice castingOffice,
            LocationManager locationManager,
            CurrencyManager currencyManager) {
        int[] upgrade = inpP.upgradeInfo();
        int rank = upgrade[1];
        Boolean upgradingWithMoney = false;
        if (upgrade[0] == 1) {
            upgradingWithMoney = true;
        }
        return castingOffice.upgrade(p, rank, upgradingWithMoney, locationManager, currencyManager);
    }

    public static boolean playerRehearse(Player p) {
        System.out.println("hi");
        Room room = LocationManager.getPlayerLocation(p);
        int budget = room.getScene().getBudget();
        if (p.getPracticeChips() >= budget - 1) {
            return false;
        } else {
            p.addPracticeChip();
            return true;
        }
    }
}