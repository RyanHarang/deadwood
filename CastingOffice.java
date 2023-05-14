public class CastingOffice {
    private static CastingOffice castingOffice;

    // three column array with column one being the target rank,
    // column two being the cost to upgrade to the corresponding
    // target in dollars, and column three being the cost in credits.
    private static int[][] info = {
            { 2, 4, 5 },
            { 3, 10, 10 },
            { 4, 18, 15 },
            { 5, 28, 20 },
            { 6, 40, 25 }
    };

    // constructor
    private CastingOffice() {

    }

    public static CastingOffice getCastingOffice(){
        if(castingOffice == null){
            castingOffice = new CastingOffice();
        }
        return castingOffice;
    }

    // getter for info
    public static int[][] getInfo() {
        return info;
    }

    public boolean upgrade(Player player, int rank, boolean upgradingWithMoney, LocationManager locationManager,
            CurrencyManager currencyManager) {
        // cant upgrade to a lower or equal rank
        if (player.getRank() >= rank) {
            return false;
        }
        // verify player is in casting office, might be unnecesary. if it is, we dont
        // need location manager
        if (locationManager.getPlayerLocation(player).getName() != "office") {
            return false;
        }

        // verify has enough money or credits
        if (upgradingWithMoney) {
            // if enough money
            if (player.getMoney() >= info[rank - 2][1]) {
                upgradeMoney(player, rank, currencyManager);
            } else {
                return false;
            }
        } else {
            // if enough credits
            if (player.getCredits() >= info[rank - 2][2]) {
                upgradeCredits(player, rank, currencyManager);
            } else {
                return false;
            }
        }
        return true;

    }

    public void upgradeMoney(Player player, int rank, CurrencyManager currencyManager) {
        player.setRank(rank);
        // remove money from info
        currencyManager.adjustMoney(-(info[rank - 2][1]), player);
    }

    public void upgradeCredits(Player player, int rank, CurrencyManager currencyManager) {
        player.setRank(rank);
        // remove credits from info
        currencyManager.adjustCredits(-(info[rank - 2][2]), player);
    }
}