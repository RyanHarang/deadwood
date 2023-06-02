import java.util.ArrayList;

public class Room {
    private int shots;
    private int permShots;
    private int[] area;
    private String name;
    private boolean SceneCardDone = false;
    private SceneCard SceneCard = null;
    private ArrayList<Role> roles;
    private ArrayList<Room> adjacents;
    private ArrayList<Role> permanentRoles;

    // constructor
    public Room(int shots, int[] area, String name, ArrayList<Role> roles) {
        this.shots = shots;
        this.area = area;
        this.name = name;
        this.roles = roles;
        this.permShots = shots+0;
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

    public SceneCard getScene() {
        return SceneCard;
    }

    public boolean isSceneCardDone() {
        return SceneCardDone;
    }

    public ArrayList<Role> getPermanentRoles(){
        return permanentRoles;
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

    public void resetRoom(){
        for(Role r: roles){
            r.setOccupant(null);
        }
        shots = permShots+0;
    }
}