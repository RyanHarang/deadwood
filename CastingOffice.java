public class CastingOffice {

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
    public CastingOffice() {

    }

    // getter for info
    public static int[][] getInfo() {
        return info;
    }

    // method that will check the validity of upgrade choices
    public boolean validate(Player player, int rank) {
        if(player.getRank() >= rank){
            return false;
        }
        // asks location manager to ensure player is in Casting Office
        if(player.getRoom().getName() == "Casting Office"){
            // ensures player has valid credit or money amounts to declare
        // the upgrade option they choose
            if(player.getMoney() >= info[rank-2][1] || player.getCredits() >= info[rank-2][2]){
                return true;
            }
        }
        
        return false;
    }

    // method that will be called by validate for
    // legal upgrades to perform the upgrade
    private void upgrade(Player player, int rank) {
        if(validate(player, rank)){
            player.setRank(rank);
            //how will player change money, will we call deadwoods currencyManager? 
        }
    }

}