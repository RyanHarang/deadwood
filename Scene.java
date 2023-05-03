import java.util.ArrayList;

public class Scene {
    private int budget;
    private int num;
    private ArrayList<Role> roles;
    private String name;
    private String description;

    // constructor
    public Scene(String name, String description, int budget, int num, ArrayList<Role> roles) {
        this.name = name;
        this.description = description;
        this.budget = budget;
        this.num = num;
        this.roles = roles;
    }

    public String toString() {
        return "Name: " + name + " | Description: " + description + " | Number: " + num + " | Budget: " + budget
                + " | Roles: " + roles.toString();
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

    public ArrayList<Role> getRoles() {
        return roles;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}