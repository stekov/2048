import javax.swing.*;
import java.awt.*;

public class Square extends JPanel {
    int value = 0;
    private final JLabel label;

    // All squares updates after an OK move
    Square(int i, int j){
        // Layout for centering
        super(new GridBagLayout());
        label = new JLabel("");
        label.setFont(new Font("Verdana", Font.BOLD, 30));
        this.add(label);
    }

    void updateSquare(String newText) {
        value = Integer.parseInt(newText);
        if (value == 0) {
            newText = " ";
        }
        label.setText(newText);
    }

    void paintSquare(int r, int g, int b){     // Uppdaterar status för knappen
        this.setBackground(new Color(r, g, b));
    }

}
