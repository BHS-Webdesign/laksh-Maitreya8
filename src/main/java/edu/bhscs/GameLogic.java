public class GameLogic {
  private char[][] board;

  // Constructor
  public GameLogic() {
    resetBoard();
  }

  // Reset the board to the initial state
  public void resetBoard() {
    board = new char[][] {
      {'1', '2', '3'},
      {'4', '5', '6'},
      {'7', '8', '9'}
    };
  }

  // Print the current state of the board
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

  // Check if a move is valid
  public boolean isValidMove(int move) {
    return board[(move - 1) / 3][(move - 1) % 3] != 'X' && board[(move - 1) / 3][(move - 1) % 3] != 'O';
  }

  // Place a move on the board
  public void placeMove(int move, char player) {
    board[(move - 1) / 3][(move - 1) % 3] = player;
  }

  // Undo a move (used in Minimax)
  public void undoMove(int move) {
    board[(move - 1) / 3][(move - 1) % 3] = (char) ('0' + move);
  }

  // Check if the board is full
  public boolean isBoardFull() {
    for (char[] row : board) {
      for (char cell : row) {
        if (cell != 'X' && cell != 'O') return false;
      }
    }
    return true;
  }

  // Check if a player has won
  public boolean checkWin(char player) {
    return (board[0][0] == player && board[0][1] == player && board[0][2] == player) ||
    (board[1][0] == player && board[1][1] == player && board[1][2] == player) ||
    (board[2][0] == player && board[2][1] == player && board[2][2] == player) ||
    (board[0][0] == player && board[1][0] == player && board[2][0] == player) ||
    (board[0][1] == player && board[1][1] == player && board[2][1] == player) ||
    (board[0][2] == player && board[1][2] == player && board[2][2] == player) ||
    (board[0][0] == player && board[1][1] == player && board[2][2] == player) ||
    (board[2][0] == player && board[1][1] == player && board[0][2] == player);
  }

  // Get the current board state as a String
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
