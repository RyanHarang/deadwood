public class Scene {
    private int budget;
    private int num;
    private Role[] roles;
    private String name;
    private String description;

    // constructor
    public Scene(String name, String description, int budget, int num, Role[] roles) {
        this.name = name;
        this.description = description;
        this.budget = budget;
        this.num = num;
        this.roles = roles;
    }

    // method for when a scene is finished, might not belong in scene class
    public void discard() {

    }

    // getters
    public int getBudget() {
        return budget;
    }

    public int getNum() {
        return num;
    }

    public Role[] getRoles() {
        return roles;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}