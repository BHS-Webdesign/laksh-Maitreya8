public class GameLogic {
  private char[][] board;

  public GameLogic() {
    resetBoard();
  }

  // Resets the game board to its initial state
  public void resetBoard() {
    board = new char[][]{
      {'1', '2', '3'},
      {'4', '5', '6'},
      {'7', '8', '9'}
    };
  }

  // Prints the current state of the board to the console
  public void printBoard() {
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        System.out.print(board[i][j]);
        if (j < 2) System.out.print(" | ");
      }
      if (i < 2) System.out.println("\n---------");
    }
    System.out.println();
  }

  // Checks if a given move is valid (i.e., the cell is not already occupied by 'X' or 'O')
  public boolean isValidMove(int move) {
    return board[(move - 1) / 3][(move - 1) % 3] != 'X' &&
    board[(move - 1) / 3][(move - 1) % 3] != 'O';
  }

  // Places a move on the board for the specified player
  public void placeMove(int move, char player) {
    board[(move - 1) / 3][(move - 1) % 3] = player;
  }

  // Reverts a move by resetting the specified cell to its original placeholder value
  // IMP for MinMax
  public void undoMove(int move) {
    board[(move - 1) / 3][(move - 1) % 3] = (char) ('0' + move); // Reset to original number
  }

  // Checks if all the cells on the board are occupied
  public boolean isBoardFull() {
    for (char[] row : board) {
      for (char cell : row) {
        if (cell != 'X' && cell != 'O') return false;
      }
    }
    return true;
  }

  // Checks if the specified player ('X' or 'O') has won the game
  public boolean checkWin(char player) {
    return (board[0][0] == player && board[0][1] == player && board[0][2] == player) || // Rows
    (board[1][0] == player && board[1][1] == player && board[1][2] == player) ||
    (board[2][0] == player && board[2][1] == player && board[2][2] == player) ||
    (board[0][0] == player && board[1][0] == player && board[2][0] == player) || // Columns
    (board[0][1] == player && board[1][1] == player && board[2][1] == player) ||
    (board[0][2] == player && board[1][2] == player && board[2][2] == player) ||
    (board[0][0] == player && board[1][1] == player && board[2][2] == player) || // Diagonals
    (board[2][0] == player && board[1][1] == player && board[0][2] == player);
  }

  // Returns the current state of the board as a single string
  public String getState() {
    StringBuilder state = new StringBuilder();
    for (char[] row : board) {
      for (char cell : row) {
        state.append(cell);
      }
    }
  return state.toString();
  }
}