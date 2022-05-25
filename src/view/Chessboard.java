package view;


import model.*;
import controller.ClickController;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 这个类表示面板上的棋盘组件对象
 */
public class Chessboard extends JComponent {
    /**
     * CHESSBOARD_SIZE： 棋盘是8 * 8的
     * <br>
     * BACKGROUND_COLORS: 棋盘的两种背景颜色
     * <br>
     * chessListener：棋盘监听棋子的行动
     * <br>
     * chessboard: 表示8 * 8的棋盘
     * <br>
     * currentColor: 当前行棋方
     */
    private static final int CHESSBOARD_SIZE = 8;

    public final ChessComponent[][] chessComponents = new ChessComponent[CHESSBOARD_SIZE][CHESSBOARD_SIZE];
    private ChessColor currentColor = ChessColor.WHITE;
    //all chessComponents in this chessboard are shared only one model controller
    private final ClickController clickController = new ClickController(this);
    private final int CHESS_SIZE;
    public List<ChessComponent[][]> regret=new ArrayList<>();


    public Chessboard(int width, int height) {
        setLayout(null); // Use absolute layout.
        setSize(width, height);
        CHESS_SIZE = width / 8;
        System.out.printf("chessboard size = %d, chess size = %d\n", width, CHESS_SIZE);

        initiateEmptyChessboard();

        // FIXME: Initialize chessboard for testing only.
        initRookOnBoard(0, 0, ChessColor.BLACK);
        initRookOnBoard(0, CHESSBOARD_SIZE - 1, ChessColor.BLACK);
        initRookOnBoard(CHESSBOARD_SIZE - 1, 0, ChessColor.WHITE);
        initRookOnBoard(CHESSBOARD_SIZE - 1, CHESSBOARD_SIZE - 1, ChessColor.WHITE);
        initKnightOnBoard(0, CHESSBOARD_SIZE - 2, ChessColor.BLACK);
        initKnightOnBoard(0, 1, ChessColor.BLACK);
        initKnightOnBoard(CHESSBOARD_SIZE - 1, 1, ChessColor.WHITE);
        initKnightOnBoard(CHESSBOARD_SIZE - 1, CHESSBOARD_SIZE - 2, ChessColor.WHITE);
        initBishopOnBoard(0, 2, ChessColor.BLACK);
        initBishopOnBoard(0, CHESSBOARD_SIZE - 3, ChessColor.BLACK);
        initBishopOnBoard(7, 2, ChessColor.WHITE);
        initBishopOnBoard(7, 5, ChessColor.WHITE);
        initQueenOnboard(7, 3, ChessColor.WHITE);
        initQueenOnboard(0, 3, ChessColor.BLACK);
        initKingOnboard(7, 4, ChessColor.WHITE);
        initKingOnboard(0, 4, ChessColor.BLACK);
        for (int i = 0; i < 8; i++) {
            initPawnOnboard(1, i, ChessColor.BLACK);
        }
        for (int i = 0; i < 8; i++) {
            initPawnOnboard(6, i, ChessColor.WHITE);
        }

    }

    public ChessComponent[][] getChessComponents() {
        return chessComponents;
    }

    public ChessColor getCurrentColor() {
        return currentColor;
    }

    public void putChessOnBoard(ChessComponent chessComponent) {
        int row = chessComponent.getChessboardPoint().getX(), col = chessComponent.getChessboardPoint().getY();

        if (chessComponents[row][col] != null) {
            remove(chessComponents[row][col]);
        }
        add(chessComponents[row][col] = chessComponent);
    }

    public void swapChessComponents(ChessComponent chess1, ChessComponent chess2) {
        // Note that chess1 has higher priority, 'destroys' chess2 if exists.
        if (!(chess2 instanceof EmptySlotComponent)) {
            remove(chess2);
            add(chess2 = new EmptySlotComponent(chess2.getChessboardPoint(), chess2.getLocation(), clickController, CHESS_SIZE));
        }
        chess1.swapLocation(chess2);
        int row1 = chess1.getChessboardPoint().getX(), col1 = chess1.getChessboardPoint().getY();
        chessComponents[row1][col1] = chess1;
        int row2 = chess2.getChessboardPoint().getX(), col2 = chess2.getChessboardPoint().getY();
        chessComponents[row2][col2] = chess2;

        chess1.repaint();
        chess2.repaint();
    }

    public void initiateEmptyChessboard() {
        for (int i = 0; i < chessComponents.length; i++) {
            for (int j = 0; j < chessComponents[i].length; j++) {
                putChessOnBoard(new EmptySlotComponent(new ChessboardPoint(i, j), calculatePoint(i, j), clickController, CHESS_SIZE));
            }
        }
    }

    public void initiateEmptyChessboard(int row, int col) {
        ChessComponent chessComponent = new EmptySlotComponent(new ChessboardPoint(row, col), calculatePoint(row, col), clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    public void swapColor() {
        currentColor = currentColor == ChessColor.BLACK ? ChessColor.WHITE : ChessColor.BLACK;
        ChessGameFrame.changeLabel(currentColor);
    }

    public void initRookOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new RookChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    public void initKnightOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new KnightChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    public void initBishopOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new BishopChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    public void initQueenOnboard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new QueenChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    public void initKingOnboard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new KingChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    public void initPawnOnboard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new PawnChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }


    private Point calculatePoint(int row, int col) {
        return new Point(col * CHESS_SIZE, row * CHESS_SIZE);
    }

    public void loadGame(List<String> chessData) {
        int cnt1 = 0;
        int cnt2 = 0;
        int cnt3 = 0;
        for (int i = 0; i < chessData.size() - 1; i++) {
            if (chessData.get(i).length() != 8) {
                cnt1++;
                break;
            }
        }
        if (cnt1 != 0) {
            JOptionPane.showMessageDialog(null, "The chessboard is not 8 * 8", "wrong", JOptionPane.ERROR_MESSAGE);
        } else {
            for (int i = 0; i < chessData.size()-1; i++) {
                for (int j = 0; j < chessData.get(i).length(); j++) {
                    if (chessData.get(i).charAt(j) != 'R' && chessData.get(i).charAt(j) != 'r' && chessData.get(i).charAt(j) != 'B' && chessData.get(i).charAt(j) != 'b' && chessData.get(i).charAt(j) != '_'
                            && chessData.get(i).charAt(j) != 'K' && chessData.get(i).charAt(j) != 'k' && chessData.get(i).charAt(j) != 'N' && chessData.get(i).charAt(j) != 'n' && chessData.get(i).charAt(j) != 'p'
                            && chessData.get(i).charAt(j) != 'P' && chessData.get(i).charAt(j) != 'Q' && chessData.get(i).charAt(j) != 'q') {
                        cnt2++;
                    }
                }
            }
            if (cnt2 != 0) {
                JOptionPane.showMessageDialog(null, "Chess pieces are not one of the six or Chess pieces are not black and white;", "wrong", JOptionPane.ERROR_MESSAGE);
            } else {
                if (chessData.size() != 9 ) {
                    cnt3++;
                }
                if (cnt3 != 0) {
                    JOptionPane.showMessageDialog(null, "The imported data only has a chessboard, and there is no prompt for the next player;", "wrong", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        if (cnt1 == 0 && cnt2 == 0 && cnt3 == 0) {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    switch (chessData.get(i).charAt(j)) {
                        case 'R':
                            initRookOnBoard(i, j, ChessColor.BLACK);
                            chessComponents[i][j].repaint();
                            break;
                        case 'r':
                            initRookOnBoard(i, j, ChessColor.WHITE);
                            chessComponents[i][j].repaint();
                            break;
                        case 'Q':
                            initQueenOnboard(i, j, ChessColor.BLACK);
                            chessComponents[i][j].repaint();
                            break;
                        case 'q':
                            initQueenOnboard(i, j, ChessColor.WHITE);
                            chessComponents[i][j].repaint();
                            break;
                        case 'B':
                            initBishopOnBoard(i, j, ChessColor.BLACK);
                            chessComponents[i][j].repaint();
                            break;
                        case 'b':
                            initBishopOnBoard(i, j, ChessColor.WHITE);
                            chessComponents[i][j].repaint();
                            break;
                        case 'N':
                            initKnightOnBoard(i, j, ChessColor.BLACK);
                            chessComponents[i][j].repaint();
                            break;
                        case 'n':
                            initKnightOnBoard(i, j, ChessColor.WHITE);
                            chessComponents[i][j].repaint();
                            break;
                        case 'K':
                            initKingOnboard(i, j, ChessColor.BLACK);
                            chessComponents[i][j].repaint();
                            break;
                        case 'k':
                            initKingOnboard(i, j, ChessColor.WHITE);
                            chessComponents[i][j].repaint();
                            break;
                        case 'P':
                            initPawnOnboard(i, j, ChessColor.BLACK);
                            chessComponents[i][j].repaint();
                            break;
                        case 'p':
                            initPawnOnboard(i, j, ChessColor.WHITE);
                            chessComponents[i][j].repaint();
                            break;
                        case '_':
                            initiateEmptyChessboard(i, j);
                            chessComponents[i][j].repaint();
                    }
                }
            }
            if (chessData.get(8).charAt(0) == 'w') {
                currentColor = ChessColor.WHITE;
                ChessGameFrame.changeLabel(ChessColor.WHITE);
            } else {
                currentColor = ChessColor.BLACK;
                ChessGameFrame.changeLabel(ChessColor.BLACK);
            }
        }
    }

    public List<String> saveGame() {
        List<String> chessData = new ArrayList<>();
        char[][] chessBoard = new char[9][9];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (chessComponents[i][j] instanceof BishopChessComponent) {
                    if (chessComponents[i][j].getChessColor() == ChessColor.WHITE) {
                        chessBoard[i][j] = 'b';
                    } else {
                        chessBoard[i][j] = 'B';
                    }
                } else if (chessComponents[i][j] instanceof EmptySlotComponent) {
                    chessBoard[i][j] = '_';
                } else if (chessComponents[i][j] instanceof KingChessComponent) {
                    if (chessComponents[i][j].getChessColor() == ChessColor.WHITE) {
                        chessBoard[i][j] = 'k';
                    } else {
                        chessBoard[i][j] = 'K';
                    }
                } else if (chessComponents[i][j] instanceof KnightChessComponent) {
                    if (chessComponents[i][j].getChessColor() == ChessColor.WHITE) {
                        chessBoard[i][j] = 'n';
                    } else {
                        chessBoard[i][j] = 'N';
                    }
                } else if (chessComponents[i][j] instanceof PawnChessComponent) {
                    if (chessComponents[i][j].getChessColor() == ChessColor.WHITE) {
                        chessBoard[i][j] = 'p';
                    } else {
                        chessBoard[i][j] = 'P';
                    }
                } else if (chessComponents[i][j] instanceof QueenChessComponent) {
                    if (chessComponents[i][j].getChessColor() == ChessColor.WHITE) {
                        chessBoard[i][j] = 'q';
                    } else {
                        chessBoard[i][j] = 'Q';
                    }
                } else if (chessComponents[i][j] instanceof RookChessComponent) {
                    if (chessComponents[i][j].getChessColor() == ChessColor.WHITE) {
                        chessBoard[i][j] = 'r';
                    } else {
                        chessBoard[i][j] = 'R';
                    }
                }
            }
        }
        if (getCurrentColor() == ChessColor.WHITE) {
            chessBoard[8][0] = 'w';
        } else {
            chessBoard[8][0] = 'b';
        }
        StringBuilder l1 = new StringBuilder();
        StringBuilder l2 = new StringBuilder();
        StringBuilder l3 = new StringBuilder();
        StringBuilder l4 = new StringBuilder();
        StringBuilder l5 = new StringBuilder();
        StringBuilder l6 = new StringBuilder();
        StringBuilder l7 = new StringBuilder();
        StringBuilder l8 = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            l1.append(chessBoard[0][i]);
        }
        for (int i = 0; i < 8; i++) {
            l2.append(chessBoard[1][i]);
        }
        for (int i = 0; i < 8; i++) {
            l3.append(chessBoard[2][i]);
        }
        for (int i = 0; i < 8; i++) {
            l4.append(chessBoard[3][i]);
        }
        for (int i = 0; i < 8; i++) {
            l5.append(chessBoard[4][i]);
        }
        for (int i = 0; i < 8; i++) {
            l6.append(chessBoard[5][i]);
        }
        for (int i = 0; i < 8; i++) {
            l7.append(chessBoard[6][i]);
        }
        for (int i = 0; i < 8; i++) {
            l8.append(chessBoard[7][i]);
        }
        chessData.add(l1.toString());
        chessData.add(l2.toString());
        chessData.add(l3.toString());
        chessData.add(l4.toString());
        chessData.add(l5.toString());
        chessData.add(l6.toString());
        chessData.add(l7.toString());
        chessData.add(l8.toString());
        chessData.add(String.valueOf(chessBoard[8][0]));
        return chessData;
    }
}
