import javax.swing.*;
import java.awt.*;

public interface AnimatableButtonCreator {
    JButton createButton(int xp, int yp, String value);
}

class NoAnimationButtonCreator implements AnimatableButtonCreator {
    public JButton createButton(int xp, int yp, String value) {
        JButton btn = new JButton(xp + Integer.toString(yp) + value);
        btn.setName("n+Integer.toString(i)+value");
        btn.setBackground(Color.GRAY);
        btn.setLocation(xp, yp);
        btn.setPreferredSize(new Dimension(100, 100));
        return btn;
    }
}

class AnimatedButtonCreator implements AnimatableButtonCreator {
    public JButton createButton(int xp, int yp, String value) {
        Button btn = new Button(xp + Integer.toString(yp) + value);
        btn.getButton().setName("n+Integer.toString(i)+value");
        btn.getButton().setBackground(Color.GRAY);
        btn.getButton().setLocation(xp, yp);
        btn.getButton().setPreferredSize(new Dimension(100, 100));
        return btn.getButton();
    }
}
