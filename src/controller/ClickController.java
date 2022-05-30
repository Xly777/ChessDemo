package controller;


import model.ChessColor;
import model.ChessComponent;
import model.KingChessComponent;
import model.PawnChessComponent;
import view.ChessGameFrame;
import view.Chessboard;
import view.ChessboardPoint;

import javax.sound.sampled.*;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class ClickController {
    private final Chessboard chessboard;
    private ChessComponent first;
    private Clip clip;
    private AudioInputStream audioInput;
    private File bgm;
    private Clip clip1;
    private AudioInputStream audioInput1;
    private File bgm1;
    private void addMusic() throws IOException, LineUnavailableException, UnsupportedAudioFileException {
        bgm = new File(".\\music\\失败背景.wav");
        try {
            clip = AudioSystem.getClip();
            audioInput = AudioSystem.getAudioInputStream(bgm);
            clip.open(audioInput);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addMusic1() throws IOException, LineUnavailableException, UnsupportedAudioFileException {
        bgm = new File(".\\music\\胜利背景.wav");
        try {
            clip1 = AudioSystem.getClip();
            audioInput1 = AudioSystem.getAudioInputStream(bgm);
            clip1.open(audioInput1);
            clip1.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void addMusic2() throws IOException, LineUnavailableException, UnsupportedAudioFileException {
        bgm = new File(".\\music\\种下音效.wav");
        try {
            clip1 = AudioSystem.getClip();
            audioInput1 = AudioSystem.getAudioInputStream(bgm);
            clip1.open(audioInput1);
            clip1.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public ClickController(Chessboard chessboard) {
        this.chessboard = chessboard;
    }

    private void whereCanMOve(){
        List<ChessboardPoint> chessboardPoints=first.whereCanMoveTo(chessboard.getChessComponents());

        for (int i = 0; i < chessboardPoints.size(); i++) {
            boolean flag = chessboard.getChessComponents()[chessboardPoints.get(i).getX()][chessboardPoints.get(i).getY()].isSelected();
            chessboard.getChessComponents()[chessboardPoints.get(i).getX()][chessboardPoints.get(i).getY()].setSelected(!flag);
            chessboard.getChessComponents()[chessboardPoints.get(i).getX()][chessboardPoints.get(i).getY()].repaint();
        }

    }
    public void onClick(ChessComponent chessComponent) {
        if (first == null) {
            if (handleFirst(chessComponent)) {
                chessComponent.setSelected(true);
                first = chessComponent;
                first.move(chessboard.getChessComponents(),1);
                first.repaint();
            }
        } else {
            if (first == chessComponent) { // 再次点击取消选取
                chessComponent.setSelected(false);
                ChessComponent recordFirst = first;
                first.move(chessboard.getChessComponents(),0);
//                whereCanMOve();
                first = null;
                recordFirst.repaint();
            } else if (handleSecond(chessComponent)) {
                //repaint in swap chess method.
                try {
                    addMusic2();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                first.move(chessboard.getChessComponents(),0);
                chessboard.swapChessComponents(first, chessComponent);
                chessboard.swapColor();
                first.setSelected(false);
                chessboard.kingAttack(first);
                first = null;
                if (chessComponent instanceof KingChessComponent && chessComponent.getChessColor() == ChessColor.WHITE) {
                    try {
                        addMusic();
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                    Object[] options ={ "restart", "exit" };  //自定义按钮上的文字
                    int m = JOptionPane.showOptionDialog(null, "The Black wins!", "Game over", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                    if(m==0){
                        this.chessboard.initiateEmptyChessboard();
                        ChessGameFrame.round=0;
                        try {
                            List<String> chessData = Files.readAllLines(Path.of(".\\loadData\\initiate.txt"));
                            chessboard.loadGame(chessData);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        this.chessboard.repaint();
                    }else{
                        System.exit(0);
                    }
                }
                else if(chessComponent instanceof KingChessComponent && chessComponent.getChessColor() == ChessColor.BLACK){
                    try {
                        addMusic1();
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                    Object[] options ={ "restart", "exit" };  //自定义按钮上的文字
                    int m = JOptionPane.showOptionDialog(null, "The White wins!", "Game over", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
                    if(m==0){
                        this.chessboard.initiateEmptyChessboard();
                        ChessGameFrame.round=0;
                        try {
                            List<String> chessData = Files.readAllLines(Path.of(".\\loadData\\initiate.txt"));
                            chessboard.loadGame(chessData);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        this.chessboard.repaint();
                    }else{
                        System.exit(0);
                    }
                }
            }
            if(first instanceof PawnChessComponent ){
                if(first.getChessColor()==ChessColor.WHITE&&first.getChessboardPoint().getX()==0){
                    Object[] options={"Queen","Knight","Rook","Bishop"};
                    String name=(String) JOptionPane.showInputDialog(null,"Choose the chess to promote:\n","Promotion",JOptionPane.PLAIN_MESSAGE,null,options,null);
                    if(name.equals("Queen")){
                        chessboard.initQueenOnboard(first.getChessboardPoint().getX(),first.getChessboardPoint().getY(),ChessColor.WHITE);
                        chessboard.repaint();
                    }else if(name.equals("Knight")){
                        chessboard.initKnightOnBoard(first.getChessboardPoint().getX(),first.getChessboardPoint().getY(),ChessColor.WHITE);
                        chessboard.repaint();
                    }else if(name.equals("Rook")){
                        chessboard.initRookOnBoard(first.getChessboardPoint().getX(),first.getChessboardPoint().getY(),ChessColor.WHITE);
                        chessboard.repaint();
                    }else if(name.equals("Bishop")){
                        chessboard.initBishopOnBoard(first.getChessboardPoint().getX(),first.getChessboardPoint().getY(),ChessColor.WHITE);
                        chessboard.repaint();
                    }
                }else if(first.getChessColor()==ChessColor.BLACK&&first.getChessboardPoint().getX()==7){
                    Object[] options={"Queen","Knight","Rook","Bishop"};
                    String name=(String) JOptionPane.showInputDialog(null,"Choose the chess to promote:\n","Promotion",JOptionPane.PLAIN_MESSAGE,null,options,null);
                    if(name.equals("Queen")){
                        chessboard.initQueenOnboard(first.getChessboardPoint().getX(),first.getChessboardPoint().getY(),ChessColor.BLACK);
                        chessboard.repaint();
                    }else if(name.equals("Knight")){
                        chessboard.initKnightOnBoard(first.getChessboardPoint().getX(),first.getChessboardPoint().getY(),ChessColor.BLACK);
                        chessboard.repaint();
                    }else if(name.equals("Rook")){
                        chessboard.initRookOnBoard(first.getChessboardPoint().getX(),first.getChessboardPoint().getY(),ChessColor.BLACK);
                        chessboard.repaint();
                    }else if(name.equals("Bishop")){
                        chessboard.initBishopOnBoard(first.getChessboardPoint().getX(),first.getChessboardPoint().getY(),ChessColor.BLACK);
                        chessboard.repaint();
                    }
                }
            }
        }
    }

    /**
     * @param chessComponent 目标选取的棋子
     * @return 目标选取的棋子是否与棋盘记录的当前行棋方颜色相同
     */

    private boolean handleFirst(ChessComponent chessComponent) {
        return chessComponent.getChessColor() == chessboard.getCurrentColor();
    }

    /**
     * @param chessComponent first棋子目标移动到的棋子second
     * @return first棋子是否能够移动到second棋子位置
     */

    private boolean handleSecond(ChessComponent chessComponent) {
        return chessComponent.getChessColor() != chessboard.getCurrentColor() &&
                first.canMoveTo(chessboard.getChessComponents(), chessComponent.getChessboardPoint());
    }
}
