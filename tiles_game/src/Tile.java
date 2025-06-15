// Tile.java
import javax.swing.*;

public class Tile extends JButton {
    private final ImageIcon frontIcon;
    private final ImageIcon backIcon;
    private boolean isFlipped = false;
    private boolean isMatched = false;

    public Tile(ImageIcon frontIcon, ImageIcon backIcon) {
        this.frontIcon = frontIcon;
        this.backIcon = backIcon;
        setIcon(backIcon); // Start with back side showing
    }

    public void flip() {
        if (isFlipped) {
            setIcon(backIcon);
        } else {
            setIcon(frontIcon);
        }
        isFlipped = !isFlipped;
    }

    public boolean isFlipped() {
        return isFlipped;
    }

    public boolean isMatched() {
        return isMatched;
    }

    public void setMatched(boolean matched) {
        isMatched = matched;
    }

    public ImageIcon getFrontIcon() {
        return frontIcon;
    }

    public String getFrontImageName() {
        return frontIcon.getDescription() != null ? frontIcon.getDescription() : frontIcon.toString();
    }
}
