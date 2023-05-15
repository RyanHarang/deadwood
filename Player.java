public class Player implements Comparable<Player> {
    private int rank;
    private int money;
    private int credits;
    private int practiceChips;
    private String name;
    private Role role = null;

    // constructor
    public Player(String name) {
        this.name = name;
        money = 0;
        credits = 0;
        practiceChips = 0;
        rank = 1;
    }

    public String toString() {
        return "Name: " + name + " | Credits: " + credits + " | Rank: " + rank + " | Money: " + money;
    }

    // getters
    public Role getRole() {
        return role;
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

    public int getScore() {
        return money + credits + (5 * rank);
    }

    // setters
    public void setRole(Role role) {
        this.role = role;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public void addPracticeChip() {
        practiceChips++;
    }

    public void setPracticeChip() {
        practiceChips = 0;
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