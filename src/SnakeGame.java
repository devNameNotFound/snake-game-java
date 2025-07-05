import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class SnakeGame extends JPanel implements ActionListener, KeyListener {

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
    Random random;

    // Game logic
    Timer gameLoop;
    int velocityX;
    int velocityY;

    SnakeGame(int boardWidth, int boardHeight) {
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        setBackground(Color.BLACK);
        setFocusable(true);
        requestFocusInWindow();
        addKeyListener(this);

        snakeHead = new Tile(5,5);
        food = new Tile(10, 10);
        random = new Random();
        placeFood();

        velocityX = 0;
        velocityY = 0;

        gameLoop = new Timer(100, this);
        gameLoop.start();
        
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

        public void placeFood() {
            food.x = random.nextInt(boardWidth/tileSize); //600/25=24
            food.y = random.nextInt(boardHeight/tileSize);
        }

        public void move() {
            snakeHead.x += velocityX;
            snakeHead.y += velocityY;

            // Check for food collision
            if (snakeHead.x == food.x && snakeHead.y == food.y) {
                placeFood();
            }

            // Check for wall collision
            if (snakeHead.x < 0 || snakeHead.x >= boardWidth/tileSize ||
                snakeHead.y < 0 || snakeHead.y >= boardHeight/tileSize) {
                gameLoop.stop();
                JOptionPane.showMessageDialog(this, "Game Over! You hit the wall.");
                System.exit(0);
            }
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            move();
            repaint();
        }
        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_UP && velocityY != 1) {
                velocityX = 0;
                velocityY = -1;
            } else if (e.getKeyCode() == KeyEvent.VK_DOWN && velocityY != -1) {
                velocityX = 0;
                velocityY = 1;
            } else if (e.getKeyCode() == KeyEvent.VK_LEFT && velocityX != 1) {
                velocityX = -1;
                velocityY = 0;
            } else if (e.getKeyCode() == KeyEvent.VK_RIGHT && velocityX != -1) {
                velocityX = 1;
                velocityY = 0;
            }
        }


        // Do not need
        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyReleased(KeyEvent e) {
        }
}
