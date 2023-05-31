import java.util.ArrayList;

public class Room {
    private int shots;
    private String name;
    private boolean SceneCardDone = false;
    private SceneCard SceneCard = null;
    private ArrayList<Role> roles;
    private ArrayList<Room> adjacents;
    private ArrayList<int[]> shotLocations;
    private int[] area;
    private int x;
    private int y;


    // constructor
    public Room(int shots, String name, ArrayList<Role> roles, int[] area) {
        this.shots = shots;
        this.name = name;
        this.roles = roles;
        this.area = area;
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

    public int getX(){
        return x;
    }

    
    public int getY(){
        return y;
    }

    // setters
    public void setX(int x){
        this.x = x;
    }

    public void setY(int y){
        this.y = y;
    }

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