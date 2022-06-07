import java.util.LinkedList;

public abstract class GameView implements BoardType {
    Player p0 = new Player(1);
    Player p1 = new Player(2);
    LinkedList<Player> L = new LinkedList<Player>();

    public AnimatableButtonCreator executeStrategy(AnimatableButtonCreator strategy) {
        return strategy;
    }

    public Player nextPlayer() {
        Player next = null;
        if (L.isEmpty()) {
            next = p0;
            p0.undoCheck = 0;
            p1.undo = 0;
            L.addLast(p0);

        } else if (L.getLast() == p0) {
            next = p1;
            p1.undoCheck = 0;
            p0.undo = 0;
            L.addLast(p1);
        } else if (L.getLast() == p1) {
            next = p0;
            p0.undoCheck = 0;
            p1.undo = 0;
            L.addLast(p0);
        }
        // in case one of p0 or p1 is not undo all the time, this two if statements are
        // used to reset the undo permeation
        if (next == p0) {
            p1.undoCheck = 0;
        }
        if (next == p1) {
            p0.undoCheck = 0;
        }
        return next;
    }

    public Player getPlayer1() {
        return p0;
    }

    public Player getPlayer2() {
        return p1;
    }

}
