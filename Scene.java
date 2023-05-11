import java.util.ArrayList;

public class Scene {
    private int budget;
    private int num;
    private ArrayList<Role> roles;
    private String name;
    private String description;
    private boolean faceUp;

    // constructor
    public Scene(String name, String description, int budget, int num, ArrayList<Role> roles) {
        this.name = name;
        this.description = description;
        this.budget = budget;
        this.num = num;
        this.roles = roles;
        this.faceUp = false;
    }

    public String toString() {
        String rolesString = "";
        for (int a = 0; a < roles.size(); a++) {
            rolesString += " " + roles.get(a).toString();
        }

        return "Name: " + name + " | Description: " + description + " | Number: " + num + " | Budget: " + budget
                + " | Roles: " + rolesString;
    }

    // getters
    public int getBudget() {
        return budget;
    }

    public boolean isFaceUp(){
        return faceUp;
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

    public void flip(){
        faceUp = true;
    }
}