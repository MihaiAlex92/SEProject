package production.snake.model;

import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * Created by Diana on 24-Jan-15.
 */
public class SnakeModel
{
    public final int ALL_DOTS = 900;
    public int dots;
    public final int x[] = new int[ALL_DOTS];
    public final int y[] = new int[ALL_DOTS];
    public Timer timer;
    public int DELAY ;
    public final int DOT_SIZE = 10;
    public final int RAND_POS = 29;
    public int apple_x;
    public int apple_y;

    public void initGame() {

        dots = 3;

        for (int z = 0; z < dots; z++) {
            x[z] = 50 - z * 10;
            y[z] = 50;
        }

        locateApple();

        timer = new Timer(DELAY, (ActionListener) this);
        timer.start();
    }
    public void locateApple() {

        int r = (int) (Math.random() * RAND_POS);
        apple_x = ((r * DOT_SIZE));

        r = (int) (Math.random() * RAND_POS);
        apple_y = ((r * DOT_SIZE));
    }


}
