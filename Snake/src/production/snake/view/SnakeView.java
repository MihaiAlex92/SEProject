package production.snake.view;

import production.snake.controller.SnakeControl;
import production.snake.model.Node;
import production.snake.model.SnakeModel;

import javax.swing.*;
import java.awt.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;
/**
 * Created by Diana on 14-Dec-14.
 */

/**
 * MVC mode too Viewer, is only responsible for the display of data, without worrying about whether the control logic of the game
 */
public class SnakeView implements Observer{

    SnakeControl control = null;
    SnakeModel model = null;

    JFrame mainFrame;
    Canvas paintCanvas;
    JLabel labelScore;
    JButton hqz;

    public static final int width=300, height=300, canvasWidth = 300 ;// surface version
    public static final int canvasHeight = 400;

    public static final int nodeWidth = 10;
    public static final int nodeHeight = 10;

    public SnakeView(SnakeModel model, SnakeControl control) {
        this.model=model;
        this.control = control;

        mainFrame = new JFrame ("Snake");

        Container cp = mainFrame.getContentPane ();

        //Create the top scores

        labelScore = new JLabel ("Score:");

        cp.add (labelScore, BorderLayout.NORTH);

        // Create the middle of the game display area
        paintCanvas = new Canvas ();
        paintCanvas.setSize (canvasWidth + 1, canvasHeight + 1);
        paintCanvas.addKeyListener (control);
        cp.add (paintCanvas, BorderLayout.CENTER);

        //Create the help of the under bar
        JPanel panelButtom = new JPanel ();
        panelButtom.setLayout (new BorderLayout ());
        panelButtom.setBackground (Color.ORANGE);

        JLabel labelHelp;

      /*  labelHelp = new JLabel ("speed: PageUp + PageDown",JLabel.CENTER);// PageUp, PageDown for Speed
                panelButtom.add (labelHelp, BorderLayout.NORTH);
        labelHelp = new JLabel (": ENTER or R or S;", JLabel.CENTER) ;//ENTER or R or S for start
        panelButtom.add (labelHelp, BorderLayout.CENTER); */

        labelHelp = new JLabel ("Press Space for Start/Pause \n Press R for Reset", JLabel.CENTER) ;//Space or P for pause
        panelButtom.add (labelHelp, BorderLayout.SOUTH);

        cp.add (panelButtom, BorderLayout.SOUTH);

        mainFrame.addKeyListener (control);
        mainFrame.pack ();
        mainFrame.setResizable (false);
        mainFrame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        mainFrame.setVisible (true);
    }

    void repaint () {
        Graphics g = paintCanvas.getGraphics ();

        // Draw background
        g.setColor (Color.WHITE);
        g.fillRect (0, 0, canvasWidth, canvasHeight);

        if(model.running) { // Draw the snake
            g.setColor(Color.black);
            LinkedList na = model.nodeArray;
            Iterator it = na.iterator();
            Node n;
            while (it.hasNext()) {
                n = (Node) it.next();
                drawNode(g, n);
            }

            // Draw the food
            g.setColor(Color.RED);
            n = model.food;
            drawNode(g, n);
        }
        else
        {
            g.setColor(Color.WHITE);
            LinkedList na = model.nodeArray;
            Iterator it = na.iterator();
            Node n;
            while (it.hasNext()) {
                n = (Node) it.next();
                drawNode(g, n);
            }

            // Draw the food
            g.setColor(Color.WHITE);
            n = model.food;
            drawNode(g, n);
        }

        UpdateScore ();
    }

    private void drawNode (Graphics g, Node n) {
        g.fillRect (n.x * nodeWidth,
                n.y * nodeHeight,
                nodeWidth - 1,
                nodeHeight - 1);
    }

    public void UpdateScore () {
        String s = "Score:"+ model.score ;// Score

        labelScore.setText (s);

    }

    public void update (Observable o, Object arg) {
        repaint ();
    }
}
