package Custom_Elements;

import javax.swing.*;
import java.awt.*;

public class CButton extends JButton {

    public CButton(String text, int size, Color backgroundColor, Color foregroundColor){
        super(text);
        setFont(new Font("Comic Sans MS", Font.BOLD, size));
        setAlignmentX(Component.CENTER_ALIGNMENT);
        setBackground(backgroundColor);
        setForeground(foregroundColor);
        setFocusable(false);

        setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 0));
    }
}
