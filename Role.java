public class Role{
    private boolean isMain;
    private int rank;
    private String name;
    private boolean occupied;
    private Player occupant;
    private String line;

    //constructor
    public Role(int rank, String name, boolean isMain){

    }

    //methods
    public String getName(){
        return name;
    }
    public int getRank(){
        return rank;
    }
    public Player getOccupant(){
        return occupant;
    }
    public boolean isOccupied(){
        return occupied;
    }
    public String getLine(){
        return line;
    }

    public void setOccupant(Player player){
        occupant = player;
    }
    public boolean isMain(){
        return isMain;
    }
    public void removePlayer(){
        occupant = null;
    }
}