public class Role {
    private static boolean isMain;
    private static String name;
    private static String line;
    private static int rank;
    private boolean occupied;
    private Player occupant;

    // constructor
    public Role(int rank, boolean isMain, String name, String line) {

    }

    // getters
    public String getName() {
        return name;
    }

    public int getRank() {
        return rank;
    }

    public Player getOccupant() {
        return occupant;
    }

    public String getLine() {
        return line;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public boolean isMain() {
        return isMain;
    }

    // setters
    public void setOccupant(Player player) {
        occupant = player;
        occupied = true;
    }

    public void removePlayer() {
        occupant = null;
        occupied = false;
    }
}