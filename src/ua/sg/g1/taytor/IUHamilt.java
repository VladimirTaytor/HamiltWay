package ua.sg.g1.taytor;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


public class IUHamilt extends JFrame {
    //Window
    private int number=numberReader();
    private String first_point;
    private JPanel panel = new JPanel();
    private JLabel label = new JLabel();
    private JTextField text = new JTextField();
    private JButton button = new JButton("Go");
    private JButton button2 = new JButton("1'st point");
    private Toolkit toolkit = getToolkit();
    private JMenuBar menubar = new JMenuBar();
    private JMenu menu =new JMenu("New");
    private JMenuItem menuitem=new JMenuItem("Add");
    private JMenuItem menuExit=new JMenuItem("Exit");
    private JMenuItem menuRestart= new JMenuItem("Restart");
    //GAmilt

    IUHamilt(String tittle) {
        super(tittle);
        numberReader();
    }

    void windowShow() {
        //Menu
        menu.add(menuitem);
        menu.add(menuRestart);
        menu.add(menuExit);
        menubar.add(menu);
        setJMenuBar(menubar);
        //Panel
        panel.add(label);
        panel.add(button);
        panel.add(text);
        panel.add(button2);
        add(panel);
        //Listeners
        menuRestart.addActionListener(e -> restart());
        menuitem.addActionListener(e -> addmatr());
        button.setEnabled(false);
        menuExit.addActionListener(e -> System.exit(0));
        button2.addActionListener(e -> {
            button.setEnabled(true);
            first_point = text.getText();
        });
        button.addActionListener(e -> {
            label.setText(null);
            main();
        });
        //Position
        Dimension size = toolkit.getScreenSize();
        setLocation(size.width / 2 - 115, size.height / 2 - 100);
        //Windowset
        text.setPreferredSize(new Dimension(50, 25));
        button.setPreferredSize(new Dimension(220, 41));
        label.setPreferredSize(new Dimension(230, 50));

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(new Dimension(250, 200));
        setVisible(true);
        setResizable(false);
    }
    private String s_matr="";

    private void addmatr(){
        JFrame frame = new JFrame("New matr");
        JPanel panelAdd = new JPanel();
        JTextArea textAreaAdd = new JTextArea();
        JButton buttonAdd = new JButton("add");
        JTextField numberAdd = new JTextField();
        Dimension size = toolkit.getScreenSize();
        frame.setLocation(size.width / 2 - 115, size.height / 2 - 100);

        panelAdd.add(textAreaAdd);
        panelAdd.add(numberAdd);
        panelAdd.add(buttonAdd);
        frame.add(panelAdd);

        numberAdd.setPreferredSize(new Dimension(40,20));
        textAreaAdd.setPreferredSize(new Dimension(200,125));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setPreferredSize(new Dimension(250, 225));
        frame.pack();
        frame.setVisible(true);

        buttonAdd.addActionListener(e -> {
            number = Integer.parseInt(numberAdd.getText());
            try(FileWriter fos=new FileWriter(".//src//file1.txt",false)){
                fos.append(number+ "");
            }
            catch(IOException ex){
                JOptionPane.showMessageDialog(panelAdd,"Smth go wrong ;(");
            }


            s_matr=textAreaAdd.getText();
            try(FileOutputStream fos=new FileOutputStream(".//src//file.txt"))
            {
                byte[] buffer = s_matr.getBytes();
                fos.write(buffer, 0, buffer.length);
            }
            catch(IOException ex){
                JOptionPane.showMessageDialog(panelAdd,"Smth go wrong ;(");
            }
        });
    }

//gamilt
    private int[][] matr = new int[number][number];
    private int[] turn = new int[number];
    private int[] path = new int[number];
    private int v0 = 0;



            private void printg(){
                for (int i = 0; i <number; i++) {
                    System.out.print((path[i]+1)+"->");
                }
                System.out.print(path[0]+1);
                System.out.println();
            }
    private void prnt() {
        int i;
        StringBuilder text = new StringBuilder(" ");
        for (i = 0; i < number; i++) {
            text.append(Integer.toString((path[i] + 1)) + "->");
        }
        text.append(path[0] + 1);
        label.setText(text.toString());
    }

    private int gamilton(int k) {
        int exist = 0;
        try {
            for (int i = 0; i < number; i++) {
                if (matr[i][path[k - 1]] == 1 && matr[path[k - 1]][i] == 1) {
                    if (k == number && i == v0) { prnt(); printg();}
                    else if (turn[i] == -1){
                        turn[i] = k;
                        System.out.print(i+" ");
                        path[k] = i;
                        exist = gamilton(k + 1);
                        if (exist == 0) {turn[i] = -1;
                            System.out.println();}
                    } else continue;
                }
            }
        } catch (ArrayIndexOutOfBoundsException ex) {
            label.setText("Please make correct insert.");
        }
        return exist;
    }

    private int main() {
        reader();
        int j = 0;
        try {
            j = Integer.parseInt(first_point);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(panel, "Input error!");
        }
        if (j > number || j < 0) {
            JOptionPane.showMessageDialog(panel, "1'st point doesn't exist");
        }
        try {
        for (int i = 0; i < number; i++) turn[i] = -1;
            v0 = j - 1;
            path[0] = v0;
            turn[v0] = v0;
        } catch (ArrayIndexOutOfBoundsException ex) {
            label.setText("Please make correct insert.");
        }
        int h=gamilton(1);
        /*if (gamilton(1) == 1) prnt();
        else JOptionPane.showMessageDialog(panel, "Loop doesn't exist");*/
        return 0;
    }

    private void reader() {
         try {
             File f = new File(".//src//file.txt");
             Scanner sc = new Scanner(f);
             int i = 0;
             try{while (sc.hasNextInt()) {
                 for (int j = 0; j < number; j++) {
                     matr[i][j] = sc.nextInt();
                    }
                 i++;
                }
             }catch (ArrayIndexOutOfBoundsException ex) {
                 JOptionPane.showMessageDialog(panel, "Smth go wrong ;(");
             }
         }  catch (IOException ex) {
             JOptionPane.showMessageDialog(panel, "Smth go wrong ;(");
         }
     }

       private int numberReader(){
            try{
            File f = new File(".//src//file1.txt");
            Scanner sc = new Scanner(f);
            while (sc.hasNextInt()) {
                number = sc.nextInt();
                }
            } catch (IOException ex) {
        JOptionPane.showMessageDialog(panel, "Smth go wrong ;(");
    }
           return number;
        }

    private void restart(){
        this.dispose();
        new IUHamilt("Ham").windowShow();
    }
}
