import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

public class AnimatedCellButton extends GameView implements Observer {

    BoardData data;
    int xp = 15;
    int yp = 15;
    String value;
    JButton undoButton = new JButton("Undo");
    Player currentPlayer;
    Button[][] arrayBtn = new Button[3][3];

    public AnimatedCellButton(BoardData aData) {
        //initial data, value and buttons
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

    //create shaking buttons
    public JButton createButton(int xp, int yp) {
        JButton btn = super.executeStrategy(new AnimatedButtonCreator()).createButton(xp, yp, value);
        arrayBtn[xp][yp] = new Button(btn);
        return btn;
    }


    public void buttonActionListener(JButton butt) {
        butt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // fetching the original text of the button on the board
                String buttonText = ((JButton) e.getSource()).getText();
                int d = Integer.parseInt(buttonText.substring(0, 1));
                int f = Integer.parseInt(buttonText.substring(1, 2));

                //change current player and changed data in observable
                currentPlayer = nextPlayer();
                data.changeData(d, f, currentPlayer);
                arrayBtn[d][f].getButton().setEnabled(false);
                arrayBtn[d][f].shakingTime = 100;
                arrayBtn[d][f].shakeButton();

                // if we have a winner, disable all buttons
                if (data.CheckWinner()) {
                    buttonDisable();
                    undoButton.setEnabled(false);
                    return;
                }

                // if all button has been selected and there is no winner, disable all buttons
                if (data.CheckGameOver()) {
                    undoButton.setEnabled(false);
                    JOptionPane.showMessageDialog(new JFrame("message"), "Game Over", "Backup Problem", JOptionPane.ERROR_MESSAGE);
                }

                arrayBtn[d][f].shakeButton();
            }
        });

    }

    public void undo() {
        if (currentPlayer == p0 && p0.undoCheck == 0 && p0.undo < 3) {  //check whether player 1 can undo or not
            //add undo times
            p0.undo++;
            p0.undoCheck = 1;
            p1.undoCheck = 0;
            L.removeLast();   // remove the player who played last

            //get the last position and enable them as normal button
            int[] position = data.setPrevious();
            arrayBtn[position[0]][position[1]].getButton().setEnabled(true);
            arrayBtn[position[0]][position[1]].shakingTime = 0;
            arrayBtn[position[0]][position[1]].getButton().setText(String.valueOf(position[0]) + position[1]);
        } else if (currentPlayer == p1 && p1.undoCheck == 0 && p1.undo < 3) {//check whether player 2 can undo or not
            //add undo times
            p1.undo++;
            p1.undoCheck = 1;
            p0.undoCheck = 0;
            L.removeLast();  // remove the player who played last

            //get the last position and enable them as normal button
            int[] position = data.setPrevious();
            arrayBtn[position[0]][position[1]].getButton().setEnabled(true);
            arrayBtn[position[0]][position[1]].shakingTime = 0;
            arrayBtn[position[0]][position[1]].getButton().setText(String.valueOf(position[0]) + position[1]);
        } else {
            //if all of them cannot undo, display error message
            JOptionPane.showMessageDialog(new JFrame("message"), "You can't undo", "Inane error", JOptionPane.ERROR_MESSAGE);
        }

    }

    //disable buttons
    public void buttonDisable() {
        for (int n = 0; n < 3; n++) {
            for (int i = 0; i < 3; i++) {
                arrayBtn[n][i].getButton().setEnabled(false);
            }
        }
    }

    //get the undoUndoButton
    public JButton getUndoButton() {
        return undoButton;
    }

    //get all buttons
    public JButton[][] getButtons() {
        JButton[][] jButtons = new JButton[3][3];
        for (int n = 0; n < 3; n++) {
            for (int i = 0; i < 3; i++) {
                jButtons[n][i] = arrayBtn[n][i].getButton();
            }
        }
        return jButtons;
    }

    public void update(Observable o, Object arg) {
        // TODO Auto-generated method stub
        data = (BoardData) o;
        for (int n = 0; n < 3; n++) {
            for (int i = 0; i < 3; i++) {
                if (data.getData()[n][i] != null) {
                    if (data.getData()[n][i].id == 1) {
                        //mark all button which is selected by player 1 as Green and display the player's name on the button
                        arrayBtn[n][i].getButton().setBackground(Color.GREEN);
                        arrayBtn[n][i].getButton().setOpaque(true);
                        arrayBtn[n][i].getButton().setBorderPainted(true);
                        arrayBtn[n][i].getButton().setText(p0.name);
                    }
                    if (data.getData()[n][i].id == 2) {
                        //mark all button which is selected by player 2 as Red and display the player's name on the button
                        arrayBtn[n][i].getButton().setBackground(Color.RED);
                        arrayBtn[n][i].getButton().setOpaque(true);
                        arrayBtn[n][i].getButton().setBorderPainted(true);
                        arrayBtn[n][i].getButton().setText(p1.name);
                    }

                } else {
                    //mark all non-selected button marked as gray.
                    arrayBtn[n][i].getButton().setForeground(Color.GRAY);
                    arrayBtn[n][i].getButton().setOpaque(false);
                    arrayBtn[n][i].getButton().setBackground(Color.BLACK);
                }

            }
        }
    }
}
