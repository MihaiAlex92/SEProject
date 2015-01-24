 import javax.swing.*;
import javax.swing.JOptionPane;
import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
/**
 * Created by Diana on 11-Dec-14.
 */
public class Snake extends JApplet{

   /*  public void init()
     {
         setSize(600,450);
         Board frame = new Board();
         frame.setVisible(true);
     }

    public Snake() {

        init();
    }
*/
    private JApplet Snaky;
    public Snake ()
    {
        Snaky=new JApplet();
        Snaky.setPreferredSize(new Dimension(500, 500));
        Snaky.init();


        add(new Board()).setVisible(true);
        Snaky.start();
       // setResizable(false);
      //  pack();

       // setTitle("Snake");
       // setLocationRelativeTo(null);
       // setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
            EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    JApplet ex = new Snake();
                    ex.setVisible(true);
                }
            });

    }

   /*public static void main(String[] args) {

        JApplet ex = new Snake();
        ex.setVisible(true);
    }
    */
}








