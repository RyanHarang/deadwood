import java.util.ArrayList;

public class PlayerActions {
    private Dice dice = new Dice();

    public PlayerActions() {

    }

    public void playerMove(Player p, LocationManager locationManager, Board board, InpParser inpP,
            CastingOffice castingOffice, CurrencyManager currencyManager) {
        // prompt for new location
        Room playerLocation = locationManager.getPlayerLocation(p);
        Room location = board.roomByName(inpP.getDestination(playerLocation.neighborsString()));
        // in theory, the way getDestination is implemented in InpManager should force a
        // valid room name to be returned

        locationManager.move(p, location);
        if (!location.getScene().isFaceUp()) {
            location.getScene().flip();
        }
        // must then check: scene card face up or down?
        // would you like to take a roll?
        if (inpP.takingRole()) {
            playerTakeRole(inpP, p, location);
        }
        // open roles on scene or room?
        // does player want to take a role?
        if (location.getName().equals("office")) {
            if (inpP.upgrading()) {
                playerUpgrade(p, inpP, castingOffice, locationManager, currencyManager);
            }
        }

        // if invalid, repeat. if valid, prompt upgrade.
    }

    public void playerTakeRole(InpParser inpP, Player p, Room location) {
        ArrayList<Role> offCardList = null;
        ArrayList<Role> onCardList = null;
        // list possible roles
        // get role the user wants
        // give player that role
        System.out.println(location.toString());
        offCardList = location.getRoles();
        /*
         * for (Role role : offCardList) {
         * System.out.println(role.toString());
         * }
         */
        if (location.getScene() != null) {
            onCardList = location.getScene().getRoles();
        }
        /*
         * for (Role role : onCardList) {
         * System.out.println(role.toString());
         * }
         */
        // get roles available to take
        ArrayList<Role> availableRoles = new ArrayList<Role>();
        if (offCardList != null || onCardList != null) {
            if (offCardList != null) {
                for (Role r : offCardList) {
                    if (!r.isOccupied()) {
                        availableRoles.add(r);
                    }
                }
            }
            if (onCardList != null) {
                for (Role r : onCardList) {
                    if (!r.isOccupied()) {
                        availableRoles.add(r);
                    }
                }
            }
            Role role = inpP.takeRole(availableRoles);
            // role is null???????
            System.out.print(role.toString());
            role.setOccupant(p);
            p.setRole(role);
        } else {
            System.out.println("ELSE HERE WEEWEWEEWE");
        }
    }

    public void playerAct(Player p, LocationManager locationManager, CurrencyManager currencyManager) {
        Room room = locationManager.getPlayerLocation(p);
        if (p.getRole().isMain()) {
            if (actOnCard(p, room.getScene().getBudget(), currencyManager)) {
                room.removeShot();
            }
        } else {
            actOffCard(p, room.getScene().getBudget(), currencyManager);
        }
        if (room.getShots() == 0) {
            currencyManager.wrapPay(room);
        }
    }

    private boolean actOnCard(Player player, int roomBudget, CurrencyManager currencyManager) {
        int roll = dice.roll(player.getPracticeChips());
        // success
        if (roll >= roomBudget) {
            currencyManager.adjustCredits(2, player);
            return true;
        }
        // falure - prompt failure with model
        return false;
    }

    private void actOffCard(Player player, int roomBudget, CurrencyManager currencyManager) {
        int roll = dice.roll(player.getPracticeChips());
        // success
        if (roll >= roomBudget) {
            currencyManager.adjustCredits(1, player);
            currencyManager.adjustMoney(1, player);
        }
        // falure - prompt failure with model
        else {
            currencyManager.adjustMoney(1, player);
        }
    }

    public void playerUpgrade(Player p, InpParser inpP, CastingOffice castingOffice, LocationManager locationManager,
            CurrencyManager currencyManager) {
        int[] upgrade = inpP.upgradeInfo();
        int rank = upgrade[1];
        Boolean upgradingWithMoney = false;
        if (upgrade[0] == 1) {
            upgradingWithMoney = true;
        }
        castingOffice.upgrade(p, rank, upgradingWithMoney, locationManager, currencyManager);
    }

    public void playerRehearse(Player p) {
        p.addPracticeChip();
    }
}