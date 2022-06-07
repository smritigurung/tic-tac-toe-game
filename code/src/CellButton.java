import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

/**
 * This class creates a 3X3 array of button to create TicTacToeTest board
 */
public class CellButton extends GameView implements Observer {

    BoardData data;
    int xp = 15;
    int yp = 15;
    String value;
    JButton undoButton = new JButton("Undo");
    Player currentPlayer;
    JButton[][] arrayBtn = new JButton[3][3];

    /**
     * a constructor to create undo button and attach ActionListener
     * for all the buttons on the board
     *
     * @param aData
     */
    public CellButton(BoardData aData) {
        //initial data, value, and buttons

        this.data = aData;
        value = " ";
        undoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                undo();
            }
        });

        //create buttons
        for (int n = 0; n < 3; n++) {
            xp = 15;
            yp += 80;
            for (int i = 0; i < 3; i++) {
                buttonActionListener(createButton(n, i));
            }
        }
    }

    /**
     * A method to create a JButton and and assign the JButton in the array
     */
    public JButton createButton(int xp, int yp) {
        arrayBtn[xp][yp] = super.executeStrategy(new NoAnimationButtonCreator()).createButton(xp, yp, value);
        return arrayBtn[xp][yp];
    }

    /**
     * button ActionListener method executed each time the buttons on the board is clicked
     */
    public void buttonActionListener(JButton btn) {
        btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // fetching the original text of the button on the board
                String buttonText = ((JButton) e.getSource()).getText();
                int d = Integer.parseInt(buttonText.substring(0, 1));
                int f = Integer.parseInt(buttonText.substring(1, 2));
                //change current player and changed data in observable
                currentPlayer = nextPlayer();
                data.changeData(d, f, currentPlayer);
                arrayBtn[d][f].setEnabled(false);

                if (data.CheckWinner()) {  // if we have a winner, disable all buttons
                    buttonDisable();
                    undoButton.setEnabled(false);
                    return;
                }
                // if all button has been selected and there is no winner, disable all buttons
                if (data.CheckGameOver()) {
                    undoButton.setEnabled(false);
                    JOptionPane.showMessageDialog(new JFrame("message"), "Game Over", "Backup Problem", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    /**
     * This method disables all the button as needed. like no winner is found.
     */
    public void buttonDisable() {

        for (int n = 0; n < 3; n++) {
            for (int i = 0; i < 3; i++) {
                arrayBtn[n][i].setEnabled(false);
            }
        }
    }

    /**
     * This method checks the current player's state and restore the button value on the previous value.
     * or it prevents player from undo
     */
    public void undo() {
        if (currentPlayer == p0 && p0.undoCheck == 0 && p0.undo < 3) { //check whether player 1 can undo or not
            //add undo times
            p0.undo++;
            p0.undoCheck = 1;
            p1.undoCheck = 0;
            L.removeLast();  // remove the player who played last

            //get the last position and enable them as normal button
            int[] position = data.setPrevious();  // calling the setPrevious method of BoardData class
            arrayBtn[position[0]][position[1]].setEnabled(true);
            arrayBtn[position[0]][position[1]].setText(String.valueOf(position[0]) + position[1]);

        } else if (currentPlayer == p1 && p1.undoCheck == 0 && p1.undo < 3) { //check whether player 2 can undo or not
            //add undo times
            p1.undo++;
            p1.undoCheck = 1;
            p0.undoCheck = 0;
            L.removeLast();   // remove the player who played last

            //get the last position and enable them as normal button
            int[] position = data.setPrevious(); // calling the setPrevious method of BoardData class
            arrayBtn[position[0]][position[1]].setEnabled(true);
            arrayBtn[position[0]][position[1]].setText(String.valueOf(position[0]) + position[1]);

        } else {
            //if all of them cannot undo, display error message
            JOptionPane.showMessageDialog(new JFrame("message"), "You can't undo", "Inane error", JOptionPane.ERROR_MESSAGE);
        }

    }

    /**
     * Accessor method
     *
     * @return undoButton the undo JButton
     */
    public JButton getUndoButton() {
        return undoButton;
    }

    /**
     * Accessor method
     *
     * @return arrayBtn a 3X3 array of button
     */
    public JButton[][] getButtons() {
        return arrayBtn;
    }

    @Override
    public void update(Observable o, Object arg) {
        data = (BoardData) o;
        for (int n = 0; n < 3; n++) {
            for (int i = 0; i < 3; i++) {
                if (data.getData()[n][i] != null) {
                    if (data.getData()[n][i].id == 1) {
                        //mark all button which is selected by player 1 as green and display the player's name on the button
                        arrayBtn[n][i].setBackground(Color.GREEN);
                        arrayBtn[n][i].setOpaque(true);
                        arrayBtn[n][i].setBorderPainted(true);
                        arrayBtn[n][i].setText(p0.name);
                    }
                    if (data.getData()[n][i].id == 2) {
                        //mark all button which is selected by player 1 as green and display the player's name on the button
                        arrayBtn[n][i].setBackground(Color.RED);
                        arrayBtn[n][i].setOpaque(true);
                        arrayBtn[n][i].setBorderPainted(true);
                        arrayBtn[n][i].setText(p1.name);
                    }

                } else {
                    //mark all non-selected button as gray
                    arrayBtn[n][i].setForeground(Color.GRAY);
                    arrayBtn[n][i].setOpaque(false);
                    arrayBtn[n][i].setBackground(Color.BLACK);
                }

            }
        }
    }
}
