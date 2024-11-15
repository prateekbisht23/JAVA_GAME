package com.prateekbisht.gaming;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;

import com.prateekbisht.gaming.sprites.Enemy;
import com.prateekbisht.gaming.sprites.Player;

public class Board extends JPanel {

    Timer timer;
    BufferedImage backgroundImage;
    BufferedImage buffer;
    Player player;
    Enemy enemies[] = new Enemy[3];

    JButton playAgainButton;
    JButton continueButton;

    int roundCounter = 1;
    int baseEnemySpeed = 2;
    int basePlayerSpeed = 5;
    boolean roundStarting = true;
    boolean gameOver = false;
    
    public Board() {
        setSize(1400, 800);
        loadBackgroundImage();
        player = new Player();
        loadEnemies();
        gameLoop();
        setFocusable(true);
        bindEvents();
        
        buffer = new BufferedImage(1400, 800, BufferedImage.TYPE_INT_ARGB);

        setupButtons();
        setLayout(null);
    }

    private void setupButtons() {
        playAgainButton = new JButton("Restart Game");
        playAgainButton.setFont(new Font("Arial", Font.BOLD, 20));
        playAgainButton.setBounds(540, 400, 150, 50);
        playAgainButton.setVisible(false);
        playAgainButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                roundCounter = 1;
                resetGame();
            }
        });

        continueButton = new JButton("Continue");
        continueButton.setFont(new Font("Arial", Font.BOLD, 20));
        continueButton.setBounds(700, 400, 150, 50);
        continueButton.setVisible(false);
        continueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetGame();
            }
        });

        add(playAgainButton);
        add(continueButton);
    }

    private void displayRoundStart(Graphics pen) {
        if (roundStarting) {
            Font font = new Font("Arial", Font.BOLD, 48);
            pen.setFont(font);
            pen.setColor(Color.RED);
            FontMetrics metrics = pen.getFontMetrics(font);
            String roundMessage = "Round " + roundCounter;
            int x = (getWidth() - metrics.stringWidth(roundMessage)) / 2;
            int y = getHeight() / 2;
            pen.drawString(roundMessage, x, y);

            new Timer(1500, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    roundStarting = false;
                    timer.start();
                    ((Timer) e.getSource()).stop();
                }
            }).start();
        }
    }

    private void gameOver(Graphics pen) {
        if (player.outOfScreen()) {
            roundCounter++;
            if (roundCounter > 5) {
                pen.setColor(Color.RED);
                displayMessage(pen, "You Won!");
                playAgainButton.setVisible(true);
                playAgainButton.setBounds(620, 400, 150, 50);
                gameOver = true;
            } else {
                roundStarting = true;
                resetGame();
            }
            return;
        }

        for (Enemy enemy : enemies) {
            if (isCollide(enemy)) {
                pen.setColor(Color.RED);
                displayMessage(pen, "Game Over");
                gameOver = true;
                playAgainButton.setVisible(true);
                continueButton.setVisible(true);
                timer.stop();
                return;
            }
        }
    }

    private void displayMessage(Graphics pen, String message) {
        Font font = new Font("Arial", Font.BOLD, 50);
        pen.setFont(font);
        FontMetrics metrics = pen.getFontMetrics(font);
        int x = (getWidth() - metrics.stringWidth(message)) / 2;
        int y = (getHeight() - metrics.getHeight()) / 2 + metrics.getAscent() - 20;
        pen.drawString(message, x, y);
    }

    private void resetGame() {
        player.resetPosition();
        gameOver = false;
        roundStarting = true;

        baseEnemySpeed = 2 + roundCounter;
        basePlayerSpeed = 5 + roundCounter;

        loadEnemies();
        playAgainButton.setVisible(false);
        continueButton.setVisible(false);
        repaint();
    }

    private boolean isCollide(Enemy enemy) {
        int paddingX = 30;
        int paddingY = 25;

        Rectangle playerRect = new Rectangle(
            player.getX() + paddingX,
            player.getY() + paddingY,
            player.getW() - 2 * paddingX,
            player.getH() - 2 * paddingY
        );

        Rectangle enemyRect = new Rectangle(
            enemy.getX() + paddingX,
            enemy.getY() + paddingY,
            enemy.getW() - 2 * paddingX,
            enemy.getH() - 2 * paddingY
        );

        return playerRect.intersects(enemyRect);
    }

    private void bindEvents() {
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {
                player.setSpeed(0);
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    player.setSpeed(basePlayerSpeed);
                } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    player.setSpeed(-basePlayerSpeed);
                }
            }
        });
    }

    private void loadEnemies() {
        int x = 300;
        for (int i = 0; i < enemies.length; i++) {
            enemies[i] = new Enemy(x + (i * 400), baseEnemySpeed + (i * 2));
        }
    }

    private void gameLoop() {
        timer = new Timer(16, (e) -> repaint());
    }

    private void drawPlayer(Graphics pen) {
        player.draw(pen);
        player.move();
    }

    private void drawEnemies(Graphics pen) {
        for (Enemy enemy : enemies) {
            enemy.draw(pen);
            enemy.move();
        }
    }

    private void loadBackgroundImage() {
        try {
            backgroundImage = ImageIO.read(Board.class.getResource("GameBG.jpg"));
        } catch (IOException e) {
            System.out.println("Background Image Not Found...");
            System.exit(1);
            e.printStackTrace();
        }
    }

    @Override
    public void paintComponent(Graphics pen) {
        super.paintComponent(pen);
        Graphics2D bufferGraphics = buffer.createGraphics();
        bufferGraphics.clearRect(0, 0, buffer.getWidth(), buffer.getHeight());

        bufferGraphics.drawImage(backgroundImage, 0, 0, 1400, 800, null);

        if (roundStarting) {
            displayRoundStart(bufferGraphics);
        } else if (!gameOver) {
            drawPlayer(bufferGraphics);
            drawEnemies(bufferGraphics);
            bufferGraphics.setFont(new Font("Arial", Font.BOLD, 24));
            bufferGraphics.setColor(Color.BLACK);
            bufferGraphics.drawString("Round: " + roundCounter, 20, 30);
        }

        gameOver(bufferGraphics);
        pen.drawImage(buffer, 0, 0, null);
        bufferGraphics.dispose();
    }
}
