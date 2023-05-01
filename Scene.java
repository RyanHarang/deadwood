public class Scene {
    private int budget;
    private Role[] roles;
    private String name;

    // constructor
    public Scene(String name, int budget, Room[] roles) {

    }

    // method for when a scene is finished, might not belong in scene class
    public void discard() {

    }

    // getters
    public int getBudget() {
        return budget;
    }

    public Role[] getRoles() {
        return roles;
    }

    public String getName() {
        return name;
    }
}