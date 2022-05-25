package controller;


import model.ChessColor;
import model.ChessComponent;
import model.KingChessComponent;
import view.ChessGameFrame;
import view.Chessboard;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class ClickController {
    private final Chessboard chessboard;
    private ChessComponent first;

    public ClickController(Chessboard chessboard) {
        this.chessboard = chessboard;
    }

    public void onClick(ChessComponent chessComponent) {
        if (first == null) {
            if (handleFirst(chessComponent)) {
                chessComponent.setSelected(true);
                first = chessComponent;
                first.repaint();
            }
        } else {
            if (first == chessComponent) { // 再次点击取消选取
                chessComponent.setSelected(false);
                ChessComponent recordFirst = first;
                first = null;
                recordFirst.repaint();
            } else if (handleSecond(chessComponent)) {
                //repaint in swap chess method.
                chessboard.swapChessComponents(first, chessComponent);
                chessboard.swapColor();
                first.setSelected(false);
                first = null;
                if (chessComponent instanceof KingChessComponent && chessComponent.getChessColor() == ChessColor.WHITE) {
                    Object[] options ={ "restart", "exit" };  //自定义按钮上的文字
                    int m = JOptionPane.showOptionDialog(null, "The Black wins!", "Game over", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                    if(m==0){
                        this.chessboard.initiateEmptyChessboard();
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
                    Object[] options ={ "restart", "exit" };  //自定义按钮上的文字
                    int m = JOptionPane.showOptionDialog(null, "The White wins!", "Game over", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
                    if(m==0){
                        this.chessboard.initiateEmptyChessboard();
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
