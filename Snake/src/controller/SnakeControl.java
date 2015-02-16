package controller;

import model.SnakeModel;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
        * MVC Controler responsible for receiving a user's operation, and the user operation notification Model
 * /

/**
 * Created by Diana on 14-Feb-15.
 */
public class SnakeControl implements KeyListener{

    SnakeModel model;

    public SnakeControl (SnakeModel model) {
        this.model=model;
    }

    public void keyPressed (KeyEvent e) {
        int keyCode = e.getKeyCode ();
        if  (model.running) {// running state, the processing of the button
            switch (keyCode) {
                case KeyEvent.VK_UP:
                    model.changeDirection (SnakeModel.UP);
                    break;
                case KeyEvent.VK_DOWN:
                    model.changeDirection (SnakeModel.DOWN);
                    break;
                case KeyEvent.VK_LEFT:
                    model.changeDirection (SnakeModel.LEFT);
                    break;
                case KeyEvent.VK_RIGHT:
                    model.changeDirection (SnakeModel.RIGHT);
                    break;
                case KeyEvent.VK_ADD:
                case KeyEvent.VK_PAGE_UP:
                    model.SpeedUp ();
                    break;
                case KeyEvent.VK_SUBTRACT:
                case KeyEvent.VK_PAGE_DOWN:
                    model.speedDown ();
                    break;
                case KeyEvent.VK_SPACE:
                case KeyEvent.VK_P:
                    model.changePauseState ();
                    break;
                default:
            }
        }

        // Any case processing keys, buttons lead to restart the game
        if (keyCode == KeyEvent.VK_R ||keyCode == KeyEvent.VK_S || keyCode == KeyEvent.VK_ENTER) {
            model.reset ();
        }
    }

    public void keyReleased (KeyEvent e) {
    }

    public void keyTyped (KeyEvent e) {
    }

}
