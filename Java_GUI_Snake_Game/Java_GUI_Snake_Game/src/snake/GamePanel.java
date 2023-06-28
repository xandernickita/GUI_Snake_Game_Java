package snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Color;



public class GamePanel extends JPanel implements ActionListener {

    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;
    static final int DELAY = 75;
    final int[] x = new int[GAME_UNITS];
    final int[] y = new int[GAME_UNITS];
    int bodyParts = 6;
    int applesEaten = 0;
    int appleX;
    int appleY;
    char direction = 'R';
    boolean isRunning = false;
    Timer timer;
    Random randomizer;

    public GamePanel() {
        randomizer = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }

    public void startGame() {
        newApple();
        isRunning = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);

    }

    public void draw(Graphics g) {
        if (isRunning) {
            // Uncomment this code to add gridlines
//            for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
//                g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
//                g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
//            }

            g.setColor(Color.red);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) {
                    g.setColor(Color.green);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                } else {
                    g.setColor(new Color(45, 180, 0));
                    // This is the code for having the snake be/change multiple colors
                    // Just delete this line if you only want it to be green
                    g.setColor(new Color(randomizer.nextInt(255), randomizer.nextInt(255), randomizer.nextInt(255)));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
            g.setColor(Color.red);
            g.setFont(new Font("Ink Free", Font.BOLD, 40));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("SCORE: " + applesEaten, (SCREEN_WIDTH - metrics.stringWidth("SCORE: " + applesEaten)) / 2, g.getFont().getSize());
        } else {
            gameOver(g);
        }
    }

    public void newApple() {
        appleX = randomizer.nextInt((int) (SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
        appleY = randomizer.nextInt((int) (SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
    }

    public void move() {
        for (int i = bodyParts; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
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
            default:
                break;
        }
    }

    public void checkApple() {
        if ((x[0] == appleX) && (y[0] == appleY)) {
            bodyParts++;
            applesEaten++;
            newApple();
        }
    }

    public void checkCollision() {
        // Checks if head collides with body
        for (int i = bodyParts; i > 0; i--) {
            if ((x[0] == x[i]) && (y[0] == y[i])) {
                isRunning = false;
            }
        }
        // Check if head touches left border
        if (x[0] < 0) {
            isRunning = false;
        }
        // Check if head touches right border
        if (x[0] > SCREEN_WIDTH) {
            isRunning = false;
        }
        // Check if head touches top border\
        if (y[0] < 0) {
            isRunning = false;
        }
        // Check if head touches bottom border
        if (y[0] > SCREEN_HEIGHT) {
            isRunning = false;
        }
        // Stop timer if game over
        if (!isRunning) {
            timer.stop();
        }
    }

//    public void gameOver(Graphics g) {
//        // Game Over text
//        g.setColor(Color.red);
//        g.setFont(new Font("Ink Free", Font.BOLD, 75));
//        FontMetrics metrics = getFontMetrics(g.getFont());
//        g.drawString("Game Over!", (SCREEN_WIDTH - metrics.stringWidth("Game Over!")) / 2, SCREEN_HEIGHT / 2);
//
//        // Restart Game button
//        JButton restartButton = new JButton("Restart Game");
//        restartButton.setBounds((SCREEN_WIDTH - 200) / 2, (SCREEN_HEIGHT / 2) + 80, 200, 50);
//        restartButton.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                restartGame(); // Call the restartGame() method
//            }
//        });
//        restartButton.setFocusable(false); // Remove focus from the button
//        restartButton.setBackground(Color.black); // Set button background color
//        restartButton.setForeground(Color.white); // Set button text color
//        restartButton.setFont(new Font("Ink Free", Font.BOLD, 20)); // Set button font
//        add(restartButton);
//
//        // Exit button
//        JButton exitButton = new JButton("Exit");
//        exitButton.setBounds((SCREEN_WIDTH - 100) / 2, (SCREEN_HEIGHT / 2) + 140, 100, 50);
//        exitButton.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                // Exit game logic here
//                System.exit(0); // This line exits the application
//            }
//        });
//        add(exitButton);
//
//        // Refresh the panel to display the buttons
//        repaint();
//    }

    public void gameOver(Graphics g) {
        // Game Over text
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 75));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Game Over!", (SCREEN_WIDTH - metrics.stringWidth("Game Over!")) / 2, SCREEN_HEIGHT / 2);

        // Restart Game button
        JButton restartButton = new JButton("Restart Game");
        restartButton.setBounds((SCREEN_WIDTH - 200) / 2, (SCREEN_HEIGHT / 2) + 80, 200, 50);
        restartButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                restartGame();
            }
        });
        restartButton.setFocusable(false);
        restartButton.setBackground(Color.black);
        restartButton.setForeground(Color.white);
        restartButton.setFont(new Font("Ink Free", Font.BOLD, 20));
        add(restartButton);

        // Exit button
        JButton exitButton = new JButton("Exit");
        exitButton.setBounds((SCREEN_WIDTH - 100) / 2, (SCREEN_HEIGHT / 2) + 140, 100, 50);
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        exitButton.setFocusable(false);
        exitButton.setBackground(Color.black);
        exitButton.setForeground(Color.white);
        exitButton.setFont(new Font("Ink Free", Font.BOLD, 20));
        add(exitButton);

        // Refresh the panel to display the buttons
        repaint();
    }


    public void restartGame() {
        // Reset game variables
        bodyParts = 6;
        applesEaten = 0;
        direction = 'R';
        isRunning = false;
        timer.stop();

        // Clear the board
        for (int i = 0; i < GAME_UNITS; i++) {
            x[i] = 0;
            y[i] = 0;
        }

        // Remove the buttons from the panel
        Component[] components = getComponents();
        for (Component component : components) {
            if (component instanceof JButton) {
                remove(component);
            }
        }

        // Start a new game
        startGame();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (isRunning) {
            move();
            checkApple();
            checkCollision();
        }
        repaint();

        // Check if the game is over
        if (!isRunning) {
            // Remove the buttons from the panel
            Component[] components = getComponents();
            for (Component component : components) {
                if (component instanceof JButton) {
                    remove(component);
                }
            }
        }
    }



    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (direction != 'R') {
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (direction != 'L') {
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (direction != 'D') {
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (direction != 'U') {
                        direction = 'D';
                    }
                    break;
            }
        }
    }
}
