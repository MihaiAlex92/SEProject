
import production.snake.model.SnakeModel;
import production.snake.controller.SnakeControl;
import production.snake.view.SnakeView;



/**
 * Created by Diana on 14-Dec-14.
 */
public class Snake{

    public static void main (String [] args) {
        SnakeModel model = new SnakeModel (30,40) ; // modify the operating range of the snake
        SnakeControl control = new SnakeControl (model);
        SnakeView view = new SnakeView (model, control);
        model.addObserver (view);

        (new Thread(model)).start();

    }

}
