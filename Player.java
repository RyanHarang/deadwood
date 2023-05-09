public class Player implements Comparable<Player> {
    private Role role = null;
    private Room room;
    private String name;
    private int credits;
    private int money;
    private int practiceChips;
    private int rank;

    // constructor
    public Player(String name) {
        this.name = name;
        money = 0;
        credits = 0;
        practiceChips = 0;
        rank = 1;
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

    public int getPracticeChips() {
        return practiceChips;
    }

    // setters
    public void setRole(Role role) {
        this.role = role;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public void addPracticeChip() {
        practiceChips++;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public int compareTo(Player p) {
        if (role.getRank() == p.role.getRank()) {
            return 0;
        } else if (role.getRank() > p.role.getRank()) {
            return 1;
        } else {
            return -1;
        }
    }
}