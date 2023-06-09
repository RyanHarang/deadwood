import java.util.ArrayList;

public class SceneCard {
    private int num;
    private int budget;
    private String name;
    private String img;
    private String description;
    private boolean faceUp;
    private ArrayList<Role> roles;

    // constructor
    public SceneCard(String img, String name, String description, int budget, int num, ArrayList<Role> roles) {
        this.img = img;
        this.name = name;
        this.description = description;
        this.budget = budget;
        this.num = num;
        this.roles = roles;
        this.faceUp = false;
    }

    public String toString() {
        return "Img: " + img + " Name: " + name + " |\n\t\tDescription: " + description + "|\n\t\tBudget: " + budget;
    }

    // getters
    public int getBudget() {
        return budget;
    }

    public boolean isFaceUp() {
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

    public String getImg() {
        return img;
    }

    public String getDescription() {
        return description;
    }

    // setters
    public void flip() {
        faceUp = true;
    }
}