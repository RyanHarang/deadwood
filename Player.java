public class Player implements Comparable<Player> {
    private int rank;
    private int money;
    private int credits;
    private int practiceChips;
    private String name;
    private Role role = null;

    private boolean canUpgrade = false;
    private boolean canMove = true;
    private boolean canTakerole;
    private boolean canAct = false;
    private boolean canRehearse = false;
    private boolean canEndTurn = false;

    private String iconName;
    private int iconIndex;

    // constructor
    public Player(String name) {
        this.name = name;
        money = 0;
        credits = 0;
        practiceChips = 0;
        rank = 1;
    }

    public String toString() {
        String roleString = "none";
        if (role != null) {
            roleString = role.toString();
        }
        return "Name: " + name + " | Credits: " + credits + " | Rank: " + rank + " | Money: " + money
                + " | Practice Chips: " + practiceChips
                + " |\n\t\tActive Role: " + roleString;
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

    public void setCanUpgrade(boolean canUpgrade){
        this.canUpgrade = canUpgrade;
    }

    public boolean getCanUpgrade(){
        return canUpgrade;
    }

    public void setCanMove(boolean canMove){
        this.canMove = canMove;
    }

    public boolean getCanMove(){
        return canMove;
    }

    public void setCanAct(boolean canAct){
        this.canAct = canAct;
    }

    public boolean getCanAct(){
        return canAct;
    }

    public void setCanRehearse(boolean canRehearse){
        this.canRehearse = canRehearse;
    }

    public boolean getCanRehearse(){
        return canRehearse;
    }

    public void setIconIndex(int iconIndex){
        this.iconIndex = iconIndex;
    }

    public int getIconIndex(){
        return iconIndex;
    }

    public void setIconName(String iconName){
        this.iconName = iconName;
    }

    public String getIconName(){
        return iconName;
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