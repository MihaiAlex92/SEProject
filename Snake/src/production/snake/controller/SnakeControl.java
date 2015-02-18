package production.snake.controller;

import production.snake.model.SnakeModel;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * MVC Controller gestioneaza actiunea unui user si notifica Modelul
 */

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
        if  (model.running) {
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
                case KeyEvent.VK_R:
                    model.reset();
                    break;
                case KeyEvent.VK_SPACE:
                case KeyEvent.VK_P:
                    model.changePauseState ();
                    break;
                default:
            }
        }
    }

    public void keyReleased (KeyEvent e) {
    }

    public void keyTyped (KeyEvent e) {
    }

}
