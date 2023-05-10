public class Board {
    private Room[] rooms;

    public Board(Room[] rooms) {
        this.rooms = rooms;
    }

    public Room[] getRooms() {
        return rooms;
    }

    public Room roomByName(String name) {
        for (int i = 0; i < rooms.length; i++) {
            if (rooms[i].getName().equalsIgnoreCase(name)) {
                return rooms[i];
            }
        }
        return null;
    }
}