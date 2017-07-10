package android.dolsoft;

import android.util.Log;

import java.util.Arrays;

/**
 * Created by kiend on 7/6/2017.
 */

public class HeuristicEvalutating {

    private static final int POINT_ABSOLUTE_WIN = 531441;
    private static final int POINT_MAKE_SURE_WIN = 59049;
    private static final int POINT_FACILITATE_TO_ATTACK = 6561;
    private static final int POINT_PREVENT_OPPONENT = 729;
    private static final int POINT_BIT_OF_FACILITATE = 81;
    private static final int POINT_OF_NORMAL_MOVE = 9;
    private static final int POINT_OTHER_WISE = 1;

    private int[][] chessBoard;
    private int totalRow;
    private int totalCol;
    // 8-line will be considered
    private int[] dr;
    private int[] dc;

    private final int PLAYER_MAX = 1;
    private final int PLAYER_MIN = -1;

    private int[][] matrixAbsoluteWinMAX = {
            {1,1,1,1,1}
    };

    private int[][] matrixAbsoluteWinMIN = {
            {-1,-1,-1,-1,-1}
    };

    private int[][] matrixWinStateMAX = {
            {0, 1, 1, 1, 1}, {1, 0, 1, 1, 1}, {1, 1, 0, 1, 1},
            {1, 1, 1, 1, 0}, {1, 1, 1, 0, 1}
    };
    private int[][] matrixWinStateMIN = {
            {0, -1, -1, -1, -1}, {-1, 0, -1, -1, -1}, {-1, -1, 0, -1, -1},
            {-1, -1, -1, -1, 0}, {-1, -1, -1, 0, -1}
    };
    private int[][] matrixFacilitateAttack = {
            {0, 0, 1, 1, 1, 0}, {0, 1, 0, 1, 1, 0}, {1, 0, 1, 0, 1, 0, 1},
            {0, 1, 1, 1, 0, 0}, {0, 1, 1, 0, 1, 0}
    };
    private int[][] matrixPreventAttack = {
            {0, 0, -1, -1, -1, 0}, {0, -1, 0, -1, -1, 0}, {-1, 0, -1, 0, -1, 0, -1},
            {0, -1, -1, -1, 0, 0}, {0, -1, -1, 0, -1, 0}
    };
    private int[][] matrixBitFacilitateMAX = {
            {0, 1, 1, 1, 0}, {0, 0, 1, 1, 1}, {0, 1, 0, 1, 1},
            {0, 1, 1, 0, 1}, {1, 0, 0, 1, 1}, {1, 0, 1, 0, 1},
            {1, 1, 1, 0, 0}, {1, 1, 0, 1, 0}, {1, 0, 1, 1, 0},
            {1, 1, 0, 0, 1}
    };
    private int[][] matrixNormalMAX = {
            {0, 0, 1, 1, 0, 0}, {0, 1, 0, 1, 0, 0}, {0, 1, 1, 0, 0, 0},
            {0, 1, 0, 0, 1, 0}, {0, 0, 1, 0, 1, 0}, {0, 0, 0, 1, 1, 0}
    };
    private int[][] matrixBitFacilitateMIN = {
            {0, -1, -1, -1, 0}, {0, 0, -1, -1, -1}, {0, -1, 0, -1, -1},
            {0, -1, -1, 0, -1}, {-1, 0, 0, -1, -1}, {-1, 0, -1, 0, -1},
            {-1, -1, -1, 0, 0}, {-1, -1, 0, -1, 0}, {-1, 0, -1, -1, 0},
            {-1, -1, 0, 0, -1}
    };
    private int[][] matrixNormalMIN = {
            {0, 0, -1, -1, 0, 0}, {0, -1, 0, -1, 0, 0}, {0, -1, -1, 0, 0, 0},
            {0, -1, 0, 0, -1, 0}, {0, 0, -1, 0, -1, 0}, {0, 0, 0, -1, -1, 0}
    };

    public HeuristicEvalutating(int[][] chessBoard, int totalRow, int totalCol, int[] dr, int[] dc) {
        this.chessBoard = chessBoard;
        this.totalRow = totalRow;
        this.totalCol = totalCol;
        this.dr = dr;
        this.dc = dc;
    }

    public int evaluatingHeuristic(int currentPlayer, int curRow, int curCol) {
        int totalPoint = 0;
        for (int i = 0; i < totalRow; i++) {
            for (int j = 0; j < totalCol; j++) {
                if (chessBoard[i][j] != 0) {
                    for (int h = 0; h < 4; h++) {
                        int[] state = new int[]{
                                chessBoard[i-1][j-1],
                                chessBoard[i][j],
                                chessBoard[i + dr[h]][j + dc[h]],
                                chessBoard[i + 2 * dr[h]][j + 2 * dc[h]],
                                chessBoard[i + 3 * dr[h]][j + 3 * dc[h]],
                                chessBoard[i + 4 * dr[h]][j + 4 * dc[h]]
                        };
                        int priority = (currentPlayer == PLAYER_MAX) ? calculateStateMAX(state) :
                                calculateStateMIN(state);

                        if(priority<6) Log.d("consider_", "state: "
                                + state[0]
                                + state[1]
                                + state[2]
                                + state[3]
                                + state[4]
                                + ", priority: " + priority);
                        totalPoint = calculateTotalPoint(currentPlayer,
                                priority, totalPoint);
                    }
                }
            }
        }
        if (chessBoard[curRow][curCol] != 0)
            chessBoard[curRow][curCol] = 0;
        return totalPoint;
    }

    // return priority
    private int calculateStateMAX(int[] curState) {
        int priority = -1;
        int lengthOne = matrixWinStateMAX.length;
        int lengthTwo = matrixFacilitateAttack.length;
        int lengthThree = matrixPreventAttack.length;
        int lengthFour = matrixBitFacilitateMAX.length;
        int lengthFive = matrixNormalMAX.length;
        for (int i = 0; i < 10; i++) {
            if (i < 1) {
                if(Arrays.equals(curState, matrixAbsoluteWinMAX[i])) return 0;
            }
            if (i < lengthOne) {
                if (Arrays.equals(curState, matrixWinStateMAX[i])) return 1;
            }
            if (i < lengthTwo) {
                if (Arrays.equals(curState, matrixFacilitateAttack[i])) return 2;
            }
            if (i < lengthThree) {
                if (Arrays.equals(curState, matrixPreventAttack[i])) return 3;
            }
            if (i < lengthFour) {
                if (Arrays.equals(curState, matrixBitFacilitateMAX[i])) return 4;
            }
            if (i < lengthFive) {
                if (Arrays.equals(curState, matrixNormalMAX[i])) return 5;
            }
        }
        if (priority == -1) priority = 6;
        return priority;
    }

    // return priority
    private int calculateStateMIN(int[] curState) {
        int priority = -1;
        int lengthOne = matrixWinStateMIN.length;
        int lengthTwo = matrixPreventAttack.length;
        int lengthThree = matrixFacilitateAttack.length;
        int lengthFour = matrixBitFacilitateMIN.length;
        int lengthFive = matrixNormalMIN.length;
        for (int i = 0; i < 10; i++) {
            if (i < 1) {
                if(Arrays.equals(curState, matrixAbsoluteWinMIN[i])) return 0;
            }
            if (i < lengthOne) {
                if (Arrays.equals(curState, matrixWinStateMIN[i])) return 1;
            }
            if (i < lengthTwo) {
                if (Arrays.equals(curState, matrixPreventAttack[i])) return 2;
            }
            if (i < lengthThree) {
                if (Arrays.equals(curState, matrixFacilitateAttack[i])) return 3;
            }
            if (i < lengthFour) {
                if (Arrays.equals(curState, matrixBitFacilitateMIN[i])) return 4;
            }
            if (i < lengthFive) {
                if (Arrays.equals(curState, matrixNormalMIN[i])) return 5;
            }
        }
        if (priority == -1) priority = 6;
        return priority;
    }

    private int calculateTotalPoint(int currentPlayer, int priority, int totalPoint) {
        if (currentPlayer == PLAYER_MAX) {
            switch (priority) {
                case 0:
                    totalPoint += POINT_ABSOLUTE_WIN;
                    break;
                case 1:
                    totalPoint += POINT_MAKE_SURE_WIN;
                    break;
                case 2:
                    totalPoint += POINT_FACILITATE_TO_ATTACK;
                    break;
                case 3:
                    totalPoint += POINT_PREVENT_OPPONENT;
                    break;
                case 4:
                    totalPoint += POINT_BIT_OF_FACILITATE;
                    break;
                case 5:
                    totalPoint += POINT_OF_NORMAL_MOVE;
                    break;
                case 6:
                    totalPoint += POINT_OTHER_WISE;
                    break;
                default:
                    break;
            }
        } else {
            switch (priority) {
                case 0:
                    totalPoint -= POINT_ABSOLUTE_WIN;
                    break;
                case 1:
                    totalPoint -= POINT_MAKE_SURE_WIN;
                    break;
                case 2:
                    totalPoint -= POINT_FACILITATE_TO_ATTACK;
                    break;
                case 3:
                    totalPoint -= POINT_PREVENT_OPPONENT;
                    break;
                case 4:
                    totalPoint -= POINT_BIT_OF_FACILITATE;
                    break;
                case 5:
                    totalPoint -= POINT_OF_NORMAL_MOVE;
                    break;
                case 6:
                    totalPoint -= POINT_OTHER_WISE;
                    break;
                default:
                    break;
            }
        }
        return totalPoint;
    }
}
