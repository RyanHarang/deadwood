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
                playerUpgrade(p, inpP, castingOffice, locationManager, currencyManager);
            }
        }

        // if invalid, repeat. if valid, prompt upgrade.
    }

    public boolean playerTakeRole(InpParser inpP, Player p, Room location) {
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
        // list possible roles
        // get role the user wants
        // give player that role
        // System.out.println(location.toString());
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
            // role is null???????
            // System.out.print(role.toString());
            role.setOccupant(p);
            p.setRole(role);
            return true;
        } else {
            System.out.println("You can't take a role here!");
        }
        return false;
    }

    public boolean playerAct(Player p, LocationManager locationManager, CurrencyManager currencyManager,
            InpParser inpP) {

        Room room = locationManager.getPlayerLocation(p);

        // might be useless/////////////
        ////////////////////////////////
        if (p.getRole().isMain()) {
            if (actOnCard(p, room.getScene().getBudget(), currencyManager, inpP)) {
                room.removeShot();
            }
        } else {
            if (actOffCard(p, room.getScene().getBudget(), currencyManager, inpP)) {
                room.removeShot();
            }
        }
        if (room.getShots() == 0) {
            inpP.pass("Scene has wrapped!");
            currencyManager.wrapPay(room);
            for (Player pl : locationManager.getOccupants(room)) {
                pl.setRole(null);
                pl.setPracticeChip();
            }
            return true;// scene has wrapped
        }
        return false;// scene not wrapped
    }

    private boolean actOnCard(Player player, int roomBudget, CurrencyManager currencyManager, InpParser inpP) {
        int roll = dice.roll(player.getPracticeChips());
        // success
        if (roll >= roomBudget) {
            currencyManager.adjustCredits(2, player);
            inpP.pass("Success!");
            return true;
        }
        // falure - prompt failure with model
        inpP.pass("Fail!");
        return false;
    }

    private boolean actOffCard(Player player, int roomBudget, CurrencyManager currencyManager, InpParser inpP) {
        int roll = dice.roll(player.getPracticeChips());
        // success
        if (roll >= roomBudget) {
            currencyManager.adjustCredits(1, player);
            currencyManager.adjustMoney(1, player);
            inpP.pass("Success!");
            return true;
        }
        // falure - prompt failure with model
        else {
            currencyManager.adjustMoney(1, player);
            inpP.pass("Fail!");
            return false;
        }
    }

    public boolean playerUpgrade(Player p, InpParser inpP, CastingOffice castingOffice, LocationManager locationManager,
            CurrencyManager currencyManager) {
        int[] upgrade = inpP.upgradeInfo();
        int rank = upgrade[1];
        Boolean upgradingWithMoney = false;
        if (upgrade[0] == 1) {
            upgradingWithMoney = true;
        }
        return castingOffice.upgrade(p, rank, upgradingWithMoney, locationManager, currencyManager);
    }

    public boolean playerRehearse(Player p, LocationManager locationManager, InpParser inpP) {
        Room room = locationManager.getPlayerLocation(p);
        int budget = room.getScene().getBudget();
        if (p.getPracticeChips() >= budget - 1) {
            inpP.pass("You must act!");
            return false;
        } else {
            p.addPracticeChip();
            return true;
        }
    }
}