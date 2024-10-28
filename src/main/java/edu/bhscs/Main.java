//use objects to record data
//split them into classes

import java.util.Scanner;

public class Main {
  static char[][] board = {
    {'1', '2', '3'},
    {'4', '5', '6'},
    {'7', '8', '9'}
  };

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    char currentPlayer = 'X'; // Player is 'X', AI (Computer) is 'O'
    boolean gameWon = false;

    printBoard();

    for (int i = 0; i < 9 && !gameWon; i++) {
      if (currentPlayer == 'X') {
        // Player's turn
        System.out.println("Player X, enter a number (1-9) to place your mark:");
        int move = scanner.nextInt();

        if (isValidMove(move)) {
          placeMove(move, currentPlayer);
          printBoard();
          gameWon = checkWin(currentPlayer);

          if (gameWon) {
            System.out.println("Player X wins!");
          } else {
            currentPlayer = 'O'; // Switch to AI
          }
        } else {
          System.out.println("Invalid move, try again.");
          i--; // Retry the same move
        }
      } else {
        // AI's turn
        System.out.println("Computer's turn:");
        int[] bestMove = findBestMove();
        placeMove(bestMove[0], 'O');
        printBoard();
        gameWon = checkWin('O');

        if (gameWon) {
          System.out.println("Computer (O) wins!");
        } else {
          currentPlayer = 'X'; // Switch to player
        }
      }

      // Check for a tie
      if (i == 8 && !gameWon) {
        System.out.println("It's a tie!");
      }
    }

    scanner.close();
  }

  // Method to print the current state of the board
  public static void printBoard() {
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        System.out.print(board[i][j]);
        if (j < 2) System.out.print(" | ");
      }
      if (i < 2) System.out.println("\n---------");
    }
    System.out.println();
  }

  // Method to check if the move is valid
  public static boolean isValidMove(int move) {
    switch (move) {
      case 1: return board[0][0] == '1';
      case 2: return board[0][1] == '2';
      case 3: return board[0][2] == '3';
      case 4: return board[1][0] == '4';
      case 5: return board[1][1] == '5';
      case 6: return board[1][2] == '6';
      case 7: return board[2][0] == '7';
      case 8: return board[2][1] == '8';
      case 9: return board[2][2] == '9';
      default: return false;
    }
  }

    // Method to place a move on the board
  public static void placeMove(int move, char player) {
    switch (move) {
      case 1: board[0][0] = player; break;
      case 2: board[0][1] = player; break;
      case 3: board[0][2] = player; break;
      case 4: board[1][0] = player; break;
      case 5: board[1][1] = player; break;
      case 6: board[1][2] = player; break;
      case 7: board[2][0] = player; break;
      case 8: board[2][1] = player; break;
      case 9: board[2][2] = player; break;
    }
  }

  // Method to check if the player has won
  public static boolean checkWin(char player) {
    // Check rows, columns, and diagonals for a win
    return (board[0][0] == player && board[0][1] == player && board[0][2] == player) ||
    (board[1][0] == player && board[1][1] == player && board[1][2] == player) ||
    (board[2][0] == player && board[2][1] == player && board[2][2] == player) ||
    (board[0][0] == player && board[1][0] == player && board[2][0] == player) ||
    (board[0][1] == player && board[1][1] == player && board[2][1] == player) ||
    (board[0][2] == player && board[1][2] == player && board[2][2] == player) ||
    (board[0][0] == player && board[1][1] == player && board[2][2] == player) ||
    (board[2][0] == player && board[1][1] == player && board[0][2] == player);
  }

  // Minimax algorithm to evaluate the best move for the AI
  public static int minimax(boolean isMaximizing) {
    // Base cases: Check for win, loss, or tie
    if (checkWin('O')) return 1; // AI wins
    if (checkWin('X')) return -1; // Player wins
    if (isBoardFull()) return 0; // Tie

    if (isMaximizing) {
      int bestScore = Integer.MIN_VALUE;
      for (int i = 1; i <= 9; i++) {
        if (isValidMove(i)) {
          placeMove(i, 'O');
          int score = minimax(false);
          placeMove(i, (char) (i + '0')); // Undo move
          bestScore = Math.max(score, bestScore);
        }
      }
      return bestScore;
    } else {
      int bestScore = Integer.MAX_VALUE;
      for (int i = 1; i <= 9; i++) {
        if (isValidMove(i)) {
          placeMove(i, 'X');
          int score = minimax(true);
          placeMove(i, (char) (i + '0')); // Undo move
          bestScore = Math.min(score, bestScore);
        }
      }
      return bestScore;
    }
  }

  // Find the best move for the AI using minimax
  public static int[] findBestMove() {
    int bestMove = -1;
    int bestValue = Integer.MIN_VALUE;

    for (int i = 1; i <= 9; i++) {
      if (isValidMove(i)) {
        placeMove(i, 'O');
        int moveValue = minimax(false);
        placeMove(i, (char) (i + '0')); // Undo move

        if (moveValue > bestValue) {
          bestMove = i;
          bestValue = moveValue;
        }
      }
    }

    return new int[] { bestMove, bestValue };
  }

  // Check if the board is full (tie condition)
  public static boolean isBoardFull() {
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        if (board[i][j] != 'X' && board[i][j] != 'O') {
          return false;
        }
      }
    }
    return true;
  }
}