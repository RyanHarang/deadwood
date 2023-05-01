import java.util.ArrayList;

public class Room {
    private static Room[] adjacents;
    private static String name;
    private static int shots;
    private Role[] roles;
    private ArrayList<Player> actors;
    private Scene scene;

    // constructor
    public Room(int shots, String name, Role[] roles, Room[] adj) {

    }

    // getters
    public Role[] getRoles() {
        return roles;
    }

    public ArrayList<Player> getPlayers() {
        return actors;
    }

    public Room[] getAdjacents() {
        return adjacents;
    }

    public String getName() {
        return name;
    }

    public int getShots() {
        return shots;
    }

    public Scene getScene() {
        return scene;
    }

    // setters
    public void setAdjacents(Room[] rooms) {
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
