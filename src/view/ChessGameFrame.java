package view;

import controller.GameController;
import model.ChessColor;

import javax.swing.*;
import java.awt.*;
import java.io.*;

/**
 * 这个类表示游戏过程中的整个游戏界面，是一切的载体
 */
public class ChessGameFrame extends JFrame {
    //    public final Dimension FRAME_SIZE ;
    private final int WIDTH;
    private final int HEIGTH;
    public final int CHESSBOARD_SIZE;
    private GameController gameController;
    public static JLabel statusLabel = new JLabel("White");

    public ChessGameFrame(int width, int height) {
        setTitle("2022 CS102A Project Demo"); //设置标题
        this.WIDTH = width;
        this.HEIGTH = height;
        this.CHESSBOARD_SIZE = HEIGTH * 4 / 5;

        setSize(WIDTH, HEIGTH);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);


        addChessboard();
        addLabel();
        addHelloButton();
        addLoadButton();
        addInitButton();
        addSaveButton();
        addLabel2();
    }


    /**
     * 在游戏面板中添加棋盘
     */
    private void addChessboard() {
        Chessboard chessboard = new Chessboard(CHESSBOARD_SIZE, CHESSBOARD_SIZE);
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

    /**
     * 在游戏面板中增加一个按钮，如果按下的话就会显示Hello, world!
     */

    private void addHelloButton() {
        JButton button = new JButton("Show Hello Here");
        button.addActionListener((e) -> JOptionPane.showMessageDialog(this, "Hello, world!"));
        button.setLocation(HEIGTH, HEIGTH / 10 + 120);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }

    private void addLoadButton() {
        JButton button = new JButton("Load");
        button.setLocation(HEIGTH, HEIGTH / 10 + 240);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.addActionListener(e -> {
            System.out.println("Click load");
            JFileChooser jfchooser = new JFileChooser("C:\\Users\\Xly\\IdeaProjects\\ChessDemo\\loadData");
            int option = jfchooser.showOpenDialog(null);
            String path = "";
            if (option == JFileChooser.APPROVE_OPTION) {
                path = jfchooser.getSelectedFile().getPath();
            }
            gameController.loadGameFromFile(path);
        });
    }

    private void addInitButton() {
        JButton button = new JButton("initiate");
        button.setLocation(HEIGTH, HEIGTH / 10 + 320);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.addActionListener(e -> {
            this.gameController.getChessboard().initiateEmptyChessboard();
            gameController.loadGameFromFile("C:\\Users\\Xly\\IdeaProjects\\ChessDemo\\loadData\\initiate.txt");
            this.gameController.getChessboard().repaint();
        });
    }

    private void addSaveButton() {
        JButton button = new JButton("Save");
        button.setLocation(HEIGTH, HEIGTH / 10 + 400);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.addActionListener(e -> {
            String name = JOptionPane.showInputDialog(this, "Input file name here");
            File save = new File("C:\\Users\\Xly\\IdeaProjects\\ChessDemo\\loadData\\", name);
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
}
