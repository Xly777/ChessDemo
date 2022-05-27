package view;

import controller.ClickController;
import controller.GameController;
import model.ChessColor;

import javax.swing.*;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

import java.awt.*;
import java.io.*;
import java.lang.reflect.MalformedParameterizedTypeException;
import java.net.MalformedURLException;
import java.util.concurrent.atomic.AtomicInteger;

import static view.ChessGameFrame.time;
import static view.ChessGameFrame.timeLabel;

/**
 * 这个类表示游戏过程中的整个游戏界面，是一切的载体
 */
public class ChessGameFrame extends JFrame {
    //    public final Dimension FRAME_SIZE ;
    private final int WIDTH;
    private final int HEIGTH;
    public final int CHESSBOARD_SIZE;
    public int a=1;
    public Chessboard chessboard=null;
    public GameController gameController;
    public static JLabel statusLabel = new JLabel("White");
    ImageIcon imageIcon = new ImageIcon(".\\images\\41bf1dd81ebacbb3b69a73042e988740.jpeg");//插入图片
    JLabel labelImage = new JLabel(imageIcon);
    static JLabel roundLabel=new JLabel();
    public static int round;
    public static int time=30;
    static JLabel timeLabel=new JLabel();



    public ChessGameFrame(int width, int height) {
        setTitle("2022 CS102A Project "); //设置标题
        this.WIDTH = width;
        this.HEIGTH = height;
        this.CHESSBOARD_SIZE = HEIGTH * 4 / 5;

        setSize(WIDTH, HEIGTH);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
//        setLayout(null);
//        ImageIcon imageIcon = new ImageIcon(".\\images\\41bf1dd81ebacbb3b69a73042e988740.jpeg");//插入图片
//        JLabel labelImage = new JLabel(imageIcon);

        addChessboard();
        addLabel();
        addLoadButton();
        addInitButton();
        addSaveButton();
        addLabel2();
        addNewButton();
        add(labelImage);

        new Thread((new MyThread(chessboard))).start();
    }


    /**
     * 在游戏面板中添加棋盘
     */
    private void addChessboard() {
        Chessboard chessboard = new Chessboard(CHESSBOARD_SIZE, CHESSBOARD_SIZE);
        this.chessboard=chessboard;
        gameController = new GameController(chessboard);
        chessboard.setLocation(HEIGTH / 10, HEIGTH / 10);
        add(chessboard);
    }

    /**
     * 在游戏面板中添加标签
     */
    private void addLabel2() {
        JLabel current = new JLabel("Current Player:");
        current.setLocation(HEIGTH, HEIGTH / 10 - 40);
        current.setSize(200, 60);
        current.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(current);

        roundLabel.setText("Round: "+round/2);
        roundLabel.setLocation(HEIGTH, HEIGTH / 10 +40);
        roundLabel.setSize(200, 60);
        roundLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(roundLabel);

        timeLabel.setText("Remaining Time: "+time);
        timeLabel.setLocation(HEIGTH, HEIGTH / 10 +80);
        timeLabel.setSize(200, 60);
        timeLabel.setFont(new Font("Rockwell", Font.BOLD, 15));
        add(timeLabel);

    }

    static void addRound(){
        round++;
        roundLabel.setText("Round: "+round/2);
    }

    private void addLabel() {
        statusLabel.setLocation(HEIGTH, HEIGTH / 10);
        statusLabel.setSize(200, 60);
        statusLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(statusLabel);



    }

    public static void changeLabel(ChessColor color) {
        if (color == ChessColor.WHITE) {
            statusLabel.setText("White");
        } else {
            statusLabel.setText("Black");
        }
    }


    private void addLoadButton() {
        JButton button = new JButton("Load");
        button.setLocation(HEIGTH, HEIGTH / 10 + 250);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.addActionListener(e -> {
            System.out.println("Click load");
            JFileChooser jfchooser = new JFileChooser(".\\loadData");
            int option = jfchooser.showOpenDialog(null);
            String path = "";
            FileFilterTest fileFilter = new FileFilterTest();
            jfchooser.setFileFilter(fileFilter);
            if (option == JFileChooser.APPROVE_OPTION) {
                if (jfchooser.getSelectedFile() == null) {
                    JOptionPane.showMessageDialog(null, "need txt file", "wrong", JOptionPane.ERROR_MESSAGE);
                } else {
                    path = jfchooser.getSelectedFile().getPath();
                }
            }
            gameController.loadGameFromFile(path);
        });
        round=0;
    }

    private void addInitButton() {
        JButton button = new JButton("Initiate");
        button.setLocation(HEIGTH, HEIGTH / 10 + 170);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.addActionListener(e -> {
            this.gameController.getChessboard().initiateEmptyChessboard();
            gameController.loadGameFromFile(".\\loadData\\initiate.txt");
            this.gameController.getChessboard().repaint();
            round=0;
        });

    }

    private void addSaveButton() {
        JButton button = new JButton("Save");
        button.setLocation(HEIGTH, HEIGTH / 10 + 330);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.addActionListener(e -> {
            String name = JOptionPane.showInputDialog(this, "Input file name here");
            File save = new File(".\\loadData\\", name + ".txt");
            if (!save.getParentFile().exists()) {
                save.getParentFile().mkdirs();
            }
            try {
                Writer out = new FileWriter(save);
                for (int i = 0; i < 9; i++) {
                    out.write(gameController.getChessboard().saveGame().get(i));
                    out.write("\n");
                }
                out.close();
            } catch (IOException a) {
                a.printStackTrace();
            }
        });
    }
    private void addNewButton(){
        JButton button2=new JButton("New theme");
        button2.setBounds(800,20,100,30);
        button2.setFont(new Font("Rockwell",Font.BOLD,10));
        button2.addActionListener(e -> {
            a++;
            if (a%2==1){
                ImageIcon imageIcon2=new ImageIcon(".\\images\\41bf1dd81ebacbb3b69a73042e988740.jpeg");
                labelImage.setIcon(imageIcon2);
                add(labelImage);
                this.repaint();
            }
            if (a%2==0){
                ImageIcon imageIcon2=new ImageIcon(".\\images\\b06e94838a673cbc4789445a1bbc5c54.jpeg");
                labelImage.setIcon(imageIcon2);
                add(labelImage);
                this.repaint();
            }
        });
        add(button2);
    }

    static class FileFilterTest extends javax.swing.filechooser.FileFilter {
        public boolean accept(java.io.File f) {
            if (f.isDirectory()) return true;
            return f.getName().endsWith(".txt");
        }

        @Override
        public String getDescription() {
            return "need txt file";
        }

    }
}

class MyThread implements Runnable{
    Chessboard chessboard=null;

    public MyThread(Chessboard chessboard){
        this.chessboard=chessboard;
    }
    @Override
    public void run() {
        while (time>0){
            time--;
            timeLabel.setText("Remaining Time: "+time);
            try{
                Thread.sleep(1000);
            }catch (Exception e){
                e.printStackTrace();
            }
            if(time==0){
                chessboard.swapColor();
            }
        }
    }
}