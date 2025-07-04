import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class SnakeGame extends JPanel {

    private class Tile {
        int x, y;

        Tile(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    int boardWidth;
    int boardHeight;
    int tileSize = 25;

    Tile snakeHead;

    SnakeGame(int boardWidth, int boardHeight) {
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        setBackground(Color.BLACK);
        setFocusable(true);
        requestFocusInWindow();

        snakeHead = new Tile(5,5);
        // Add key listener for snake movement
        }
        
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            draw(g);
        }

        public void draw(Graphics g) {
            for (int i = 0; i < boardWidth; i += tileSize) {
                for (int j = 0; j < boardHeight; j += tileSize) {
                    g.setColor(Color.DARK_GRAY);
                    g.drawRect(i, j, tileSize, tileSize);
                }
            }
            // snake
            g.setColor(Color.green);
            g.fillRect(snakeHead.x * tileSize, snakeHead.y * tileSize, tileSize, tileSize);

        }
}
