package hu.ait.android.tictactoe.model;

/**
 * Created by Jason on 9/21/15.
 */

// Responsible for the game logic.
public class TicTacToeModel {

    // This is how you write for singleton patterns.

    //
    private static TicTacToeModel instance = null;

    // Nobody else can create this class from the outside
    private TicTacToeModel() {
    }

    // Cannot create new model from
    public static TicTacToeModel getInstance() {
        if (instance == null) {
            instance = new TicTacToeModel();
        }
        return instance;
    }

    // end of singleton pattern implementation.

    // used short instead of integer -> take up less memory.
    public static final short EMPTY = 0;
    public static final short CIRCLE = 1;
    public static final short CROSS = 2;

    // used short 2d-array
    private short[][] model = {
            {EMPTY, EMPTY, EMPTY},
            {EMPTY, EMPTY, EMPTY},
            {EMPTY, EMPTY, EMPTY}
    };
    private short nextPlayer = CIRCLE;

    // Reset function
    public void resetModel() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                model[i][j] = EMPTY;
            }
        }
        nextPlayer = CIRCLE;
    }

    public short getFieldContent(int x, int y) {
        return model[x][y];
    }

    public short setFieldContent(int x, int y, short content) {
        return model[x][y] = content;
    }

    public short getNextPlayer() {
        return nextPlayer;
    }

    // If statement in Java programming
    public void changeNextPlayer() {
        nextPlayer = (nextPlayer == CIRCLE) ? CROSS : CIRCLE;
    }

    /* The above is similar to
        if (nextPlayer == CIRCLE) {
            nextPlayer = CROSS;
        }
        else {
            nextPlayer = CIRCLE;
        }
     */

    public boolean isWinner() {
        return (checkRowWinCondition() || checkColumnWinCondition() || checkDiagonalWinCondition());
    }

    private boolean checkRowWinCondition() {
        for (int i = 0; i < 3; i++) {
            if (checkRowAndColumn((model[i][0]), (model[i][1]), (model[i][2]))) {
                return true;
            }
        }
        return false;
    }

    private boolean checkColumnWinCondition() {
        for (int j = 0; j < 3; j++) {
            if (checkRowAndColumn((model[0][j]), (model[1][j]), (model[2][j]))) {
                return true;
            }
        }
        return false;
    }

    private boolean checkDiagonalWinCondition() {
        return checkRowAndColumn((model[0][0]), (model[1][1]), (model[2][2])) ||
                checkRowAndColumn((model[0][2]), (model[1][1]), (model[2][0]));
    }

    private boolean checkRowAndColumn(short x1, short x2, short x3) {
        return ((x1 != EMPTY) && (x1 == x2) && (x2 == x3));
    }

    public boolean boardIsFull() {
        boolean isFull = true;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (model[i][j] == EMPTY) {
                    isFull = false;
                }
            }
        }

        return isFull;
    }

}
