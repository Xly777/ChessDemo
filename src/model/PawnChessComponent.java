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
public class PawnChessComponent extends ChessComponent {
    /**
     * 黑车和白车的图片，static使得其可以被所有车对象共享
     * <br>
     * FIXME: 需要特别注意此处加载的图片是没有背景底色的！！！
     */
    private static Image PAWN_WHITE;
    private static Image PAWN_BLACK;

    /**
     * 车棋子对象自身的图片，是上面两种中的一种
     */
    private Image pawnImage;

    /**
     * 读取加载车棋子的图片
     *
     * @throws IOException
     */
    public void loadResource() throws IOException {
        if (PAWN_WHITE == null) {
            PAWN_WHITE = ImageIO.read(new File("./images/pawn-white.png"));
        }

        if (PAWN_BLACK == null) {
            PAWN_BLACK = ImageIO.read(new File("./images/pawn-black.png"));
        }
    }


    /**
     * 在构造棋子对象的时候，调用此方法以根据颜色确定rookImage的图片是哪一种
     *
     * @param color 棋子颜色
     */

    private void initiatePawnImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                pawnImage = PAWN_WHITE;
            } else if (color == ChessColor.BLACK) {
                pawnImage = PAWN_BLACK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public PawnChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size) {
        super(chessboardPoint, location, color, listener, size);
        initiatePawnImage(color);
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
        if (this.getChessColor() == ChessColor.WHITE) {
            if (x == 6) {
                if (chessComponents[x - 1][y].getChessColor() == ChessColor.NONE) {
                    canMoveTo.add(new ChessboardPoint(x - 1, y));
                }
                if (chessComponents[x - 2][y].getChessColor() == ChessColor.NONE&&chessComponents[x-1][y].getChessColor()==ChessColor.NONE) {
                    canMoveTo.add(new ChessboardPoint(x - 2, y));
                }
                if (y - 1 >= 0 && chessComponents[x - 1][y - 1].getChessColor() == ChessColor.BLACK) {
                    canMoveTo.add(new ChessboardPoint(x - 1, y - 1));
                }
                if (y + 1 < 8 && chessComponents[x - 1][y + 1].getChessColor() == ChessColor.BLACK) {
                    canMoveTo.add(new ChessboardPoint(x - 1, y + 1));
                }
            } else {
                if (x - 1 >= 0 && y - 1 >= 0 && chessComponents[x - 1][y - 1].getChessColor() == ChessColor.BLACK) {
                    canMoveTo.add(new ChessboardPoint(x - 1, y - 1));
                }
                if (x - 1 >= 0 && y + 1 < 8 && chessComponents[x - 1][y + 1].getChessColor() == ChessColor.BLACK) {
                    canMoveTo.add(new ChessboardPoint(x - 1, y + 1));
                }
                if (x - 1 >= 0 && chessComponents[x - 1][y].getChessColor() == ChessColor.NONE) {
                    canMoveTo.add(new ChessboardPoint(x - 1, y));
                }
            }
        } else {
            if (x == 1) {
                if (chessComponents[x + 1][y].getChessColor() == ChessColor.NONE) {
                    canMoveTo.add(new ChessboardPoint(x + 1, y));
                }
                if (chessComponents[x + 2][y].getChessColor() == ChessColor.NONE&&chessComponents[x+1][y].getChessColor()==ChessColor.NONE) {
                    canMoveTo.add(new ChessboardPoint(x + 2, y));
                }
                if (y + 1 < 8 && chessComponents[x + 1][y + 1].getChessColor() == ChessColor.WHITE) {
                    canMoveTo.add(new ChessboardPoint(x + 1, y + 1));
                }
                if (y - 1 >= 0 && chessComponents[x + 1][y - 1].getChessColor() == ChessColor.WHITE) {
                    canMoveTo.add(new ChessboardPoint(x + 1, y - 1));
                }
            } else {
                if (x + 1 < 8 && y + 1 < 8 && chessComponents[x + 1][y + 1].getChessColor() == ChessColor.WHITE) {
                    canMoveTo.add(new ChessboardPoint(x + 1, y + 1));
                }
                if (x + 1 < 8 && y - 1 >= 0 && chessComponents[x + 1][y - 1].getChessColor() == ChessColor.WHITE) {
                    canMoveTo.add(new ChessboardPoint(x + 1, y - 1));
                }
                if (x + 1 < 8 && chessComponents[x + 1][y].getChessColor() == ChessColor.NONE) {
                    canMoveTo.add(new ChessboardPoint(x + 1, y));
                }
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
        g.drawImage(pawnImage, 0, 0, getWidth(), getHeight(), this);
        g.setColor(Color.BLACK);
        if (isSelected()) { // Highlights the model if selected.
            g.setColor(Color.PINK);
            g.fillRect(0, 0, getWidth(), getHeight());
            g.drawImage(pawnImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
