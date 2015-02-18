package production.snake.view;

import production.snake.controller.SnakeControl;
import production.snake.model.SnakeModel;
import production.snake.model.Node;

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
 * MVC View este responsabil doar cu interfata grafica
 */
public class SnakeView  implements Observer{  // implementeaza  interfata Observer pentru a fi informat in legatura cu schimbarile unui obiect de tip observable

    SnakeControl control = null;
    SnakeModel model = null;

    JFrame mainFrame;
    Canvas paintCanvas;
    JLabel labelScore;

    public static final int  canvasWidth = 300 ;// suprafata jocului
    public static final int canvasHeight = 400;

    public static final int nodeWidth = 10;
    public static final int nodeHeight = 10;

    public SnakeView (SnakeModel model, SnakeControl control) {

        this.model=model;
        this.control = control;
        mainFrame = new JFrame ("Snake");
        Container cp = mainFrame.getContentPane ();
        labelScore = new JLabel ("Score:");
        cp.add (labelScore, BorderLayout.NORTH);

        // zona jocului din mijloc
        paintCanvas = new Canvas ();
        paintCanvas.setSize (canvasWidth + 1, canvasHeight + 1);
        paintCanvas.addKeyListener (control);
        cp.add (paintCanvas, BorderLayout.CENTER);

        //zona de help cu instructiuni
        JPanel panelButtom = new JPanel ();
        panelButtom.setLayout (new BorderLayout ());
        panelButtom.setBackground (Color.green);

        JLabel labelHelp;
        labelHelp = new JLabel ("Press Space for Start/Pause!", JLabel.CENTER) ;//Space or P for pause
        panelButtom.add (labelHelp, BorderLayout.NORTH);
        labelHelp = new JLabel ("Press P for pause!", JLabel.CENTER) ;
        panelButtom.add (labelHelp, BorderLayout.CENTER);
        labelHelp = new JLabel ("Press R for Reset then press Space!", JLabel.CENTER) ;
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

        // desenam background
        g.setColor (Color.WHITE);
        g.fillRect (0, 0, canvasWidth, canvasHeight);

        // desenam  sarpele
        g.setColor (Color.black);
        LinkedList na = model.nodeArray;
        Iterator it = na.iterator ();

        while (it.hasNext ()) {
            Node n;
            n  = (Node)it.next ();
            drawNode (g, n);
        }

        // desenam mancarea, patratele rosii
        g.setColor (Color.RED);
        Node n = model.food;
        drawNode (g, n);

        UpdateScore ();
    }

    private void drawNode (Graphics g, Node n) {
        g.fillRect (n.x * nodeWidth, n.y * nodeHeight,nodeWidth - 1,nodeHeight - 1);
    }

    public void UpdateScore () {
        String s = "Score:"+ model.score +" "+ model.getHighScore() ;
        labelScore.setText (s);
    }
    //apelata cand obiectul observat se schimba, SnakeModel extends Observable
    public void update (Observable o, Object arg) {
        repaint ();
    }
}
