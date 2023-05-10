import java.util.ArrayList;

public class Room {
    private ArrayList<Room> adjacents;
    private String name;
    private int shots;
    private ArrayList<Role> roles;
    private ArrayList<Player> actors;
    private Scene scene;
    private int[] area;
    // area formatted as x, y, h, w

    // constructor
    public Room(int shots, int[] area, String name, ArrayList<Role> roles) {
        this.shots = shots;
        this.area = area;
        this.name = name;
        this.roles = roles;
    }

    public String neighborsString(){
        String neighbors = "";
        for(Room r: adjacents){
            neighbors += r.getName() + " ";
        }
        return neighbors;
    }

    public String toString() {
        String adjs = "", areas = "";
        for (int i = 0; i < 4; i++) {
            areas += " " + area[i];
        }
        for (int j = 0; j < adjacents.size(); j++) {
            adjs += " " + adjacents.get(j).getName();
        }
        return "RoomName: " + name + " | Shots: " + shots + " | SceneAreas:" + areas + " | Adjacents: " + adjs;
    }

    // getters
    public ArrayList<Role> getRoles() {
        return roles;
    }

    public ArrayList<Player> getPlayers() {
        return actors;
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

    // setters
    public void setAdjacents(ArrayList<Room> rooms) {
        adjacents = rooms;
    }

    // might not be needed
    /*
     * public void setShots(int shots) {
     * this.shots = shots;
     * }
     */

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public void addPlayer(Player player) {
        actors.add(player);
    }

    public void removePlayer(Player player) {
        actors.remove(player);
    }

}
