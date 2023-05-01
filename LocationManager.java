import java.util.Map;

public class LocationManager {
    private Map<Player, Room> locations;

    // constructor
    public LocationManager() {
        // intialize locations
        // all players in the trailers
    }

    // method to check if moves are legal, called by move
    private boolean validateMove(Player player, Room location) {
        // updates locations, or will move update locations?
        return false;
    }

    // method to attempt to move players
    public boolean move(Player player, Room location) {
        return validateMove(player, location);
    }

    // method to send all players to the trailers
    public void returnTrailers() {

    }

    // getters
    public Room getPlayerLocation(Player player) {
        return null;
    }

    public Player[] getOccupants(Room room) {
        return null;
    }
}
