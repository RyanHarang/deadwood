public class Role {
    private boolean isMain;
    private String name;
    private String line;
    private int rank;
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