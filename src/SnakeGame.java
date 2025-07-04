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

    // Snake
    Tile snakeHead;

    // Food
    Tile food;

    
    SnakeGame(int boardWidth, int boardHeight) {
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        setBackground(Color.BLACK);
        setFocusable(true);
        requestFocusInWindow();

        snakeHead = new Tile(5,5);
        food = new Tile(10, 10);
        // Add key listener for snake movement
        }
        
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            draw(g);
        }

        public void draw(Graphics g) {
            // Grid
            for (int i = 0; i < boardWidth; i += tileSize) {
                for (int j = 0; j < boardHeight; j += tileSize) {
                    g.setColor(Color.DARK_GRAY);
                    g.drawRect(i, j, tileSize, tileSize);
                }
            }

            // Food
            g.setColor(Color.red);
            g.fillRect(food.x * tileSize, food.y * tileSize, tileSize, tileSize);

            // Snake
            g.setColor(Color.green);
            g.fillRect(snakeHead.x * tileSize, snakeHead.y * tileSize, tileSize, tileSize);

        }
}
