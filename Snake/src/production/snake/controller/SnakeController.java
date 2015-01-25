package production.snake.controller;

import production.snake.model.SnakeModel;
import production.snake.view.SnakeView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import static com.sun.java.accessibility.util.AWTEventMonitor.addKeyListener;

/**
 * Created by Diana on 24-Jan-15.
 */
public class SnakeController {

    private SnakeView theView=new SnakeView();
    private SnakeModel theModel=new SnakeModel();


    public SnakeController(SnakeView theView, SnakeModel theModel)
    {
        this.theView=theView;
        this.theModel=theModel;

        addKeyListener(new TAdapter());
    }

    public class changeListener implements ActionListener
    {
        public void actionPerformed(ActionEvent arg0)
        {
            try {
                theView.loadingGame();
                if (theView.inGame) {

                    theView.checkApple();
                    theView.checkCollision();
                    theView.move();
                }

                theView.repaint();


            } catch (Exception e) {
                // e.printStackTrace();
                System.out.println(e);
                theView.displayErrorMessage("An error occurred  !");
            }
        }

    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();

            if ((key == KeyEvent.VK_LEFT) && (!theView.rightDirection)) {
                theView.leftDirection = true;
                theView.upDirection = false;
                theView.downDirection = false;
            }

            if ((key == KeyEvent.VK_RIGHT) && (! theView.leftDirection)) {
                theView.rightDirection = true;
                theView.upDirection = false;
                theView.downDirection = false;
            }

            if ((key == KeyEvent.VK_UP) && (!theView.downDirection)) {
                theView.upDirection = true;
                theView.rightDirection = false;
                theView.leftDirection = false;
            }

            if ((key == KeyEvent.VK_DOWN) && (!theView.upDirection)) {
                theView.downDirection = true;
                theView.rightDirection = false;
                theView.leftDirection = false;
            }
        }
    }
}
