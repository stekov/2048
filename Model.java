public class Model implements Boardgame{

    final String[][] board;
    int randRow;
    int randCol;
    String tile;
    String currentMessage = "No message yet";
    private final int totalTiles;
    private int tileCount = 0;
    private int emptyCount = 0;
    private int moves;
    private int prevTile;


    // Initiates board and state
    public Model(int n){
        board = new String[n][n];
        totalTiles = (int) Math.pow(board.length, 2);

        for (int row=0; row<board.length; row++) {
            for (int col=0; col<board.length; col++) {
                board[row][col] = "0";
            }
        }
        randomTile();
    }

    // Model for mock game, easy to win
    public Model(int n, boolean mock){
        board = new String[n][n];
        totalTiles = (int) Math.pow(board.length, 2);

        for (int row=0; row<board.length; row++) {
            for (int col=0; col<board.length; col++) {
                board[row][col] = "0";
            }
        }
        board[n-1][n-2] = "1024";
        board[n-1][n-1] = "1024";
        randomTile();
    }

    private void randomTile() {
        String[] startNums = new String[2];
        startNums[0] = "2";
        startNums[1] = "4";
        randRow = (int) (Math.random() * board.length);
        randCol = (int) (Math.random() * board.length);
        int randNum = (int) (Math.random() * 2);    // int-cast will floor

        // Handles collision
        while (!board[randRow][randCol].equals("0")) {
            randRow = (int) (Math.random() * board.length);
            randCol = (int) (Math.random() * board.length);
        }
        board[randRow][randCol] = startNums[randNum];     // Lägger ut 2 eller 4
        tileCount += 1;
        tile = board[randRow][randCol];
    }

    // Test MockGame to win
    private void win(int newTile) {
        if (newTile == 2048) {
            currentMessage = "CONGRATULATIONS, you won!";
        }
    }

    // Checks if there is a possible move to make, when full board
    private boolean sameNeighbour() {
        for (int row=0; row<board.length; row++) {
            for (int col=0; col<board.length; col++) {
                tile = board[row][col];
                // Handles index out of bounds
                if (row+1 != board.length) {
                    // If two neighbouring tiles are same, there is a possible move to make
                    if (board[row + 1][col].equals(tile)) {
                        return true;
                    }
                }
                if (col+1 != board.length) {
                    if (board[row][col + 1].equals(tile)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean gameOver() {
        if (tileCount == totalTiles) {
            if (!sameNeighbour()) {
                currentMessage = "GAME OVER... Press enter to play again.";
                return true;
            }
            // Else a move is possible, but you picked a pointless one
            return false;
        }
        return false;
    }

    private void moveLeft() {
        String a;
        for (int row=0; row<board.length; row++) {
            for (int col=0; col<board.length; col++) {
                a = board[row][col];
                if (a.equals("0")) {        // If empty
                    emptyCount += 1;
                }
                // Not empty and has empty place(s) in front
                else if (emptyCount != 0) {
                    board[row][col-emptyCount] = a;
                    board[row][col] = "0";
                    moves += 1;      // Ensures we are not "stuck"
                }
                // Else we have a number, but stuck
            }
            // Row is checked
            emptyCount = 0;
        }
    }

    private void moveRight() {
        String a;
        for (int row=board.length-1; row>=0; row--) {
            for (int col=board.length-1; col>=0; col--) {
                a = board[row][col];
                if (a.equals("0")) {
                    emptyCount += 1;
                }
                else if (emptyCount != 0) {
                    board[row][col+emptyCount] = a;
                    board[row][col] = "0";
                    moves += 1;
                }
            }
            emptyCount = 0;
        }
    }

    private void moveUp() {
        String a;
        for (int col=0; col<board.length; col++) {
            for (int row=0; row<board.length; row++) {
                a = board[row][col];
                if (a.equals("0")) {
                    emptyCount += 1;
                }
                else if (emptyCount != 0) {
                    board[row-emptyCount][col] = a;
                    board[row][col] = "0";
                    moves += 1;
                }
            }
            emptyCount = 0;
        }
    }

    private void moveDown() {
        String a;
        for (int col=board.length-1; col>=0; col--) {
            for (int row=board.length-1; row>=0; row--) {
                a = board[row][col];
                if (a.equals("0")) {
                    emptyCount += 1;
                }
                else if (emptyCount != 0) {
                    board[row+emptyCount][col] = a;
                    board[row][col] = "0";
                    moves += 1;
                }
            }
            emptyCount = 0;
        }
    }

    private void moveTiles(String direction) {
        moves = 0;
        if (direction.equals("l")) {
            moveLeft();
        }
        if (direction.equals("r")) {
            moveRight();
        }
        if (direction.equals("u")) {
            moveUp();
        }
        if (direction.equals("d")) {
            moveDown();
        }
    }

    private void mergeLeft() {
        int newTile;
        for (int row=0; row<board.length; row++) {
            prevTile = 0;
            for (int col=0; col<board.length; col++) {
                newTile = Integer.parseInt(board[row][col]);
                // Empty cell (the previous ones too)
                if (newTile == 0) {
                    emptyCount += 1;
                }
                // Not empty, either same number (merge) or different (add)
                else {
                    // Same tile values: merge
                    if (newTile == prevTile) {
                        newTile = 2*newTile;
                        board[row][col] = Integer.toString(newTile);
                        board[row][col-1] = "0";
                        tileCount -= 1;
                        emptyCount += 1;
                        moves += 1;
                        // Checks if reached 2048
                        win(newTile);
                    }
                    // Not same tile value, no merge
                    if (emptyCount > 0) {
                        board[row][col-emptyCount] = board[row][col];
                        board[row][col] = "0";
                    }
                }
                prevTile = newTile;
            }
            emptyCount = 0;
        }
    }

    private void mergeRight() {
        int newTile;
        for (int row=board.length-1; row>=0; row--) {
            prevTile = 0;
            for (int col=board.length-1; col>=0; col--) {
                newTile = Integer.parseInt(board[row][col]);
                if (newTile == 0) {
                    emptyCount += 1;
                }
                else {
                    if (newTile == prevTile) {
                        newTile = 2*newTile;
                        board[row][col] = Integer.toString(newTile);
                        board[row][col+1] = "0";
                        tileCount -= 1;
                        emptyCount += 1;
                        moves += 1;
                        win(newTile);
                    }
                    if (emptyCount > 0) {
                        board[row][col+emptyCount] = board[row][col];
                        board[row][col] = "0";
                    }
                }
                prevTile = newTile;
            }
            emptyCount = 0;
        }
    }

    private void mergeUp() {
        int newTile;
        for (int col=0; col<board.length; col++) {
            prevTile = 0;
            for (int row=0; row<board.length; row++) {
                newTile = Integer.parseInt(board[row][col]);
                if (newTile == 0) {
                    emptyCount += 1;
                }
                else {
                    if (newTile == prevTile) {
                        newTile = 2*newTile;
                        board[row][col] = Integer.toString(newTile);
                        board[row-1][col] = "0";
                        tileCount -= 1;
                        emptyCount += 1;
                        moves += 1;
                        win(newTile);
                    }
                    if (emptyCount > 0) {
                        board[row-emptyCount][col] = board[row][col];
                        board[row][col] = "0";
                    }
                }
                prevTile = newTile;
            }
            emptyCount = 0;
        }
    }

    private void mergeDown() {
        int newTile;
        for (int col=board.length-1; col>=0; col--) {
            prevTile = 0;
            for (int row=board.length-1; row>=0; row--) {
                newTile = Integer.parseInt(board[row][col]);
                if (newTile == 0) {
                    emptyCount += 1;
                }
                else {
                    if (newTile == prevTile) {
                        newTile = 2*newTile;
                        board[row][col] = Integer.toString(newTile);
                        board[row+1][col] = "0";
                        tileCount -= 1;
                        emptyCount += 1;
                        moves += 1;
                        win(newTile);
                    }
                    if (emptyCount > 0) {
                        board[row+emptyCount][col] = board[row][col];
                        board[row][col] = "0";
                    }
                }
                prevTile = newTile;
            }
            emptyCount = 0;
        }
    }

    private void mergeTiles(String direction) {
        if (direction.equals("l")) {
            mergeLeft();
        }
        if (direction.equals("r")) {
            mergeRight();
        }
        if (direction.equals("u")) {
            mergeUp();
        }
        if (direction.equals("d")) {
            mergeDown();
        }
    }

    // Below are implementations of Boardgame methods

    @Override
    public boolean move(String direction) {
        // Quit game
        if (direction.equals("q")) {
            return false;
        }
        // For the non-graphic test game
        if (!direction.equals("l") && !direction.equals("r") && !direction.equals("u") && !direction.equals("d")) {
            currentMessage = "Invalid move... You have to choose between: l, r, u, d.";
            return true;
        }
        currentMessage = "Move ok";
        moveTiles(direction);
        mergeTiles(direction);
        // New random tile if possible
        if (moves > 0) {
            randomTile();
            return true;
        }
        // Either stuck in one direction or game over
        else {
            currentMessage = "That won't do anything...";
            return !gameOver();
        }

    }

    @Override
    public String getStatus(int i, int j) {
        // return tile on position (i,j)
        return board[i][j];
    }

    @Override
    public String getMessage() {
        // Returns OK, error message or game over
        return currentMessage;
    }
}
