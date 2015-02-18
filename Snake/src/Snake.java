import production.snake.controller.SnakeControl;
import production.snake.model.SnakeModel;
import production.snake.view.SnakeView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Created by Diana on 14-Feb-15.
 */
public class Snake extends JApplet implements ActionListener  {

    //private static final long serialVersionUID = -1394879975755306414L;

    private JApplet snake;
    Button runbutton=new Button("Play Snake!");

    public Snake ()
    {
        snake=new JApplet();
        snake.setPreferredSize(new Dimension(500, 500));
        snake.init();
    }

    public void init()
    {
        Font font = new Font("Arial",Font.BOLD,60);
        runbutton.setBackground(Color.blue);
        runbutton.setFont(font);
        runbutton.setSize(20,100);
        add(runbutton);
        runbutton.addActionListener(this);
    }

    public void starting()
    {
        SnakeModel model = new SnakeModel (30,40) ;
        SnakeControl control = new SnakeControl (model);
        SnakeView view = new SnakeView (model, control);
        model.addObserver (view);
        (new Thread(model)).start();
    }

    public static void main (String [] args) {
        JApplet ex=new Snake();
        ex.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==runbutton) //verific daca sursa evenimentului este runbutton
            starting();  //daca da, pornesc jocul
    }

}