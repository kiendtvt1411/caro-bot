package android.dolsoft;

/**
 * Created by kiend on 7/9/2017.
 */

public class MatrixHeuristicFake {

    public final int[][] matrixAbsoluteWinMAX = {
            {1,1,1,1,1}
    };

    public final int[][] matrixAbsoluteWinMIN = {
            {2,2,2,2,2}
    };

    public final int[][] matrixWinStateMAX = {
            {0, 1, 1, 1, 1}, {1, 0, 1, 1, 1}, {1, 1, 0, 1, 1},
            {1, 1, 1, 1, 0}, {1, 1, 1, 0, 1}
    };
    public final int[][] matrixWinStateMIN = {
            {0, 2, 2, 2, 2}, {2, 0, 2, 2, 2}, {2, 2, 0, 2, 2},
            {2, 2, 2, 2, 0}, {2, 2, 2, 0, 2}
    };
    public final int[][] matrixFacilitateAttack = {
            {0, 0, 1, 1, 1, 0}, {0, 1, 0, 1, 1, 0}, {1, 0, 1, 0, 1, 0, 1},
            {0, 1, 1, 1, 0, 0}, {0, 1, 1, 0, 1, 0}
    };
    public final int[][] matrixPreventAttack = {
            {2, 1, 1, 1, 1, 2}, {1, 1, 2, 1, 1}, {1, 0, 1, 2, 1, 0, 1},
            {2, 1, 1, 1, 0}, {1, 1, 2, 1, 0}
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
            {0, 2, 2, 2, 0}, {0, 0, 2, 2, 2}, {0, 2, 0, 2, 2},
            {0, 2, 2, 0, 2}, {2, 0, 0, 2, 2}, {2, 0, 2, 0, 2},
            {2, 2, 2, 0, 0}, {2, 2, 0, 2, 0}, {2, 0, 2, 2, 0},
            {2, 2, 0, 0, 2}
    };
    public final int[][] matrixNormalMIN = {
            {0, 0, 2, 2, 0, 0}, {0, 2, 0, 2, 0, 0}, {0, 2, 2, 0, 0, 0},
            {0, 2, 0, 0, 2, 0}, {0, 0, 2, 0, 2, 0}, {0, 0, 0, 2, 2, 0}
    };

    public MatrixHeuristicFake() {
    }
}
