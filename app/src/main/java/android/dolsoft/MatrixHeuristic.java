package android.dolsoft;

/**
 * Created by kiend on 7/8/2017.
 */

public class MatrixHeuristic {

    public final int[][] matrixAbsoluteWinMAX = {
            {1,1,1,1,1}
    };

    public final int[][] matrixAbsoluteWinMIN = {
            {-1,-1,-1,-1,-1}
    };

    public final int[][] matrixWinStateMAX = {
            {0, 1, 1, 1, 1}, {1, 0, 1, 1, 1}, {1, 1, 0, 1, 1},
            {1, 1, 1, 1, 0}, {1, 1, 1, 0, 1}
    };
    public final int[][] matrixWinStateMIN = {
            {0, -1, -1, -1, -1}, {-1, 0, -1, -1, -1}, {-1, -1, 0, -1, -1},
            {-1, -1, -1, -1, 0}, {-1, -1, -1, 0, -1}
    };
    public final int[][] matrixFacilitateAttack = {
            {0, 0, 1, 1, 1, 0}, {0, 1, 0, 1, 1, 0}, {1, 0, 1, 0, 1, 0, 1},
            {0, 1, 1, 1, 0, 0}, {0, 1, 1, 0, 1, 0}
    };
    public final int[][] matrixPreventAttack = {
            {0, 0, -1, -1, -1, 0}, {0, -1, 0, -1, -1, 0}, {-1, 0, -1, 0, -1, 0, -1},
            {0, -1, -1, -1, 0, 0}, {0, -1, -1, 0, -1, 0}
    };
    public final int[][] matrixBitFacilitateMAX = {
            {0, 1, 1, 1, 0}, {0, 0, 1, 1, 1}, {0, 1, 0, 1, 1},
            {0, 1, 1, 0, 1}, {1, 0, 0, 1, 1}, {1, 0, 1, 0, 1},
            {1, 1, 1, 0, 0}, {1, 1, 0, 1, 0}, {1, 0, 1, 1, 0},
            {1, 1, 0, 0, 1}
    };
    public final int[][] matrixNormalMAX = {
            {0, 0, 1, 1, 0, 0}, {0, 1, 0, 1, 0, 0}, {0, 1, 1, 0, 0, 0},
            {0, 1, 0, 0, 1, 0}, {0, 0, 1, 0, 1, 0}, {0, 0, 0, 1, 1, 0}
    };
    public final int[][] matrixBitFacilitateMIN = {
            {0, -1, -1, -1, 0}, {0, 0, -1, -1, -1}, {0, -1, 0, -1, -1},
            {0, -1, -1, 0, -1}, {-1, 0, 0, -1, -1}, {-1, 0, -1, 0, -1},
            {-1, -1, -1, 0, 0}, {-1, -1, 0, -1, 0}, {-1, 0, -1, -1, 0},
            {-1, -1, 0, 0, -1}
    };
    public final int[][] matrixNormalMIN = {
            {0, 0, -1, -1, 0, 0}, {0, -1, 0, -1, 0, 0}, {0, -1, -1, 0, 0, 0},
            {0, -1, 0, 0, -1, 0}, {0, 0, -1, 0, -1, 0}, {0, 0, 0, -1, -1, 0}
    };

    public MatrixHeuristic() {
    }
}
