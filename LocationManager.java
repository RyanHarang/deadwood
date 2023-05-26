import java.util.*;

public class LocationManager {
    private static Map<Player, Room> locations = new HashMap<Player, Room>();
    private static Player[] players;
    private static Room trailers;

    // constructor
    public LocationManager(Player[] playerList, Room nTrailers) {
        trailers = nTrailers;
        for (Player p : playerList) {
            locations.put(p, trailers);
        }

        players = playerList;
        // all players in the trailers make list for later use of all players
    }

    // method to check if moves are legal, called by move
    private static boolean validateMove(Player player, Room new_location) {
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
    public static boolean move(Player player, Room new_location) {
        boolean isValidMove = validateMove(player, new_location);
        if (isValidMove) {
            // System.out.println(locations.get(player).toString());
            String test = locations.get(player).getName();
            locations.put(player, new_location);
            
            System.out.println("moved " + player.getName() + " from " + test + " to " + new_location.getName());
        }
        return isValidMove;
    }

    // method to send all players to the trailers
    public static void returnTrailers() {
        for (Player p : locations.keySet()) {
            locations.put(p, trailers);
        }
    }

    // getters
    public static Room getPlayerLocation(Player player) {
        return locations.get(player);
    }

    public static ArrayList<Player> getOccupants(Room room) {
        // loop through the list of players and get their rooms from the hashmap
        ArrayList<Player> occupants = new ArrayList<Player>();
        for (Player player : players) {
            if ((locations.get(player)).equals(room)) {
                occupants.add(player);
            }
        }
        return occupants;
    }
}