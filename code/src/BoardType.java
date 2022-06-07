import javax.swing.*;

public interface BoardType {
    public void buttonActionListener(JButton butt);

    public JButton createButton(int xp, int yp);

    public void undo();

    public Player nextPlayer();

    public void buttonDisable();

    public JButton getUndoButton();

    public JButton[][] getButtons();

    public Player getPlayer1();

    public Player getPlayer2();
}
