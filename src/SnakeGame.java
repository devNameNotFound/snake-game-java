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
    ArrayList<Tile> snakeBody; // Not used yet, but can be used for snake body segments

    // Food
    Tile food;
    Random random;

    // Game logic
    Timer gameLoop;
    int velocityX;
    int velocityY;
    boolean gameOver = false;
    
    SnakeGame(int boardWidth, int boardHeight) {
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);
        requestFocusInWindow();

        snakeHead = new Tile(5,5);
        snakeBody = new ArrayList<Tile>();

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

            // Snake Head
            g.setColor(Color.green);
            g.fillRect(snakeHead.x * tileSize, snakeHead.y * tileSize, tileSize, tileSize);

            // Snake Body
            for (int i = 0; i < snakeBody.size(); i++) {
                Tile segment = snakeBody.get(i);
                g.setColor(Color.green.darker());
                g.fillRect(segment.x * tileSize, segment.y * tileSize, tileSize, tileSize);
            }

            // Score
            g.setFont(new Font("Arial", Font.BOLD, 16));
            if (gameOver) {
                g.setColor(Color.red);
                g.drawString("Game Over!: " + String.valueOf(snakeBody.size()), tileSize - 16, tileSize);
            }
            else {
                g.drawString("Score: " + String.valueOf(snakeBody.size()), tileSize - 16, tileSize);
            }
        }

        public void placeFood() {
            food.x = random.nextInt(boardWidth/tileSize); //600/25=24
            food.y = random.nextInt(boardHeight/tileSize);
        }

        public boolean collision(Tile tile1, Tile tile2) {
            // eat food
            return tile1.x == tile2.x && tile1.y == tile2.y;
        }

        public void move() {
            //eat food
            if (collision(snakeHead, food)) {
                snakeBody.add(new Tile(food.x, food.y));
                placeFood();
            }

            //move snake body
            for (int i = snakeBody.size()-1; i >= 0; i--) {
                Tile snakePart = snakeBody.get(i);
                if (i == 0) { //right before the head
                    snakePart.x = snakeHead.x;
                    snakePart.y = snakeHead.y;
                }
                else {
                    Tile prevSnakePart = snakeBody.get(i-1);
                    snakePart.x = prevSnakePart.x;
                    snakePart.y = prevSnakePart.y;
                }
            }
            //move snake head
            snakeHead.x += velocityX;
            snakeHead.y += velocityY;

            //game over conditions
            for (int i = 0; i < snakeBody.size(); i++) {
                Tile snakePart = snakeBody.get(i);

                //collide with snake head
                if (collision(snakeHead, snakePart)) {
                    gameOver = true;
                }
            }

            if (snakeHead.x*tileSize < 0 || snakeHead.x*tileSize > boardWidth || //passed left border or right border
                snakeHead.y*tileSize < 0 || snakeHead.y*tileSize > boardHeight ) { //passed top border or bottom border
                gameOver = true;
            }
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            move();
            repaint();
            if (gameOver) {
                gameLoop.stop();
            }
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
