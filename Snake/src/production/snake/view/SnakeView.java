package production.snake.view;

import javax.swing.*;
import javax.swing.JApplet;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.URL;
import javax.imageio.ImageIO;
import java.applet.Applet;
import java.lang.String;
import production.snake.controller.SnakeController;
import production.snake.model.SnakeModel;

/**
 * Created by Diana on 24-Jan-15.
 */
public class SnakeView extends JPanel{
    private final int B_WIDTH = 300;
    private final int B_HEIGHT = 300;
    private int score;
	
    public boolean leftDirection = false;
    public boolean rightDirection = true;
    public boolean upDirection = false;
    public boolean downDirection = false;
    public boolean inGame = true;
	
    private Image ball;
    private Image apple;
    private Image head;
    private SnakeModel model=new SnakeModel();

    public SnakeView()
    {
        score =0;
        model.DELAY=140;

        //addKeyListener(new TAdapter());
        setBackground(Color.black);
        setFocusable(true);

        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        loadImages();
       // initGame();
    }
    public void  loadingGame()
    {

         model.initGame();
    }
    public void loadImages() {

         ImageIcon iid = new ImageIcon("D:\\Snake\\Snake\\src\\dot.png");
         ball = iid.getImage();

        ImageIcon iia = new ImageIcon("D:\\Snake\\Snake\\src\\apple2.png");
        apple = iia.getImage();

        ImageIcon iih = new ImageIcon("D:\\Snake\\Snake\\src\\head.png");
        head = iih.getImage();
    }



    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        doDrawing(g);
    }
    private void doDrawing(Graphics g) {

        if (inGame) {

            String msg = "Score: "+score;
            Font small = new Font("Helvetica", Font.BOLD, 14);
            FontMetrics metr = getFontMetrics(small);

            g.setColor(Color.white);
            g.setFont(small);
            g.drawString(msg, (B_WIDTH - metr.stringWidth(msg))/2, B_HEIGHT/12 );



            g.drawImage(apple, model.apple_x, model.apple_y, this);

            for (int z = 0; z < model.dots; z++) {
                if (z == 0) {
                    g.drawImage(head, model.x[z],model.y[z], this);
                } else {
                    g.drawImage(ball, model.x[z],model.y[z], this);
                }
            }

            Toolkit.getDefaultToolkit().sync();

        } else {

            gameOver(g);
        }
    }

    private void gameOver(Graphics g) {

        String msg = "Game Over! Total Score: "+score;
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, (B_WIDTH - metr.stringWidth(msg)) / 2, B_HEIGHT / 2);
    }

    public void checkApple() {

        if ((model.x[0] == model.apple_x) && (model.y[0] == model.apple_y)) {

            model.dots++;
            score+=10;
            if(score%50==0 && model.DELAY>10)
                model.DELAY-=10;
            model.timer.setDelay(model.DELAY);
            // timer.start();
            model.locateApple();
        }
    }
    public void move() {

        for (int z = model.dots; z > 0; z--) {
            model.x[z] = model.x[(z - 1)];
            model.y[z] = model.y[(z - 1)];
        }

        if (leftDirection) {
            model.x[0] -= model.DOT_SIZE;
        }

        if (rightDirection) {
            model.x[0] +=model.DOT_SIZE;
        }

        if (upDirection) {
            model.y[0] -= model.DOT_SIZE;
        }

        if (downDirection) {
            model.y[0] += model.DOT_SIZE;
        }
    }
    public void checkCollision() {

        for (int z = model.dots; z > 0; z--) {

            if ((z > 4) && (model.x[0] == model.x[z]) && (model.y[0] == model.y[z])) {
                inGame = false;
            }
        }

        if (model.y[0] >= B_HEIGHT) {
            inGame = false;
        }

        if (model.y[0] < 0) {
            inGame = false;
        }

        if (model.x[0] >= B_WIDTH) {
            inGame = false;
        }

        if (model.x[0] < 0) {
            inGame = false;
        }

        if(!inGame) {
            model.timer.stop();
        }
    }



    //open a popup that contains the error message passed
    public void displayErrorMessage(String errorMess)
    {
        JOptionPane.showMessageDialog(this,errorMess);
    }

}
