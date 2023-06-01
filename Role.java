public class Role {
    private boolean isMain;
    private String name;
    private String line;
    private int rank;
    private int[] area;
    private boolean occupied = false;
    private Player occupant = null;

    // constructor
    public Role(String name, String line, int rank, int[] area, boolean isMain) {
        this.name = name;
        this.line = line;
        this.rank = rank;
        this.area = area;
        this.isMain = isMain;
    }

    // getters
    public String getName() {
        return name;
    }

    public int getRank() {
        return rank;
    }

    public int[] getArea() {
        return area;
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