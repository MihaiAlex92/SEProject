package production.snake.model;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Random;

/**
 * Created by Diana on 14-Feb-15.
 */
public class SnakeModel extends Observable implements Runnable {

    boolean [] [] matrix; // indication position, there is no snake body or food

    public  LinkedList nodeArray = new LinkedList (); // snake body

    public  Node food;

    public int speedlevel = 1;

    int maxX;

    int maxY;

    int Direction = 2; // snake running direction

    public boolean running = true; // running state



    int timeInterval = 140; // interval, in milliseconds

    //  double speedChangeRate = 1.5; // every time the speed of the rate of change

    boolean paused = false; // suspend flag

    public int score = 0; //Score

    //  int countMove = 0; // eat the food before the number of mobile

    // UP and DOWN should be even
    // RIGHT and LEFT should be odd
    public static final int UP = 2;

    public static final int DOWN = 4;

    public static final int LEFT = 1;

    public static final int RIGHT = 3;

    public SnakeModel (int maxX, int maxY) {
        this.maxX = maxX;
        this.maxY = maxY;

        reset ();
    }

    public void reset () {
        Direction = SnakeModel.UP; // snake running direction
        timeInterval = 140; // time interval, in milliseconds
        paused = false; // suspend flag
        running=true;

        score = 0; // Score
        speedlevel=1;
        // countMove = 0; // eat the food before the number of mobile

        // Initial matirx, all cleared
        matrix = new boolean [maxX] [];
        for (int i = 0; i <maxX; ++i) {
            matrix [i] = new boolean [maxY];
            Arrays.fill (matrix [i], false);
        }

        // Initial the snake
        //Initialize the snake body, if the lateral position of more than 20, the length is 10, otherwise, the lateral position of the half
        int initArrayLength = maxX> 20? 10: maxX / 2;
        nodeArray.clear ();
        for (int i = 0; i <initArrayLength; ++i) {
            int x = maxX / 2 + i;
            int y = maxY / 2;
            nodeArray.addLast (new Node (x, y));
            matrix [x] [y] = true;
        }

        // Create food
        food = createFood ();
        matrix [food.x] [food.y] = true;
    }

    public void changeDirection (int newDirection) {
        // Change the direction can not be with the original direction of the same or opposite
        if (Direction% 2!= newDirection% 2) {
            Direction = newDirection;
        }
    }



    public boolean MoveOn () {
        Node n = (Node) nodeArray.getFirst ();
        int x = n.x;
        int y = n.y;

        // According to changes in the direction of the coordinate values
        switch (Direction) {
            case UP:y --;
                break;
            case DOWN:
                y ++;
                break;
            case LEFT:
                x --;
                break;
            case RIGHT:
                x ++;
                break;
        }

        // If a new coordinate falls within the valid range, then the processing
        if ((0 <= x && x <maxX) && (0 <= y && y <maxY)) {
            if (matrix [x] [y])
            {    // if something new coordinates of the point (the snake body or food)
                if (x == food.x && y == food.y) {// eat food successfully
                    nodeArray.addFirst (food); // gift from the snakeheads long

                    score+=10* speedlevel;
                    if(score%80==0 && timeInterval>50) {
                        timeInterval -= 10;
                        speedlevel++;
                    }

                    //  countMove = 0;

                    food = createFood (); // create a new food
                    matrix [food.x] [food.y] = true; // set the location of the food
                    return true;
                }
                else {
                    // Eat the the snake body itself, failure
                    return false;
                }

            } else {// move the snake body
                nodeArray.addFirst (new Node (x, y));
                matrix [x] [y] = true;
                n = (Node) nodeArray.removeLast ();
                matrix [n.x] [n.y] = false;
                // countMove ++;
                return true;
            }
        }

        return false; // touch the edges, failure
    }

    public void run () {
        running = true;
        while (running) {

            try {
                Thread.sleep (timeInterval);

            } catch (Exception e) {
                break;
            }

            if (paused) {
                running=MoveOn();
                if (running) {
                    setChanged (); // Model has been updated to notify the View data
                    notifyObservers ();
                } else {

                    // the game ends, show highscore?

                }

            }
        }

        // sau aici stergere inserare score?

    }

    private  Node createFood () {
        int x = 0;
        int y = 0;
        // Random access to a valid area do not overlap with the snake body and food
        do {
            Random r = new Random ();
            x = r.nextInt(maxX);
            y = r.nextInt (maxY);
        } while (matrix [x] [y]);

        return new Node(x, y);
    }

    /* public void SpeedUp () {
           timeInterval *= speedChangeRate;
           speedlevel++;
       }

        public void speedDown () {
           if(speedlevel>1) {
               timeInterval /= speedChangeRate;
               speedlevel--;
           }
       }
   */
    public void changePauseState () {
        paused =! paused;
    }

    public String toString () {
        String result = "";
        for (int i = 0; i <nodeArray.size (); ++i) {
            Node n = (Node) nodeArray.get (i);
            result += "[" + n.x + "," + n.y + "]";
        }
        return result;
    }
}

