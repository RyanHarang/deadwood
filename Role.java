public class Role {
    private boolean isMain;
    private String name;
    private String line;
    private int rank;
    private boolean occupied = false;
    private Player occupant = null;

    // constructor
    public Role(String name, String line, int rank, boolean isMain) {
        this.name = name;
        this.line = line;
        this.rank = rank;
        this.isMain = isMain;
    }

    public String toString() {
        return "RoleName: " + name + " | RoleLine: " + line + " | RoleRank: " + rank;
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
}