import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
    // Panel size
    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25; // Size of each grid square
    static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / (UNIT_SIZE * UNIT_SIZE);
    // ^ Calculates total number of grid squares in the panel, Area (360,000px) / unit_size (625px)
    static final int DELAY = 90; // game speed

    // Snake and food variables
    final int[] x = new int[GAME_UNITS]; // Snakes x-coordinates
    final int[] y = new int[GAME_UNITS]; // Snakes y-coordinates
    int bodyParts = 6; // Initial length of snake
    int applesEaten; // Score
    int appleX; // x-coordinate of apple
    int appleY; // y-coordinate of apple
    char direction = 'R'; // Initial direction: 'R' (Right)
    boolean running = false;
    Timer timer;

    // Initial Constructor
    public GamePanel() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(this);
        startGame();
    }

    // Start the game
    public void startGame() {
        newApple();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }

    // Generate new apple
    public void newApple() {
        appleX = (int) (Math.random() * (SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
        appleY = (int) (Math.random() * (SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
    }

    // Draw the game objects
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }
//
    public void draw(Graphics g) {
        if (running) {
            // Draw Grid
            for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
                g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
                g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
            }

            // Draw Apple
            g.setColor(Color.RED);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

            // Draw Snake
            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) { // Snakes head
                    g.setColor(Color.GREEN);
                } else {
                    g.setColor(new Color(45, 180, 0));
                }
                g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
            }

            // Display Score
            g.setColor(Color.WHITE);
            g.setFont(new Font("Ink Free", Font.BOLD, 25));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Game Over")) / 2, SCREEN_HEIGHT / 2);

        } else {
            gameOver(g);
        }
    }

    // Check collisions and handle game over
    public void checkCollisions() {
        // check if head collides with body
        for (int i = bodyParts; i > 0; i--) {
            if ((x[0] == x[1]) && (y[0] == y[1])) {
                running = false;
            }
        }

        // Check if head collides with walls
        if (x[0] < 0 || x[0] >= SCREEN_WIDTH || y[0] < 0 || y[0] >= SCREEN_HEIGHT) {
            running = false;
        }

        if (!running) {
            timer.stop();
        }
    }

    // Display game over screen
    public void gameOver(Graphics g) {
        // Display Score
        g.setColor(Color.RED);
        g.setFont(new Font("Ink Free", Font.BOLD, 50));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Game Over", (SCREEN_WIDTH - metrics.stringWidth("Game Over")) / 2, SCREEN_HEIGHT / 2);
    }

    // Update the game state
    public void move() {
        for (int i = bodyParts; i > 0; i --) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }

        // change direction using switch cases
        switch (direction) {
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                break;
        }
    }

    // checks if apple is eaten and calls method to create new apple
    public void checkApple() {
        if ((x[0] == appleX) && (y[0] == appleY)) {
            bodyParts++;
            applesEaten++;
            newApple();
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (running) {
            move();
            checkApple();
            checkCollisions();
        }
        repaint();
    }

    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                if (direction != 'R') direction = 'L';
                break;
            case KeyEvent.VK_RIGHT:
                if (direction!= 'L') direction = 'R';
                break;
            case KeyEvent.VK_UP:
                if (direction != 'D') direction = 'U';
                break;
            case KeyEvent.VK_DOWN:
                if (direction != 'U') direction = 'D';
                break;
        }
    }

    public void keyReleased(KeyEvent e) {}

    public void keyTyped(KeyEvent e) {}
}