package android.dolsoft;

import android.graphics.Point;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kiend on 7/4/2017.
 */

public class Computer {
    private static final String TAG = "CONSIDER_";
    // computer MIN, human MAX
    private static final int PLUS_INFINITY = 10000000;
    private static final int MINUS_INFINITY = -10000000;

    public static final int PLAYER_MAX = 1; // X
    public static final int PLAYER_MIN = 2; // O
    public static final int EMPTY_CELL = 0; // empty

    private int[][] chessBoard; // 1-X, -1-O, 0-empty
    private boolean[][] markCheck; // true - if this cell was considered, else - false
    private int totalRow;
    private int totalCol;
    private List<Point> listMove;
    private HeuristicEvalutating heuristic;
    private Heuristic hFunction;
    private CalculateHeuristic calH;
    private CalculateHeuristicFake calHF;

    private int dr[] = {0, 1, 1, 1, 0, -1, -1, -1};
    private int dc[] = {-1, -1, 0, 1, 1, 1, 0, -1};

    public Computer(int[][] chessBoard, int totalRow, int totalCol, List<Point> listMove) {
        this.chessBoard = chessBoard;
        this.totalRow = totalRow;
        this.totalCol = totalCol;
        this.listMove = listMove;
        markCheck = new boolean[totalRow][totalCol];
//        heuristic = new HeuristicEvalutating(chessBoard, totalRow, totalCol, dr, dc);
//        hFunction = new Heuristic(chessBoard, totalRow, totalCol);
//        calH = new CalculateHeuristic(chessBoard, totalRow, totalCol);
        calHF = new CalculateHeuristicFake(chessBoard, totalRow, totalCol);
    }

    private void initMarkCheck() {
        for (int i = 0; i < totalRow; i++) {
            for (int j = 0; j < totalCol; j++) {
                markCheck[i][j] = false;
            }
        }
    }

    public Move computerPlay(int currentPlayer) {
        Move bestMove = null;
        int curRow;
        int curCol;
        initMarkCheck();
        boolean isMaxPlayer = (currentPlayer == PLAYER_MAX) ? true : false;
        int bestValue = (isMaxPlayer) ? MINUS_INFINITY : PLUS_INFINITY;
        for (Point mv : listMove) {
            curRow = mv.x;
            curCol = mv.y;
            if (checkInsideOfChessBoard(totalRow, totalCol, curRow - 1, curCol - 1)
                    && checkInsideOfChessBoard(totalRow, totalCol, curRow + 1, curCol + 1))
                for (int i = curRow - 1; i <= curRow + 1; i++) {
                    for (int j = curCol - 1; j <= curCol + 1; j++) {
                        if (chessBoard[i][j] == EMPTY_CELL && !markCheck[i][j]) {
                            Move curMove = new Move(null, null, isMaxPlayer, i, j);
                            markCheck[i][j] = true;
                            int value = /*minimaxAlgo(curMove)*/alphaBetaCut(curMove, MINUS_INFINITY, PLUS_INFINITY, 0);
                            if (isMaxPlayer) {
                                if (value > bestValue) {
                                    bestMove = curMove;
                                    bestValue = value;
                                }
                            } else {
                                if (value < bestValue) {
                                    bestMove = curMove;
                                    bestValue = value;
                                }
                            }
                        }
                    }
                }
        }
        return bestMove;
    }

    private int minimaxAlgo(Move curMove) {
        int currentPlayer = (curMove.isMaxPlayer()) ? PLAYER_MAX : PLAYER_MIN;
        chessBoard[curMove.getRow()][curMove.getCol()] = currentPlayer;
        generateSubListMoveOfCurrentMove(curMove);
        if (curMove.getLeftMostChild() == null) {
            return /*getValueOfNodeLeaf(curMove)*/
                    /*evaluate(chessBoard, curMove.getRow(), curMove.getCol(), totalRow, totalCol)*/
                    /*heuristic.evaluatingHeuristic(currentPlayer, curMove.getRow(), curMove.getCol())*/
                    /*hFunction.evaluatingHeuristic(curMove)*/
                    /*calH.heuristicFunction(curMove)*/
                    calHF.heuristicFunction(curMove);
        }

        int bestValue = (curMove.isMaxPlayer()) ? MINUS_INFINITY : PLUS_INFINITY;
        // suppose fill this cell with type X or O
        Move subMove = curMove.getLeftMostChild();
        while (subMove != null) {
            int value = minimaxAlgo(subMove);
            bestValue = (curMove.isMaxPlayer()) ?
                    ((value >= bestValue) ? value : bestValue) :
                    ((value <= bestValue) ? value : bestValue);
            subMove = subMove.getRightSibling();
        }
        // recover cell to empty
        chessBoard[curMove.getRow()][curMove.getCol()] = EMPTY_CELL;
        Log.d("minimax", "best: " + bestValue);
        return bestValue;
    }

    private int alphaBetaCut(Move curMove, int alpha, int beta, int depth) {
        int currentPlayer = (curMove.isMaxPlayer()) ? PLAYER_MAX : PLAYER_MIN;
//        generateSubListMoveOfCurrentMove(curMove);

        if (depth == 0 || curMove.getLeftMostChild() == null
                /*&& checkInsideOfChessBoard(totalRow, totalCol, curMove.getRow(), curMove.getCol())*/) {
            return /*getValueOfNodeLeaf(curMove)*/
                    /*evaluate(chessBoard, curMove.getRow(), curMove.getCol(), totalRow, totalCol)*/
                    /*heuristic.evaluatingHeuristic(currentPlayer, curMove.getRow(), curMove.getCol())*/
                    /*hFunction.evaluatingHeuristic(curMove)*/
                    /*calH.heuristicFunction(curMove)*/
                    calHF.heuristicFunction(curMove);
        }
        chessBoard[curMove.getRow()][curMove.getCol()] = currentPlayer;

        int bestValue = MINUS_INFINITY;

        Move subMove = curMove.getLeftMostChild();
        while (subMove != null && bestValue < beta && checkInsideOfChessBoard(totalRow, totalCol, subMove.getRow(), subMove.getCol())) {
            if (bestValue > alpha) {
                alpha = bestValue;
            }
            int value = -alphaBetaCut(subMove, -beta, -alpha, depth - 1);
            if (value > bestValue) {
                bestValue = value;
            }
            subMove = subMove.getRightSibling();
        }
        chessBoard[curMove.getRow()][curMove.getCol()] = EMPTY_CELL;
        return bestValue;
    }


    private void generateSubListMoveOfCurrentMove(Move curMove) {
        int curRow = curMove.getRow();
        int curCol = curMove.getCol();
        List<Move> listSubMove = new ArrayList<Move>();
        // scan empty cell around curMove(curCell)
        // check inside of chess board??
        if (checkInsideOfChessBoard(totalRow, totalCol, curRow - 1, curCol - 1)
                && checkInsideOfChessBoard(totalRow, totalCol, curRow + 1, curCol + 1))
            for (int i = curRow - 1; i < curRow + 1; i++) {
                for (int j = curCol - 1; j < curCol + 1; j++) {
                    if (chessBoard[i][j] == EMPTY_CELL && !markCheck[i][j]) {
                        markCheck[i][j] = true;
                        Move mv = new Move(
                                null, null, !curMove.isMaxPlayer(), i, j);
                        listSubMove.add(mv);
                    }
                }
            }
        if (listSubMove.size() > 0)
            for (Move mv : listSubMove) {
                if (curMove.getLeftMostChild() == null) curMove.setLeftMostChild(mv);
                else {
                    Move subOfMv = curMove.getLeftMostChild();
                    while (subOfMv != null) {
                        if (subOfMv.getRightSibling() == null) {
                            subOfMv.setRightSibling(mv);
                            break;
                        }
                        subOfMv = subOfMv.getRightSibling();
                    }
                }
            }
    }

    private boolean checkInsideOfChessBoard(int totalRow, int totalCol, int x, int y) {
        if (x < totalRow && y < totalCol
                && x >= 0 && y >= 0) return true;
        return false;
    }

    private int getValueOfNodeLeaf(Move curMove) {
        int curRow = curMove.getRow();
        int curCol = curMove.getCol();

        // easy level
        int bestPoint = (curMove.isMaxPlayer()) ? MINUS_INFINITY : PLUS_INFINITY;
        for (int h = 0; h < 8; h++) {
            int len = 0;
            int totalPoint = 0;
            int x = curRow;
            int y = curCol;
            while (chessBoard[x][y] == chessBoard[curRow][curCol]) {
                x += dr[h];
                y += dc[h];
                len++;
            }
            switch (len) {
                case 0:
                    totalPoint += 0;
                    break;
                case 1:
                    totalPoint += 1;
                    break;
                case 2:
                    totalPoint += 10;
                    break;
                case 3:
                    totalPoint += 100;
                    break;
                case 4:
                    totalPoint += 1000;
                    break;
                default:
                    totalPoint += 10000;
                    break;
            }
            totalPoint = (curMove.isMaxPlayer()) ? totalPoint : -totalPoint;
            bestPoint = (curMove.isMaxPlayer()) ?
                    (totalPoint >= bestPoint ? totalPoint : bestPoint) :
                    (totalPoint <= bestPoint ? totalPoint : bestPoint);
        }
        if (chessBoard[curMove.getRow()][curMove.getCol()] != EMPTY_CELL)
            chessBoard[curMove.getRow()][curMove.getCol()] = EMPTY_CELL;
        return bestPoint;
    }

    private int evaluate(int[][] chessBoard, int i, int j, int row, int column) {
        int num, totalPoint = 0;
        int[] dx = {1, 1, 1, 0};
        int[] dy = {-1, 0, 1, 1};
        // Gia su quan co duoc dat o day
        chessBoard[i][j] = PLAYER_MIN;

        for (int h = 0; h < 4; h++) {
            Point p, q;
            p = runline(chessBoard, row, column, i, j, dx[h], dy[h]);
            q = runline(chessBoard, row, column, i, j, -dx[h], -dy[h]);

            int len; // so quan co thang hang
            if (p.x != q.x)
                len = (int) Math.abs(p.x - q.x);
            else
                len = (int) Math.abs(p.y - q.y);

            if (len < 6) {
                // 1 trong 2 diem nam ngoai hay da bi danh dau
                if (!(this.IsInside((int) p.x, (int) p.y, row, column) && this.getState(p) == 0 &&
                        this.IsInside((int) q.x, (int) q.y, row, column) && this.getState(q) == 0))
                    len--;
                // ca hai diem nam ngoai hay ca hai diem da danh dau
                if (!((this.IsInside((int) p.x, (int) p.y, row, column) && this.getState(p) == 0) ||
                        (this.IsInside((int) q.x, (int) q.y, row, column) && this.getState(q) == 0)))
                    len = 2;
            }

            switch (len) {
                case 1:
                    totalPoint -= 1;
                    break;
                case 2:
                    break;
                case 3:
                    totalPoint -= 2;
                    break;
                case 4:
                    totalPoint -= 8;
                    break;
                case 5:
                    totalPoint -= 5000;
                    break;
                default:
                    totalPoint -= 10000;
                    break;
            }

            if (totalPoint <= -10000) {
                chessBoard[i][j] = 0; // khoi phuc
                return totalPoint;
            }
        }

        // Kiem tra hai duong
        num = 0;
        for (int h = 0; h < 4; h++) {
            Point p, p1;
            p = runline(chessBoard, row, column, i, j, dx[h], dy[h]);
            p1 = p;
            // Kiem tra duong thu 2
            if (this.IsInside((int) p1.x, (int) p1.y, row, column) && this.getState(p1) == 0)
                p1 = runline(chessBoard, row, column, (int) p1.x, (int) p1.y, dx[h], dy[h]);

            Point q, q1;
            q = runline(chessBoard, row, column, i, j, (int) -dx[h], (int) -dy[h]);

            // xet diem cuoi voi diem con x1,y1 cua diem dau x,y
            if (this.IsInside((int) p1.x, (int) p1.y, row, column) && this.getState(p1) == 0 &&
                    this.IsInside((int) q.x, (int) q.y, row, column) && this.getState(q) == 0) {
                if (Math.abs(p1.x - q.x) >= 6 || Math.abs(p1.y - q.y) >= 6) {
                    num++;
                    if (num == 2) {
                        totalPoint += 150;
                        break; // for h
                    }
                }
            }

            q1 = q;
            if (this.IsInside((int) q1.x, (int) q1.y, row, column) && this.getState(q1) == 0)
                q1 = runline(chessBoard, row, column, (int) q1.x, (int) q1.y, dx[h], dy[h]);

            // xet diem dau voi diem con u1,v1 cua diem cuoi u,v
            if (this.IsInside((int) q1.x, (int) q1.y, row, column) && this.getState(q1) == 0 &&
                    this.IsInside((int) p.x, (int) p.y, row, column) && this.getState(p) == 0) {
                if (Math.abs(q1.x - p.x) >= 6 || Math.abs(q1.y - p.y) >= 6) {
                    num++;
                    if (num == 2) {
                        totalPoint += 150;
                        break;
                    }
                }
            }
        }

        //
        // uoc luong duong di neu User dat o vi tri i,j.
        //

        chessBoard[i][j] = PLAYER_MAX;
        for (int h = 0; h < 4; h++) {
            Point p, q;
            p = runline(chessBoard, row, column, i, j, dx[h], dy[h]);
            q = runline(chessBoard, row, column, i, j, (int) -dx[h], (int) -dy[h]);

            int len; // so quan co thang hang
            if (p.x != q.x)
                len = (int) Math.abs(p.x - q.x);
            else
                len = (int) Math.abs(p.y - q.y);
            if (len < 6) {
                // 1 trong 2 diem nam ngoai hay da bi danh dau
                if (!(this.IsInside((int) p.x, (int) p.y, row, column) && this.getState(p) == 0 &&
                        this.IsInside((int) q.x, (int) q.y, row, column) && this.getState(q) == 0))
                    len--;
                // diem p,q nam ngoai ngay user da danh dau
                if (!this.IsInside((int) p.x, (int) p.y, row, column) || this.getState(p) == 2 ||
                        !this.IsInside((int) q.x, (int) q.y, row, column) || this.getState(q) == 2)
                    continue; // next h
            }
            switch (len) {
                case 1:
                    totalPoint -= 1;
                    break;
                case 2:
                    break;
                case 3:
                    totalPoint -= 2;
                    break;
                case 4:
                    totalPoint -= 6;
                    break;
                case 5:
                    totalPoint -= 800;
                    break;
                default:
                    totalPoint -= 7000;
                    break;
            }
        }
        // Kiem tra hai duong
        num = 0;
        for (int h = 0; h < 4; h++) {
            Point p, p1;
            p = runline(chessBoard, row, column, i, j, dx[h], dy[h]);
            p1 = p;
            // Kiem tra duong thu 2
            if (this.IsInside((int) p1.x, (int) p1.y, row, column) && this.getState(p1) == 0)
                p1 = runline(chessBoard, row, column, (int) p1.x, (int) p1.y, dx[h], dy[h]);

            Point q, q1;
            q = runline(chessBoard, row, column, i, j, (int) -dx[h], (int) -dy[h]);

            // xet diem cuoi voi diem con x1,y1 cua diem dau x,y
            if (this.IsInside((int) p1.x, (int) p1.y, row, column) && this.getState(p1) == 0 &&
                    this.IsInside((int) q.x, (int) q.y, row, column) && this.getState(q) == 0) {
                if (Math.abs(p1.x - q.x) >= 6 || Math.abs(p1.y - q.y) >= 6) {
                    num++;
                    if (num == 2) {
                        totalPoint -= 30;
                        break;
                    }
                }
            }

            q1 = q;
            if (this.IsInside((int) q1.x, (int) q1.y, row, column) && this.getState(q1) == 0)
                q1 = runline(chessBoard, row, column, (int) q1.x, (int) q1.y, dx[h], dy[h]);

            // xet diem dau voi diem con u1,v1 cua diem cuoi u,v
            if (this.IsInside((int) q1.x, (int) q1.y, row, column) && this.getState(q1) == 0 &&
                    this.IsInside((int) p.x, (int) p.y, row, column) && this.getState(p) == 0) {
                if (Math.abs(q1.x - p.x) >= 6 || Math.abs(q1.y - p.y) >= 6) {
                    num++;
                    if (num == 2) {
                        totalPoint -= 30;
                        break;
                    }
                }
            }

        }
        chessBoard[i][j] = 0; // khoi phuc
        return totalPoint;
    }

    // check line
    private Point runline(int[][] chessBoard, int row, int column, int i, int j, int dx, int dy) {
        int x = i, y = j;
        do {
            x += dx;
            y += dy;
        } while (this.IsInside(x, y, row, column) && chessBoard[x][y] == chessBoard[i][j]);
        return (new Point(x, y));
    }

    private boolean IsInside(int i, int j, int row, int column) {
        if (i > 0 && i < row && j > 0 && j < column)
            return true;
        return false;
    }

    public int getState(Point p) {
        return chessBoard[p.x][p.y];
    }
}
