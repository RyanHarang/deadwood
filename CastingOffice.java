public class CastingOffice {
    private static CastingOffice castingOffice = new CastingOffice();
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

    public static CastingOffice getCastingOffice() {
        return castingOffice;
    }

    // getter for info
    public static int[][] getInfo() {
        return info;
    }

    public static boolean upgrade(Player player, int rank, boolean upgradingWithMoney) {
        // cant upgrade to a lower or equal rank
        // verify has enough money or credits
        if (upgradingWithMoney) {
            // if enough money
            if (player.getMoney() >= info[rank - 2][1]) {
                upgradeMoney(player, rank);
            } else {
                return false;
            }
        } else {
            // if enough credits
            if (player.getCredits() >= info[rank - 2][2]) {
                upgradeCredits(player, rank);
            } else {
                return false;
            }
        }
        return true;

    }

    public static void upgradeMoney(Player player, int rank) {
        player.setRank(rank);
        // remove money from info
        CurrencyManager.adjustMoney(-(info[rank - 2][1]), player);
    }

    public static void upgradeCredits(Player player, int rank) {
        player.setRank(rank);
        // remove credits from info
        CurrencyManager.adjustCredits(-(info[rank - 2][2]), player);
    }

    public static int[][] validRanks(Player p){
        int[][] ranks = new int[5][3];
        for(int i = 2; i < 7; i++){
            ranks[i-2][0] = i;
            if(p.getRank() < i){
                if (p.getMoney() >= info[i - 2][1]) {
                    ranks[i-2][1] = 1;
                }
                if (p.getCredits() >= info[i  -2][2]){
                    ranks[i-2][2] = 1;
                }  
            }

        }
        return ranks;
    }
}