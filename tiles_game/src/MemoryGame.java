import javax.swing.*;

public class MemoryGame {
    private static JFrame frame;
    private static int currentLevel = 1;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            frame = new JFrame("Memory Game");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(700, 700);
            frame.setLocationRelativeTo(null);

            runLevel(1);
            frame.setVisible(true);
        });
    }

    private static void runLevel(int level) {
        int gridSize = (level == 1) ? 4 : 6;
        int previewTime = (level == 1) ? 5 : 8;

        GameBoard board = new GameBoard(gridSize, previewTime);
        frame.getContentPane().removeAll();          // Clear previous content
        frame.add(board);                            // Add new board
        frame.setTitle("Level " + level + ": " + gridSize + "x" + gridSize);
        frame.revalidate();                          // Refresh UI
        frame.repaint();

        // Watch for level completion
        Timer levelWatcher = new Timer(500, e -> {
            if (board.isLevelComplete()) {
                ((Timer) e.getSource()).stop();
                if (level == 1) {
                    JOptionPane.showMessageDialog(frame, "Level 1 Complete! Starting Level 2...");
                    runLevel(2);
                } else {
                    JOptionPane.showMessageDialog(frame, "Congratulations! You completed both levels.");
                }
            }
        });
        levelWatcher.start();
    }
}
