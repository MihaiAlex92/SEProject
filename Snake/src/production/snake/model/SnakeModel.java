package production.snake.model;

import javax.swing.*;
import java.io.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Random;

/**
 * Created by Diana on 14-Feb-15.
 */

/**
 * MVC Model responsabil cu datele jocului si rularea acestuia
 */
public class SnakeModel extends Observable implements Runnable { //pentru instanta acestei clase vom folosi un thread

    boolean [] [] matrix; // indica pozitia, daca exista sarpele sau mancarea
    public  LinkedList nodeArray = new LinkedList (); // corpul sarpelui
    public  Node food;
    public int speedlevel = 1;
    int maxX;
    int maxY;
    int Direction = 2; // directia de mers a sarpelui , initial merge in sus
    public boolean running =true; //starea de deplasare
    int timeInterval = 140; // interval, in ms
    boolean paused = false; //flag-ul pentru suspendare
    public int score = 0; //Scor

    public String hscore=null;
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
        //matricea initiala, toate componentele au valoarea false
        matrix = new boolean [maxX] [];
        for (int i = 0; i <maxX; ++i)
        {
            matrix [i] = new boolean [maxY];
            Arrays.fill (matrix [i], false);
        }

        //Sarpele initial
        //Initializare corp sarpe, daca pozitia lateral este mai mare de 20, lungimea este 10
        //altfel 1/2
        int initArrayLength = maxX> 20? 10: maxX / 2;
        nodeArray.clear ();
        for (int i = 0; i <initArrayLength; ++i) {
            int x = maxX / 2 + i;
            int y = maxY / 2;
            nodeArray.addLast (new Node (x, y));
            matrix [x] [y] = true;
        }

        //creare mancare
        food = createFood ();
        matrix [food.x] [food.y] = true;
    }

    public void changeDirection (int newDirection) {
        //schimbam directia, dar nu putem trece de exemplu de la UP direct la DOWN
        if (Direction% 2!= newDirection% 2) {
            Direction = newDirection;
        }
    }

    public boolean MoveOn () {
        Node n = (Node) nodeArray.getFirst ();
        int x = n.x;
        int y = n.y;

        //schimbarile directiei duc la schimbarea  valorilor coordonatelor
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

        //daca o noua coordonata se afla in  intervalul valid, procesam
        if ((0 <= x && x <maxX) && (0 <= y && y <maxY)) {
            if (matrix [x] [y]) {//daca exista ceva :o parte din corpul sarpelui sau mancarea
                if (x == food.x && y == food.y) { //mananca
                    nodeArray.addFirst (food); //adauga mancarea

                    //regulile scorului
                    score  +=10*speedlevel;
                    if( score%80==0 && timeInterval>50)
                    {
                        timeInterval-=10;
                        speedlevel++;
                    }

                    food = createFood (); //cream mancarea
                    matrix [food.x] [food.y] = true; // setam locatia mancarii
                    return true;
                } else
                    // esec
                    return false;
            } else { //daca nu exista ceva (corpul sarpelui), sarpele se poate deplasa in continuare
                nodeArray.addFirst (new Node (x, y));
                matrix [x] [y] = true;
                n = (Node) nodeArray.removeLast ();
                matrix [n.x] [n.y] = false;
                return true;
            }
        }
        return false; //a atins marginile
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
                    setChanged (); //modelul a fost updatat, se va notifica View-ul
                    notifyObservers (); //notificam toti observer-i acestui element
                } else {
                    //highscore
                    Checkscore();
                    int a=JOptionPane.showConfirmDialog (null,"Play again!","GAME OVER!", JOptionPane.INFORMATION_MESSAGE);
                    if(a==0) {
                        JOptionPane.showMessageDialog(null, "Press Space to play!", "Play", JOptionPane.WARNING_MESSAGE);
                        reset(); //daca s-a apasat OK, jocul se va restarta si trebuie apasata tasta Space pentru a incepe
                    }
                    else
                        System.exit(1);



                }

            }
        }
        // running = false;
        // sau aici
    }
    private  Node createFood () {
        int x = 0;
        int y = 0;

        //creare random a mancarii fara  a se suprapune peste sarpe sau  mancare
        do {
            Random r = new Random ();
            x = r.nextInt(maxX);
            y = r.nextInt (maxY);
        } while (matrix [x] [y]);

        return new Node (x, y);
    }

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


    public void Checkscore() {

        hscore=getHighScore();
        if (score > Integer.parseInt(hscore.split(":")[1])) {
            String name = JOptionPane.showInputDialog("You set a new highscore.Please enter your name:");

            if (name.equals(null)) {
                name = "Annonimous";
            }
            hscore=name + ":" +score;
            File scoreFile=new File("C:\\highscore.dat");
            if(!scoreFile.exists())
                try {
                    scoreFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            FileWriter writeFile=null;
            BufferedWriter writer=null;
            try {
                writeFile=new FileWriter(scoreFile);
                writer=new BufferedWriter(writeFile);
                writer.write(hscore);

            }
            catch (Exception e)
            {

            }

            finally {
                try {
                    if (writer != null)
                        writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public String getHighScore() {

        FileReader readFile = null;


        BufferedReader reader = null;

        try {
            readFile = new FileReader("C:\\highscore.dat");
            reader = new BufferedReader(readFile);
            return reader.readLine();
        } catch (Exception e) {
            return "Nobody:0";
        } finally {
            try {
                if (reader != null)
                    reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}

