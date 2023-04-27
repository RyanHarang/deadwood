import java.util.Map;
public class LocationManager {
    private Map<Player, Room> locations;

    //constructer
    public LocationManager(){

    }

    //methods
    public Room getPlayerLocation(Player player){
        return null;
    }

    public Player[] getOccupants(Room room){
        return null;
    }
    private boolean validateMove(Player player, Room location){
        return false;
    }
    public boolean move(Player player, Room location){
        return validateMove(player, location);
    }
    public void returnTrailers(){
        
    }
}
