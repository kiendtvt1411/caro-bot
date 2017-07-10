package android.dolsoft;

import android.graphics.Point;
import android.util.Log;

import static android.content.ContentValues.TAG;

/**
 * Created by kiend on 7/7/2017.
 */

public class Heuristic {

    private static final int PLAYER_MAX = 1;
    private static final int PLAYER_MIN = -1;

    private int[][] chessBoard;
    private int totalRow;
    private int totalCol;
    private int[] drUp;
    private int[] dcUp;
    private int[] drDown;
    private int[] dcDown;

    public Heuristic(int[][] chessBoard, int totalRow, int totalCol) {
        this.chessBoard = chessBoard;
        this.totalRow = totalRow;
        this.totalCol = totalCol;
        drUp = new int[]{-1, -1, 0, 1};
        dcUp = new int[]{0, -1, -1, -1};
        drDown = new int[]{1, 1, 0, -1};
        dcDown = new int[]{0, 1, 1, 1};
    }

    public int evaluatingHeuristic(Move curMove) {
        int curRow = curMove.getRow();
        int curCol = curMove.getCol();
        int currentPlayer = curMove.isMaxPlayer() ? PLAYER_MAX : PLAYER_MIN;
        int opponent = (curMove.isMaxPlayer()) ? PLAYER_MIN : PLAYER_MAX;
        int totalPoint = 0;
        Point start = new Point(curRow, curCol);
        Point end;

        chessBoard[curRow][curCol] = currentPlayer;

        if (currentPlayer == PLAYER_MAX) {
            Log.d(TAG, "evaluatingHeuristic: PLAYER_MAX");
            for (int h = 0; h < 4; h++) {
//                if (checkInsideBoard(start.x + drUp[h], start.y + dcUp[h])
//                        && chessBoard[start.x + drUp[h]][start.y + dcUp[h]] == opponent) {
//                    totalPoint -= 100;
//                }
//                if (checkInsideBoard(start.x + 2 * drUp[h], start.y + 2 * dcUp[h])
//                        && chessBoard[start.x + 2 * drUp[h]][start.y + 2 * dcUp[h]] == opponent) {
//                    totalPoint -= 200;
//                }
//                if (checkInsideBoard(start.x + drDown[h], start.y + dcDown[h])
//                        && chessBoard[start.x + drDown[h]][start.y + dcDown[h]] == opponent) {
//                    totalPoint -= 100;
//                }
//                if (checkInsideBoard(start.x + 2 * drDown[h], start.y + 2 * dcDown[h])
//                        && chessBoard[start.x + 2 * drDown[h]][start.y + 2 * dcDown[h]] == opponent) {
//                    totalPoint -= 200;
//                }

                do {
                    EndPoint endPoint = lastOfSegmentLine(opponent, start.x, start.y, drUp[h], dcUp[h]);
                    end = endPoint.pointEnd;
                    int len = endPoint.len;
                    if (checkInsideBoard(start.x + drDown[h], start.y + dcDown[h])
                            && chessBoard[start.x][start.y] == chessBoard[start.x + drDown[h]][start.y + dcDown[h]])
                        ++len;
                    totalPoint += len * len;
                    if (len == 3) totalPoint = totalPoint - len * len + 100 * 100;
                    if (len == 4) totalPoint = totalPoint - len * len + 200 * 200;
                    int x = end.x;
                    int y = end.y;
                    start = end;
                    int opponentLen = 0;
                    while (checkInsideBoard(start.x, start.y)
                            && chessBoard[start.x][start.y] == chessBoard[end.x][end.y]
                            && x != 0 && y != 0
                            && checkInsideBoard(x + drUp[h], y + dcUp[h])) {
                        x += drUp[h];
                        y += dcUp[h];
                        if(chessBoard[x][y]==opponent) opponentLen++;
                        if (chessBoard[x][y] == currentPlayer) {
                            start = new Point(x, y);
                            if(opponentLen<3) totalPoint-=opponentLen*opponentLen;
                            if(opponentLen==3) totalPoint+=150*150;
                            break;
                        }
                    }
                } while (checkInsideBoard(start.x, start.y)
                        && chessBoard[start.x][start.y] != chessBoard[end.x][end.y] && end.x != 0 && end.y != 0);
                start = new Point(curRow, curCol);
                do {
                    EndPoint endPoint = lastOfSegmentLine(opponent, start.x, start.y, drDown[h], dcDown[h]);
                    end = endPoint.pointEnd;
                    int len = endPoint.len;
                    if (checkInsideBoard(start.x + drUp[h], start.y + dcUp[h])
                            && chessBoard[start.x][start.y] == chessBoard[start.x + drUp[h]][start.y + dcUp[h]])
                        ++len;
                    totalPoint += len * len;
                    if (len == 3) totalPoint = totalPoint - len * len + 100 * 100;
                    if (len == 4) totalPoint = totalPoint - len * len + 200 * 200;
                    int x = end.x;
                    int y = end.y;
                    start = end;
                    int opponentLen = 0;
                    while (checkInsideBoard(start.x, start.y)
                            && chessBoard[start.x][start.y] == chessBoard[end.x][end.y]
                            && x != 0 && y != 0 && checkInsideBoard(x + drDown[h], y + dcDown[h])) {
                        x += drDown[h];
                        y += dcDown[h];
                        if(chessBoard[x][y]==opponent) opponentLen++;
                        if (chessBoard[x][y] == currentPlayer) {
                            start = new Point(x, y);
                            if(opponentLen<3) totalPoint-=opponentLen*opponentLen;
                            if(opponentLen==3) totalPoint+=150*150;
                            break;
                        }
                    }
                } while (checkInsideBoard(start.x, start.y)
                        && chessBoard[start.x][start.y] != chessBoard[end.x][end.y] && end.x != 0 && end.y != 0);
            }
        } else {
            Log.d(TAG, "evaluatingHeuristic: PLAYER_MIN");
            for (int h = 0; h < 4; h++) {
//                if (checkInsideBoard(start.x + drUp[h], start.y + dcUp[h])
//                        && chessBoard[start.x + drUp[h]][start.y + dcUp[h]] == opponent) {
//                    totalPoint += 100*100;
//                }
//                if (checkInsideBoard(start.x + 2 * drUp[h], start.y + 2 * dcUp[h])
//                        && chessBoard[start.x + 2 * drUp[h]][start.y + 2 * dcUp[h]] == opponent) {
//                    totalPoint += 150*150;
//                }
//                if (checkInsideBoard(start.x + drDown[h], start.y + dcDown[h])
//                        && chessBoard[start.x + drDown[h]][start.y + dcDown[h]] == opponent) {
//                    totalPoint += 100*100;
//                }
//                if (checkInsideBoard(start.x + 2 * drDown[h], start.y + 2 * dcDown[h])
//                        && chessBoard[start.x + 2 * drDown[h]][start.y + 2 * dcDown[h]] == opponent) {
//                    totalPoint += 150*150;
//                }

                do {
                    EndPoint endPoint = lastOfSegmentLine(opponent, start.x, start.y, drUp[h], dcUp[h]);
                    end = endPoint.pointEnd;
                    int len = endPoint.len;
                    if (checkInsideBoard(start.x + drDown[h], start.y + dcDown[h])
                            && chessBoard[start.x][start.y] == chessBoard[start.x + drDown[h]][start.y + dcDown[h]])
                        ++len;
                    totalPoint -= len * len;
                    if (len == 3) totalPoint = totalPoint + len * len - 100 * 100;
                    if (len == 4) totalPoint = totalPoint + len * len - 200 * 200;
                    int x = end.x;
                    int y = end.y;
                    start = end;
                    int opponentLen = 0;
                    while (checkInsideBoard(start.x, start.y)
                            && chessBoard[start.x][start.y] == chessBoard[end.x][end.y]
                            && x != 0 && y != 0
                            && checkInsideBoard(x + drUp[h], y + dcUp[h])) {
                        x += drUp[h];
                        y += dcUp[h];
                        if(chessBoard[x][y]==opponent) opponentLen++;
                        if (chessBoard[x][y] == currentPlayer) {
                            start = new Point(x, y);
                            if(opponentLen<3) totalPoint+=opponentLen*opponentLen;
                            if(opponentLen==3) totalPoint-=150*150;
                            break;
                        }
                    }
                } while (checkInsideBoard(start.x, start.y)
                        && chessBoard[start.x][start.y] != chessBoard[end.x][end.y] && end.x != 0 && end.y != 0);
                start = new Point(curRow, curCol);
                do {
                    EndPoint endPoint = lastOfSegmentLine(opponent, start.x, start.y, drDown[h], dcDown[h]);
                    end = endPoint.pointEnd;
                    int len = endPoint.len;
                    if (checkInsideBoard(start.x + drUp[h], start.y + dcUp[h])
                            && chessBoard[start.x][start.y] == chessBoard[start.x + drUp[h]][start.y + dcUp[h]])
                        ++len;
                    totalPoint -= len * len;
                    if (len == 3) totalPoint = totalPoint + len * len - 100 * 100;
                    if (len == 4) totalPoint = totalPoint + len * len - 200 * 200;
                    int x = end.x;
                    int y = end.y;
                    int opponentLen = 0;
                    // chessBoard(endPoint) = opponent
                    while (checkInsideBoard(start.x, start.y)
                            && chessBoard[start.x][start.y] == chessBoard[end.x][end.y]
                            && x != 0 && y != 0 && checkInsideBoard(x + drDown[h], y + dcDown[h])) {
                        x += drDown[h];
                        y += dcDown[h];
                        if(chessBoard[x][y]==opponent) opponentLen++;
                        if (chessBoard[x][y] == currentPlayer) {
                            start = new Point(x, y);
                            if(opponentLen<3) totalPoint+=opponentLen*opponentLen;
                            if(opponentLen==3) totalPoint-=150*150;
                            break;
                        }
                    }
                } while (checkInsideBoard(start.x, start.y)
                        && chessBoard[start.x][start.y] != chessBoard[end.x][end.y] && end.x != 0 && end.y != 0);
            }
        }

        chessBoard[curRow][curCol] = 0;
        return totalPoint;
    }

    // last point X (or O) in line
    private EndPoint lastOfSegmentLine(int opponent, int curRow, int curCol, int dr, int dc) {
        int x = curRow;
        int y = curCol;
        int len = 0;
        do {
            x += dr;
            y += dc;
            if (checkInsideBoard(x, y) && chessBoard[x][y] != 0 && chessBoard[x][y] != opponent)
                len++;
            if(len==4) break;
        } while (checkInsideBoard(x, y)
                && chessBoard[x][y] != opponent);
        Point end = new Point(x, y);
        return new EndPoint(end, len);
    }

    private boolean checkInsideBoard(int x, int y) {
        if (x > 0 && x < totalRow && y > 0 && y < totalCol) return true;
        return false;
    }

    private class EndPoint {
        Point pointEnd;
        int len;

        public EndPoint(Point pointEnd, int len) {
            this.pointEnd = pointEnd;
            this.len = len;
        }
    }
}
