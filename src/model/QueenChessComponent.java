package model;

import view.ChessboardPoint;
import controller.ClickController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 这个类表示国际象棋里面的车
 */
public class QueenChessComponent extends ChessComponent {
    /**
     * 黑车和白车的图片，static使得其可以被所有车对象共享
     * <br>
     * FIXME: 需要特别注意此处加载的图片是没有背景底色的！！！
     */
    private static Image QUEEN_WHITE;
    private static Image QUEEN_BLACK;

    /**
     * 车棋子对象自身的图片，是上面两种中的一种
     */
    private Image queenImage;

    /**
     * 读取加载车棋子的图片
     *
     * @throws IOException
     */
    public void loadResource() throws IOException {
        if (QUEEN_WHITE == null) {
            QUEEN_WHITE = ImageIO.read(new File(".\\images\\棋子\\IMG_0875.PNG"));
        }

        if (QUEEN_BLACK == null) {
            QUEEN_BLACK = ImageIO.read(new File(".\\images\\棋子\\IMG_0869.PNG"));
        }
    }


    /**
     * 在构造棋子对象的时候，调用此方法以根据颜色确定rookImage的图片是哪一种
     *
     * @param color 棋子颜色
     */

    private void initiateQueenImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                queenImage = QUEEN_WHITE;
            } else if (color == ChessColor.BLACK) {
                queenImage = QUEEN_BLACK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public QueenChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size) {
        super(chessboardPoint, location, color, listener, size);
        initiateQueenImage(color);
    }

    /**
     * 车棋子的移动规则
     *
     * @param chessComponents 棋盘
     * @param destination     目标位置，如(0, 0), (0, 7)等等
     * @return 车棋子移动的合法性
     */

    @Override
    public boolean canMoveTo(ChessComponent[][] chessComponents, ChessboardPoint destination) {
        ChessboardPoint source = getChessboardPoint();
        int x = source.getX();
        int y = source.getY();
        List<ChessboardPoint> canMoveTo = new ArrayList<>();
        for (int i = 1; i < 8 - x; i++) {
            if (chessComponents[x + i][y] instanceof EmptySlotComponent) {
                canMoveTo.add(new ChessboardPoint(x + i, y));
            } else if (!(chessComponents[x + i][y] instanceof EmptySlotComponent) && chessComponents[x + i][y].getChessColor() != this.getChessColor()) {
                canMoveTo.add(new ChessboardPoint(x + i, y));
                break;
            } else {
                break;
            }
        }
        for (int i = 1; i <= x; i++) {
            if (chessComponents[x - i][y] instanceof EmptySlotComponent) {
                canMoveTo.add(new ChessboardPoint(x - i, y));
            } else if (!(chessComponents[x - i][y] instanceof EmptySlotComponent) && chessComponents[x - i][y].getChessColor() != this.getChessColor()) {
                canMoveTo.add(new ChessboardPoint(x - i, y));
                break;
            } else {
                break;
            }

        }
        for (int i = 1; i < 8 - y; i++) {
            if (chessComponents[x][y + i] instanceof EmptySlotComponent) {
                canMoveTo.add(new ChessboardPoint(x, y + i));
            } else if (!(chessComponents[x][y + i] instanceof EmptySlotComponent) && chessComponents[x][y + i].getChessColor() != this.getChessColor()) {
                canMoveTo.add(new ChessboardPoint(x, y + i));
                break;
            } else {
                break;
            }

        }
        for (int i = 1; i <= y; i++) {
            if (chessComponents[x][y - i] instanceof EmptySlotComponent) {
                canMoveTo.add(new ChessboardPoint(x, y - i));
            } else if (!(chessComponents[x][y - i] instanceof EmptySlotComponent) && chessComponents[x][y - i].getChessColor() != this.getChessColor()) {
                canMoveTo.add(new ChessboardPoint(x, y - i));
                break;
            } else {
                break;
            }
        }
        for (int i = 1; i < 10; i++) {
            if (x + i > 7 || y + i > 7) {
                break;
            }
            if (chessComponents[x + i][y + i] instanceof EmptySlotComponent) {
                canMoveTo.add(new ChessboardPoint(x + i, y + i));
            } else if (chessComponents[x + i][y + i].getChessColor() != this.getChessColor()) {
                canMoveTo.add(new ChessboardPoint(x + i, y + i));
                break;
            } else {
                break;
            }

        }
        for (int i = 1; i < 10; i++) {
            if (x - i < 0 || y + i > 7) {
                break;
            }
            if (chessComponents[x - i][y + i] instanceof EmptySlotComponent) {
                canMoveTo.add(new ChessboardPoint(x - i, y + i));
            } else if (chessComponents[x - i][y + i].getChessColor() != this.getChessColor()) {
                canMoveTo.add(new ChessboardPoint(x - i, y + i));
                break;
            } else {
                break;
            }

        }
        for (int i = 1; i < 10; i++) {
            if (x - i < 0 || y - i < 0) {
                break;
            }
            if (chessComponents[x - i][y - i] instanceof EmptySlotComponent) {
                canMoveTo.add(new ChessboardPoint(x - i, y - i));
            } else if (chessComponents[x - i][y - i].getChessColor() != this.getChessColor()) {
                canMoveTo.add(new ChessboardPoint(x - i, y - i));
                break;
            } else {
                break;
            }
        }
        for (int i = 1; i < 10; i++) {
            if (x + i > 7 || y - i < 0) {
                break;
            }
            if (chessComponents[x + i][y - i] instanceof EmptySlotComponent) {
                canMoveTo.add(new ChessboardPoint(x + i, y - i));
            } else if (chessComponents[x + i][y - i].getChessColor() != this.getChessColor()) {
                canMoveTo.add(new ChessboardPoint(x + i, y - i));
                break;
            } else {
                break;
            }
        }
        int cnt = 0;
        for (int i = 0; i < canMoveTo.size(); i++) {
            if (canMoveTo.get(i).getX() == destination.getX() && canMoveTo.get(i).getY() == destination.getY()) {
                cnt++;
                break;
            }
        }
        if (cnt != 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 注意这个方法，每当窗体受到了形状的变化，或者是通知要进行绘图的时候，就会调用这个方法进行画图。
     *
     * @param g 可以类比于画笔
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //     g.drawImage(rookImage, 0, 0, getWidth() - 13, getHeight() - 20, this);
        g.drawImage(queenImage, 0, 0, getWidth(), getHeight(), this);
        g.setColor(Color.BLACK);
        if (isSelected()) { // Highlights the model if selected.
            g.setColor(Color.PINK);
            g.fillRect(0, 0, getWidth(), getHeight());
            g.drawImage(queenImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
