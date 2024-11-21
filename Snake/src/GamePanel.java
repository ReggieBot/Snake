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
    static final int DELAY = 75; // game speed

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

    // Inital Constructor

}