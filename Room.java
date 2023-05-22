import java.util.ArrayList;

public class Room {
    private int shots;
    private String name;
    private boolean SceneCardDone = false;
    private SceneCard SceneCard = null;
    private ArrayList<Role> roles;
    private ArrayList<Room> adjacents;

    // constructor
    public Room(int shots, String name, ArrayList<Role> roles) {
        this.shots = shots;
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

    public SceneCard getScene() {
        return SceneCard;
    }

    public boolean isSceneCardDone() {
        return SceneCardDone;
    }

    // setters
    public void setAdjacents(ArrayList<Room> rooms) {
        adjacents = rooms;
    }

    public void removeShot() {
        shots--;
    }

    public void setScene(SceneCard SceneCard) {
        this.SceneCard = SceneCard;
    }

    public void setSceneCardDone(boolean SceneCardDone) {
        this.SceneCardDone = SceneCardDone;
    }
}