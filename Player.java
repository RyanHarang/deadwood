public class Player {
    private Role role;
    private Room room;
    private String name;
    private int credits = 0;
    private int money = 0;
    private int practiceChips = 0;
    private int rank;

    // constructor
    public Player(String name) {

    }

    // getters
    public Role getRole() {
        return role;
    }

    public Room getRoom() {
        return room;
    }

    public String getName() {
        return name;
    }

    public int getCredits() {
        return credits;
    }

    public int getMoney() {
        return money;
    }

    public int getRank() {
        return rank;
    }

    // setters
    public void setRole(Role role) {
        this.role = role;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addPracticeChip() {
        practiceChips++;
    }
}