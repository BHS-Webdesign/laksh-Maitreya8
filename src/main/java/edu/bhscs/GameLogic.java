public class GameLogic {
  private char[][] board;

  public GameLogic() {
    resetBoard();
  }

  public void resetBoard() {
    board = new char[][]{
      {'1', '2', '3'},
      {'4', '5', '6'},
      {'7', '8', '9'}
    };
  }

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

  public boolean isValidMove(int move) {
    return board[(move - 1) / 3][(move - 1) % 3] != 'X' &&
    board[(move - 1) / 3][(move - 1) % 3] != 'O';
  }

  public void placeMove(int move, char player) {
    board[(move - 1) / 3][(move - 1) % 3] = player;
  }

  public void undoMove(int move) {
    board[(move - 1) / 3][(move - 1) % 3] = (char) ('0' + move); // Reset to original number
  }

  public boolean isBoardFull() {
    for (char[] row : board) {
      for (char cell : row) {
        if (cell != 'X' && cell != 'O') return false;
      }
    }
    return true;
  }

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