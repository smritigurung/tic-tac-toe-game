import javax.swing.*;
import java.awt.*;

/**
 * This class creates a button with shaking property
 */
public class Button {
    public JButton button;
    public String buttonName;
    public int shakingTime = 0;

    /**
     * a constructor to create a new JButton
     *
     * @param str the value of button
     */
    public Button(String str) {
        buttonName = str;
        button = new JButton(buttonName);
    }

    /**
     * a constructor to change current button name
     *
     * @param b a JButton
     */
    public Button(JButton b) {
        this.button = b;
        this.buttonName = b.getName();
    }

    /**
     * accessor method
     *
     * @return button, which is JButton
     */
    public JButton getButton() {
        return button;
    }

    /**
     * this method creates a thread to shake the button with a delay of 75.
     */
    public void shakeButton() {
        final Point point = button.getLocation();
        final int delay = 75;
        Runnable r = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < shakingTime; i++) {
                    try {


                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                button.setLocation(new Point(point.x + 5, point.y));
                            }
                        });

                        Thread.sleep(delay);
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                button.setLocation(point);
                            }
                        });
                        Thread.sleep(delay);
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                button.setLocation(new Point(point.x - 5, point.y));
                            }
                        });
                        Thread.sleep(delay);
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                button.setLocation(point);
                            }
                        });
                        Thread.sleep(delay);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        };
        Thread t = new Thread(r);
        t.start();
    }

}
