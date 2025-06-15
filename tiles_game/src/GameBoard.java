// GameBoard.java
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

public class GameBoard extends JPanel {
    private final int gridSize;
    private final Tile[][] tiles;
    private Tile selectedTile1 = null;
    private Tile selectedTile2 = null;
    private final javax.swing.Timer flipBackTimer;
    private final Queue<String> matchHistory = new LinkedList<>();

    public GameBoard(int gridSize, int previewTime) {
        this.gridSize = gridSize;
        this.tiles = new Tile[gridSize][gridSize];
        setLayout(new GridLayout(gridSize, gridSize));

        List<ImageIcon> images = loadImages(gridSize);
        Collections.shuffle(images);

        ImageIcon backIcon = new ImageIcon("resources/back.png");

        int index = 0;
        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                Tile tile = new Tile(images.get(index++), backIcon);
                tile.addActionListener(e -> handleTileClick(tile));
                tiles[row][col] = tile;
                add(tile);
            }
        }

        showPreview(previewTime);

        flipBackTimer = new javax.swing.Timer(1000, e -> {
            if (selectedTile1 != null && selectedTile2 != null) {
                selectedTile1.flip();
                selectedTile2.flip();
                selectedTile1 = null;
                selectedTile2 = null;
            }
        });
        flipBackTimer.setRepeats(false);
    }

    private List<ImageIcon> loadImages(int gridSize) {
        List<ImageIcon> images = new ArrayList<>();
        int numPairs = (gridSize * gridSize) / 2;
        for (int i = 1; i <= numPairs; i++) {
            ImageIcon icon = new ImageIcon("resources/img" + i + ".png");
            icon.setDescription("img" + i);  // set description for identification
            images.add(icon);
            images.add(icon);
        }
        return images;
    }

    private void showPreview(int seconds) {
        for (Tile[] row : tiles) {
            for (Tile tile : row) {
                tile.flip();
            }
        }

        javax.swing.Timer timer = new javax.swing.Timer(seconds * 1000, e -> {
            for (Tile[] row : tiles) {
                for (Tile tile : row) {
                    tile.flip();
                }
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    private void handleTileClick(Tile tile) {
        if (tile.isFlipped() || tile.isMatched() || selectedTile2 != null) return;

        tile.flip();

        if (selectedTile1 == null) {
            selectedTile1 = tile;
        } else {
            selectedTile2 = tile;
            checkForMatch();
        }
    }

    private void checkForMatch() {
        if (selectedTile1.getFrontIcon().equals(selectedTile2.getFrontIcon())) {
            selectedTile1.setMatched(true);
            selectedTile2.setMatched(true);
            matchHistory.add(selectedTile1.getFrontImageName() + " matched!");
            selectedTile1 = null;
            selectedTile2 = null;

            if (isLevelComplete()) {
                showMatchHistory();
            }
        } else {
            flipBackTimer.start();
        }
    }

    private void showMatchHistory() {
        StringBuilder sb = new StringBuilder("You matched the following tiles in order:\n\n");
        for (String match : matchHistory) {
            sb.append(match).append("\n");
        }
        JOptionPane.showMessageDialog(this, sb.toString(), "Match History", JOptionPane.INFORMATION_MESSAGE);
    }

    public boolean isLevelComplete() {
        for (Tile[] row : tiles) {
            for (Tile tile : row) {
                if (!tile.isMatched()) {
                    return false;
                }
            }
        }
        return true;
    }

    public Queue<String> getMatchHistory() {
        return matchHistory;
    }
}
