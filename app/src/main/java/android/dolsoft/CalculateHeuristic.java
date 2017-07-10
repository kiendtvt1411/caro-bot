package android.dolsoft;

import android.graphics.Point;
import android.util.Log;

/**
 * Created by kiend on 7/8/2017.
 */

public class CalculateHeuristic {

    private MatrixHeuristic matrixH;
    private int[][] chessBoard;
    private int totalRow;
    private int totalCol;
    private int[] dx;
    private int[] dy;
    private static final int PLAYER_MAX = 1;
    private static final int PLAYER_MIN = 2;

    private static final int POINT_ABSOLUTE_WIN = 531441;
    private static final int POINT_MAKE_SURE_WIN = 59049;
    private static final int POINT_FACILITATE_TO_ATTACK = 6561;
    private static final int POINT_PREVENT_OPPONENT = 729;
    private static final int POINT_BIT_OF_FACILITATE = 81;
    private static final int POINT_OF_NORMAL_MOVE = 9;
    private static final int POINT_OTHER_WISE = 1;

    public CalculateHeuristic(int[][] chessBoard, int totalRow, int totalCol) {
        this.chessBoard = chessBoard;
        this.totalRow = totalRow;
        this.totalCol = totalCol;
        this.dx = new int[]{1, 1, 0, -1};
        this.dy = new int[]{0, 1, 1, 1};
        matrixH = new MatrixHeuristic();
    }

    public int heuristicFunction(Move curMove) {
        int curRow = curMove.getRow();
        int curCol = curMove.getCol();
        int currentPlayer = (curMove.isMaxPlayer()) ? PLAYER_MAX : PLAYER_MIN;
        int totalPoint = 0;
        int priority = 0;

        chessBoard[curRow][curCol] = currentPlayer;

        for (int v = 0; v < 4; v++) {
            Point bound = getPointOfChessBound(curRow, curCol, dx[v], dy[v]);
            priority = checkValueOfLine(currentPlayer, bound, dx[v], dy[v]);
            Log.d("test_com_", "priority: " + priority);

            if (currentPlayer == PLAYER_MAX) {
                switch (priority) {
                    case 1:
                        totalPoint += POINT_ABSOLUTE_WIN;
                        break;
                    case 2:
                        totalPoint += POINT_MAKE_SURE_WIN;
                        break;
                    case 3:
                        totalPoint += POINT_FACILITATE_TO_ATTACK;
                        break;
                    case 4:
                        totalPoint += POINT_PREVENT_OPPONENT;
                        break;
                    case 5:
                        totalPoint += POINT_BIT_OF_FACILITATE;
                        break;
                    case 6:
                        totalPoint += POINT_OF_NORMAL_MOVE;
                        break;
                    case 7:
                        totalPoint += POINT_OTHER_WISE;
                        break;
                    default:
                        break;
                }
            } else {
                switch (priority) {
                    case 1:
                        totalPoint -= POINT_ABSOLUTE_WIN;
                        break;
                    case 2:
                        totalPoint -= POINT_MAKE_SURE_WIN;
                        break;
                    case 3:
                        totalPoint -= POINT_FACILITATE_TO_ATTACK;
                        break;
                    case 4:
                        totalPoint -= POINT_PREVENT_OPPONENT;
                        break;
                    case 5:
                        totalPoint -= POINT_BIT_OF_FACILITATE;
                        break;
                    case 6:
                        totalPoint -= POINT_OF_NORMAL_MOVE;
                        break;
                    case 7:
                        totalPoint -= POINT_OTHER_WISE;
                        break;
                    default:
                        break;
                }
            }
        }
        chessBoard[curRow][curCol] = 0;
        return totalPoint;
    }

    // return priority
    private int checkValueOfLine(int player, Point bound, int dx, int dy) {
        int x = bound.x;
        int y = bound.y;
        StringBuilder builder = new StringBuilder();
        while (checkInsideBoard(x+dx, y+dy)) {
            x += dx;
            y += dy;
            builder.append(chessBoard[x][y]);
        }
        String listState = builder.toString();

        if (player == PLAYER_MAX) {
            for(int i = 0;i<10;i++) {
                if (i < 1) {
                    String absoluteWinMAX = toString(matrixH.matrixAbsoluteWinMAX[i]);
                    if(listState.contains(absoluteWinMAX)) {

                        Log.d("priority_", "priority_1 & list state: " + listState + "/" + absoluteWinMAX);
                        return 1;
                    }
                }
                if (i < 5) {
                    String winStateMAX = toString(matrixH.matrixWinStateMAX[i]);

                    if(listState.contains(winStateMAX)) {
                        Log.d("priority_", "priority_2 & list state: " + listState + "/" + winStateMAX);
                        return 2;
                    }
                    String facilitateAttack = toString(matrixH.matrixFacilitateAttack[i]);

                    if(listState.contains(facilitateAttack)) {
                        Log.d("priority_", "priority_3 & list state: " + listState + "/" + facilitateAttack);
                        return 3;
                    }
                    String preventAttack = toString(matrixH.matrixPreventAttack[i]);

                    if(listState.contains(preventAttack)) {
                        Log.d("priority_", "priority_4 & list state: " + listState + "/" + preventAttack);
                        return 4;
                    }
                }
                if (i < 6) {
                    String normalMAX = toString(matrixH.matrixNormalMAX[i]);

                    if(listState.contains(normalMAX)) {
                        Log.d("priority_", "priority_6 & list state: " + listState + "/" + normalMAX);
                        return 6;
                    }
                }
                if (i < 10) {
                    String bitFacilitate = toString(matrixH.matrixBitFacilitateMAX[i]);

                    if(listState.contains(bitFacilitate)) {
                        Log.d("priority_", "priority_5 & list state: " + listState + "/" + bitFacilitate);
                        return 5;
                    }
                }
            }
        } else {
            for(int i = 0;i<10;i++) {
                if (i < 1) {
                    String absoluteWinMIN = toString(matrixH.matrixAbsoluteWinMIN[i]);

                    if(listState.contains(absoluteWinMIN)) {
                        Log.d("priority_", "priority_1 & list state: " + listState + "/" + absoluteWinMIN);
                        return 1;
                    }
                }
                if (i < 5) {
                    String winStateMIN = toString(matrixH.matrixWinStateMIN[i]);

                    if(listState.contains(winStateMIN)) {
                        Log.d("priority_", "priority_2 & list state: " + listState + "/" + winStateMIN);
                        return 2;
                    }
                    String preventAttack = toString(matrixH.matrixPreventAttack[i]);

                    if(listState.contains(preventAttack)) {
                        Log.d("priority_", "priority_3 & list state: " + listState + "/" + preventAttack);
                        return 3;
                    }
                    String facilitateAttack = toString(matrixH.matrixFacilitateAttack[i]);

                    if(listState.contains(facilitateAttack)) {
                        Log.d("priority_", "priority_4 & list state: " + listState + "/" + facilitateAttack);
                        return 4;
                    }
                }
                if (i < 6) {
                    String normalMIN = toString(matrixH.matrixNormalMIN[i]);

                    if(listState.contains(normalMIN)) {
                        Log.d("priority_", "priority_6 & list state: " + listState + "/" + normalMIN);
                        return 6;
                    }
                }
                if (i < 10) {
                    String bitFacilitate = toString(matrixH.matrixBitFacilitateMIN[i]);

                    if(listState.contains(bitFacilitate)) {
                        Log.d("priority_", "priority_5 & list state: " + listState + "/" + bitFacilitate);
                        return 5;
                    }
                }
            }
        }
        return 7;
    }

    private String toString(int[] matrix) {
        StringBuilder builder = new StringBuilder();
        int len = matrix.length;
        for(int i = 0;i<len;i++) {
            builder.append(matrix[i]);
        }
        return builder.toString();
    }

    // bound of chess start in point(1,1) not point(0,0)
    private Point getPointOfChessBound(int curRow, int curCol, int dx, int dy) {
        int x = curRow;
        int y = curCol;
        while (checkInsideBoard(x-dx, y-dy)){
            x-=dx;
            y-=dy;
        };
        return new Point(x, y);
    }

    private boolean checkInsideBoard(int x, int y) {
        if (x >= 0 && x < totalRow && y >= 0 && y < totalCol) return true;
        return false;
    }
}
