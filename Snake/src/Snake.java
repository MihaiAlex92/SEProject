import javax.swing.*;
import java.awt.EventQueue;

/**
 * Created by Diana on 11-Dec-14.
 */
public class Snake extends JFrame{
    public Snake() {

        add(new Board());

        setResizable(false);
        pack();

        setTitle("Snake");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                //adaugare cadru pentru pronire cu start/highscore/exit
                //modificare fundal snake+adaugare margini vizibile
                //adaugare elemente noi pe fundal pe langa mar
                //ecran final+highscore
                //adaugare sunete
                JFrame ex = new Snake();
                ex.setVisible(true);
            }
        });
    }

}
