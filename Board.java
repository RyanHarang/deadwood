public class Board {
    private static Room[] rooms;

    public Board(Room[] nRooms) {
        rooms = nRooms;
    }

    public Room[] getRooms() {
        return rooms;
    }

    // returns room with a given name if it exists
    public static Room roomByName(String name) {
        for (int i = 0; i < rooms.length; i++) {
            if (rooms[i].getName().equalsIgnoreCase(name)) {
                return rooms[i];
            }
        }
        return null;
    }
}