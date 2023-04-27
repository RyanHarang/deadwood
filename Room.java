import java.util.ArrayList;
public class Room{
    private Role[] roles;
    private ArrayList<Player> actors;
    private Room[] adjacents;
    private String name;
    private int shots;
    private Scene scene;

    //constructor
    public Room(Role[] roles, Room[] adj, String name, int shots){

    }
    //methods
    public Role[] getRoles(){
        return roles;
    }

    public ArrayList<Player> getPlayers(){
        return actors;
    }

    public Room[] getAdjacents(){
        return adjacents;
    }

    public String getName(){
        return name;
    }

    public int getShots(){
        return shots;
    }

    public Scene getScene(){
        return scene;
    }

    public void addPlayer(Player player){

    }

    public void removePlayer(Player player){

    }

    public void setAdjacents(Room[] rooms){

    }

    public void setShots(int shots){

    }

    public void setScene(Scene scene){

    }




}
