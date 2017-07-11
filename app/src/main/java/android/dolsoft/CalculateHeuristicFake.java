package android.dolsoft;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kiend on 7/9/2017.
 */

public class CalculateHeuristicFake {

    //    private MatrixHeuristic matrixH;
    private MatrixHeuristicFake matrixH;
    private int[][] chessBoard;
    private int totalRow;
    private int totalCol;
    private int[] dx;
    private int[] dy;
    private static final int PLAYER_MAX = 1;
    private static final int PLAYER_MIN = 2;

    private static final int POINT_ABSOLUTE_WIN = 160000;
    private static final int POINT_MAKE_SURE_WIN = 8000;
    private static final int POINT_FACILITATE_TO_ATTACK = 400;
    //    private static final int POINT_PREVENT_OPPONENT = 729;
    private static final int POINT_BIT_OF_FACILITATE = 20;
    private static final int POINT_OF_NORMAL_MOVE = 1;
//    private static final int POINT_OTHER_WISE = 1;

    private String matrixAbsoluteWinMAX;
    private String matrixAbsoluteWinMIN;
    private List<String> matrixStateWinMAX;
    private List<String> matrixStateWinMIN;
    private List<String> matrixFaciliateAttack;
    private List<String> matrixPrevent;
    private List<String> matrixBitFacilitateMAX;
    private List<String> matrixBitFacilitateMIN;
    private List<String> matrixNormalMAX;
    private List<String> matrixNormalMIN;
    private List<String> listStateCheckLine;

    public CalculateHeuristicFake(int[][] chessBoard, int totalRow, int totalCol) {
        this.chessBoard = chessBoard;
        this.totalRow = totalRow;
        this.totalCol = totalCol;
        this.dx = new int[]{1, 1, 0, -1};
        this.dy = new int[]{0, 1, 1, 1};
//        matrixH = new MatrixHeuristic();
        matrixH = new MatrixHeuristicFake();
        initMatrixH();
        initListStateCheckLine();
    }

    private void initListStateCheckLine() {
        if (listStateCheckLine != null) listStateCheckLine.clear();
        listStateCheckLine = new ArrayList<String>();
        for (int i = 0; i < totalRow; i++) {
            // duong ngang
            String stateOne = getStateOfLine(0, i, dx[0], dy[0]);
            listStateCheckLine.add(stateOne);
            // duong cheo tu trai xuong
            String stateTwo = getStateOfLine(0, i, dx[1], dy[1]);
            listStateCheckLine.add(stateTwo);
            // duong cheo tu phai xuong
            String stateThree = getStateOfLine(totalCol - 1, i, dx[3], dy[3]);
            listStateCheckLine.add(stateThree);
        }
        for (int j = 0; j < totalCol; j++) {
            // duong cheo tu trai xuong
            String stateOne = getStateOfLine(j, 0, dx[1], dy[1]);
            listStateCheckLine.add(stateOne);
            // duong doc
            String stateTwo = getStateOfLine(j, 0, dx[2], dy[2]);
            listStateCheckLine.add(stateTwo);
            // duong cheo tu phai xuong
            String stateThree = getStateOfLine(j, 0, dx[3], dy[3]);
            listStateCheckLine.add(stateThree);
        }
    }

    private String getStateOfLine(int x, int y, int dx, int dy) {
        int i = x;
        int j = y;
        StringBuilder builder = new StringBuilder();
//        builder.append(chessBoard[i][j]);
        while (checkInsideBoard(i + dx, j + dy)) {
            i += dx;
            j += dy;
            builder.append(chessBoard[i][j]);
        }
        String state = builder.toString();
        return state;
    }

    private void initMatrixH() {
        matrixStateWinMAX = new ArrayList<String>();
        matrixStateWinMIN = new ArrayList<String>();
        matrixFaciliateAttack = new ArrayList<String>();
        matrixPrevent = new ArrayList<String>();
        matrixBitFacilitateMAX = new ArrayList<String>();
        matrixBitFacilitateMIN = new ArrayList<String>();
        matrixNormalMAX = new ArrayList<String>();
        matrixNormalMIN = new ArrayList<String>();
        for (int i = 0; i < 10; i++) {
            if (i < 1) {
                matrixAbsoluteWinMAX = toString(matrixH.matrixAbsoluteWinMAX[i]);
                matrixAbsoluteWinMIN = toString(matrixH.matrixAbsoluteWinMIN[i]);
            }
            if (i < 5) {
                String winStateMAX = toString(matrixH.matrixWinStateMAX[i]);
                matrixStateWinMAX.add(winStateMAX);

                String facilitateAttack = toString(matrixH.matrixFacilitateAttack[i]);
                matrixFaciliateAttack.add(facilitateAttack);
            }
            if (i < 6) {
                String preventAttack = toString(matrixH.matrixPreventAttack[i]);
                matrixPrevent.add(preventAttack);
            }
            if (i < 7) {
                String winStateMIN = toString(matrixH.matrixWinStateMIN[i]);
                matrixStateWinMIN.add(winStateMIN);
            }
            if (i < 6) {
                String normalMAX = toString(matrixH.matrixNormalMAX[i]);
                matrixNormalMAX.add(normalMAX);
                String normalMIN = toString(matrixH.matrixNormalMIN[i]);
                matrixNormalMIN.add(normalMIN);
            }
            if (i < 10) {
                String bitFacilitateMAX = toString(matrixH.matrixBitFacilitateMAX[i]);
                matrixBitFacilitateMAX.add(bitFacilitateMAX);
                String bitFacilitateMIN = toString(matrixH.matrixBitFacilitateMIN[i]);
                matrixBitFacilitateMIN.add(bitFacilitateMIN);
            }
        }
    }

    public int heuristicFunction(Move move) {
        int totalPoint = 0;
        chessBoard[move.getRow()][move.getCol()] = (move.isMaxPlayer()) ? PLAYER_MAX : PLAYER_MIN;
        initListStateCheckLine();
        for (String state : listStateCheckLine) {
            int priority = checkPriorityOfLine(state);
            if (priority > 6) Log.d("test_pri", priority + "-----------" + state);
            switch (priority) {
                case 1:
                    totalPoint += POINT_ABSOLUTE_WIN/2;
                    break;
                case 2:
                    totalPoint += POINT_MAKE_SURE_WIN/2;
                    break;
                case 3:
                    totalPoint += POINT_FACILITATE_TO_ATTACK/2;
                    break;
                case 4:
                    totalPoint += POINT_BIT_OF_FACILITATE/2;
                    break;
                case 5:
                    totalPoint += POINT_OF_NORMAL_MOVE;
                    break;
                case 6:
                default:
                    break;
                case 7:
                    totalPoint -= POINT_ABSOLUTE_WIN;
                    break;
                case 8:
                    totalPoint -= POINT_MAKE_SURE_WIN;
                    break;
                case 9:
                    totalPoint -= POINT_FACILITATE_TO_ATTACK;
                    break;
                case 10:
                    totalPoint -= POINT_BIT_OF_FACILITATE;
                    break;
                case 11:
                    totalPoint -= POINT_OF_NORMAL_MOVE;
                    break;
            }
        }
        chessBoard[move.getRow()][move.getCol()] = 0;
        return totalPoint;
    }

    // return priority
    private int checkPriorityOfLine(String state) {
        for (int i = 0; i < 10; i++) {
            if (i < 1 && state.contains(matrixAbsoluteWinMAX)) return 1;
            if (i < 5 && state.contains(matrixStateWinMAX.get(i))) return 2;
            if (i < 5 && state.contains(matrixFaciliateAttack.get(i))) return 3;
            if (i < 10 && state.contains(matrixBitFacilitateMAX.get(i))) return 4;
            if (i < 6 && state.contains(matrixNormalMAX.get(i))) return 5;
            if (i < 1 && state.contains(matrixAbsoluteWinMIN)) return 7;
            if (i < 6 && state.contains(matrixPrevent.get(i))) return 8;
            if (i < 7 && state.contains(matrixStateWinMIN.get(i))) return 9;
            if (i < 10 && state.contains(matrixBitFacilitateMIN.get(i))) return 10;
            if (i < 6 && state.contains(matrixNormalMIN.get(i))) return 11;
        }
        return 6;
    }

    private String toString(int[] matrix) {
        StringBuilder builder = new StringBuilder();
        int len = matrix.length;
        for (int i = 0; i < len; i++) {
            builder.append(matrix[i]);
        }
        return builder.toString();
    }

    private boolean checkInsideBoard(int x, int y) {
        if (x >= 0 && x < totalRow && y >= 0 && y < totalCol) return true;
        return false;
    }
}
