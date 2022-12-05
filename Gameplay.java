//imports for the Gameplay class
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import javax.swing.*;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Gameplay extends JPanel implements KeyListener, ActionListener{
    /** reference variables for Gameplay class **/
    private boolean play = false;
    private int score = 0;

    private int totalBricks = 21;

    private MapGenerator map;

    //controlling the speed of the ball or the game
    private Timer timer;
    private int delay = -10;

    /** initializing the position of the player and ball **/
    private int playerX = 310;

    private int ballposX = (int)(Math.random()*641) + 10; //max 650 <= ballposX <= min 10
    private int ballposY = (int)(Math.random()*181) + 250; //max 430 <= ballposY <= min 250
    private int ballXdir = (int)(Math.random()*-2)-2;   //random direction
    private int ballYdir = (int)(Math.random()*-4)-3;   //random direction

    public Gameplay() {
        //array with 3 rows and 7 columns
        map = new MapGenerator(3, 7);
        //keyListener for player key movement controls
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        //setting speed and start of game
        timer = new Timer(delay, this);
        timer.start();

    }

    //display setting method
    public void paint(Graphics g) {
        Random random = new Random();

        //background
        g.setColor(Color.black);
        g.fillRect(1, 1, 692, 592);

        //drawing map
        map.draw((Graphics2D) g);

        //borders
        g.setColor(Color.yellow);
        //left border
        g.fillRect(0, 0, 3, 590);
        //top border
        g.fillRect(0, 0, 700, 3);
        //right border
        g.fillRect(682, 0, 3, 590);

        //score
        g.setColor (Color.white);
        g.setFont (new Font("serif" , Font.BOLD, 25));
        g.drawString (""+score, 590, 30);

        // this paddle
        g.setColor(Color.green);
        g.fillRect(playerX, 550, 100, 8);

        // this ball
        g.setColor(Color.yellow);
        g.fillOval(ballposX, ballposY, 20, 20);

        if(totalBricks <= 0) {
            play = false;
            ballXdir = 0;
            ballYdir = 0;
            g.setColor(Color.red);
            g.setFont (new Font("serif" , Font.BOLD, 30));
            g.drawString ("You Won: ", 260, 300);

            g.setFont (new Font("serif" , Font.BOLD, 20));
            g.drawString ("Press ENTER to RESTART", 230, 350);
        }

        if (ballposY > 560) {
            play = false;
            ballXdir = 0;
            ballYdir = 0;
            g.setColor(Color.red);
            g.setFont (new Font("serif" , Font.BOLD, 30));
            g.drawString ("Game Over, Score: ", 200, 300);

            g.setFont (new Font("serif" , Font.BOLD, 20));
            g.drawString ("Press ENTER to RESTART", 205, 350);

            //borders
            g.setColor(Color.red);
            //left border
            g.fillRect(0, 0, 3, 590);
            //top border
            g.fillRect(0, 0, 700, 3);
            //right border
            g.fillRect(682, 0, 3, 590);

            // this paddle
            g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
            g.fillRect(playerX, 550, 100, 8);
        }

        g.dispose();

    }

    //functionality game method
    @Override
    public void actionPerformed(ActionEvent e) {
        Random random = new Random();
        timer.start();
        if(play) {
            if(new Rectangle (ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))) {
                ballYdir = -ballYdir;
            }

            //set and remove brick functionalities
            A: for (int i = 0; i <map.map.length; i++){
                for (int j = 0; j < map.map[0].length; j++) {
                    if(map.map[i][j] > 0) {
                        int brickX = j* map.brickWidth + 80;
                        int brickY = i * map.brickHeight + 50;
                        int brickWidth = map.brickWidth;
                        int brickHeight = map.brickHeight;

                        Rectangle rect = new Rectangle (brickX, brickY, brickWidth, brickHeight);
                        Rectangle ballRect = new Rectangle (ballposX, ballposY, 20, 20);
                        Rectangle brickRect = rect;

                        //interaction conditions
                        if (ballRect.intersects (brickRect)) {
                            map.setBrickValue (0, i, j);
                            totalBricks --;
                            score += 5;
                            if (ballposX + 19 <= brickRect.x || ballposX + 1 >= brickRect.x + brickRect.width) {
                                ballXdir = -ballXdir;
                            }else {
                                ballYdir = -ballYdir;
                            }

                            break A;
                        }
                    }
                }
            }

            //interaction direction of the ball
            ballposX += ballXdir;
            ballposY += ballYdir;
            //left border
            if(ballposX < 0) {
                ballXdir = - ballXdir;
            //top border
            }if(ballposY < 0) {
                ballYdir = - ballYdir;
            //right border
            }if(ballposX > 670) {
                ballXdir = - ballXdir;
            }
        }

        //rest the map, "repaint"
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if(playerX >= 600) {
                playerX = 600;
            } else {
                moveRight();
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_LEFT) {
            if(playerX < 10) {
                playerX = 10;
            } else {
                moveLeft();
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!play) {
                play = true;
                ballposX = 120;
                ballposY = 350;
                ballXdir = -1;
                ballYdir = -2;
                playerX = 310;
                score = 0;
                totalBricks = 21;
                map = new MapGenerator(3, 7);

                repaint();
            }
        }
    }

    public void moveRight() {
        play= true;
        playerX+= 20;
    }

    public void moveLeft() {
        play= true;
        playerX-= 20;
    }

}
