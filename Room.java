import java.util.ArrayList;

public class Room {
    private int shots;
    private int[] area; // area formatted as x, y, h, w
    private String name;
    private boolean sceneDone = false;
    private Scene scene = null;
    private ArrayList<Role> roles;
    private ArrayList<Room> adjacents;

    // constructor
    public Room(int shots, int[] area, String name, ArrayList<Role> roles) {
        this.shots = shots;
        this.area = area;
        this.name = name;
        this.roles = roles;
    }

    public String neighborsString() {
        String neighbors = "";
        for (Room r : adjacents) {
            neighbors += r.getName() + ", ";
        }
        return neighbors.substring(0, neighbors.length() - 2);
    }

    public String toString() {
        return "RoomName: " + name + " | Shots: " + shots;
    }

    // getters
    public ArrayList<Role> getRoles() {
        return roles;
    }

    public ArrayList<Room> getAdjacents() {
        return adjacents;
    }

    public String getName() {
        return name;
    }

    public int getShots() {
        return shots;
    }

    public int[] getArea() {
        return area;
    }

    public Scene getScene() {
        return scene;
    }

    public boolean isSceneDone() {
        return sceneDone;
    }

    // setters
    public void setAdjacents(ArrayList<Room> rooms) {
        adjacents = rooms;
    }

    public void removeShot() {
        shots--;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public void setSceneDone(boolean sceneDone) {
        this.sceneDone = sceneDone;
    }
}