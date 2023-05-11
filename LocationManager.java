import java.util.*;

public class LocationManager {
    // private static LocationManager locationManager;
    private static Map<Player, Room> locations = new HashMap<Player, Room>();
    private Room trailers;

    // constructor
    public LocationManager(Player[] playerList, Room trailers) {
        this.trailers = trailers;
        System.out.println(this.trailers.toString());
        for (Player p : playerList) {
            locations.put(p, this.trailers);

        }
        // all players in the trailers
    }

    // method to check if moves are legal, called by move
    private boolean validateMove(Player player, Room new_location) {
        // array of rooms adjacent to the player
        ArrayList<Room> adjRooms = locations.get(player).getAdjacents();
        for (Room r : adjRooms) {
            if (r.equals(new_location)) {
                return true;
            }
        }
        return false;
    }

    // method to attempt to move players
    public boolean move(Player player, Room new_location) {
        boolean isValidMove = validateMove(player, new_location);
        if (isValidMove) {
            System.out.println(locations.get(player).toString());
            // locations.get(player).removePlayer(player);
            locations.put(player, new_location);
            // new_location.addPlayer(player);
        }
        return isValidMove;
    }

    // method to send all players to the trailers
    public void returnTrailers() {
        for (Player p : locations.keySet()) {
            locations.put(p, trailers);
        }
    }

    // getters
    public Room getPlayerLocation(Player player) {
        return locations.get(player);
    }

    /*
     * public ArrayList<Player> getOccupants(Room room) {
     * return room.getPlayers();
     * }
     */
}