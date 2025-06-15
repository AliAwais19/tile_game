import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.io.File;

public class ImageManager {

    public static List<ImageIcon> loadImages(int count) {
        List<ImageIcon> icons = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            String path = "resources/img" + i + ".png";
            File file = new File(path);
            if (!file.exists()) {
                System.err.println("❌ Image not found: " + path);
            }
            icons.add(new ImageIcon(path));
        }
        return icons;
    }
}
