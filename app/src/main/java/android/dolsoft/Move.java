package android.dolsoft;

/**
 * Created by kiend on 7/5/2017.
 */

public class Move {

    private Move leftMostChild;
    private Move rightSibling;
    private boolean isMaxPlayer; // human
    private int row;
    private int col;

    public Move() {
    }

    public Move(Move leftMostChild, Move rightSibling, boolean isMaxPlayer, int row, int col) {
        this.leftMostChild = leftMostChild;
        this.rightSibling = rightSibling;
        this.isMaxPlayer = isMaxPlayer;
        this.row = row;
        this.col = col;
    }

    public Move getLeftMostChild() {
        return leftMostChild;
    }

    public void setLeftMostChild(Move leftMostChild) {
        this.leftMostChild = leftMostChild;
    }

    public Move getRightSibling() {
        return rightSibling;
    }

    public void setRightSibling(Move rightSibling) {
        this.rightSibling = rightSibling;
    }

    public boolean isMaxPlayer() {
        return isMaxPlayer;
    }

    public void setMaxPlayer(boolean maxPlayer) {
        isMaxPlayer = maxPlayer;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }
}
