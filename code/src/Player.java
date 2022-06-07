/**
 * This class creates a player with information about playing conditions
 */
public class Player {
    int id;
    String name;
    int undo;
    int undoCheck;

    /**
     * Constructor to create a player with initialize values
     *
     * @param id
     */
    public Player(int id) {
        this.undoCheck = 0;  // if already undo assign 1, if not undo assign 0
        this.undo = 0;  //undo counter max 3
        this.id = id;  //player id 1 or 0
    }

    /**
     * mutator method
     *
     * @param name to change the player value with the player name
     */
    public void setName(String name) {
        this.name = name;   // the name of the given player
    }
}
