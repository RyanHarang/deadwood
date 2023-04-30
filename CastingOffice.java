public class CastingOffice {

    private static int[][] info = {
            { 2, 4, 5 },
            { 3, 10, 10 },
            { 4, 18, 15 },
            { 5, 28, 20 },
            { 6, 40, 25 }
    };

    public CastingOffice() {

    }

    public static int[][] getInfo() {
        return info;
    }

    public boolean validate(Player player) {
        // asks location manager to ensure player is in Casting Office
        // ensures player has valid credit or money amounts to declare
        // the upgrade option they choose
        return false;
    }

    public void upgrade(Player player) {

    }

}