import java.util.Arrays;
import java.util.Scanner;

public class Game {
    private static final char EMPTY = '-';
    private static final char PLAYER = 'X';
    private static final char AI = 'O';
    private char[][] board;

    public Game() {
        reset();
    }

    public void reset() {
        board = new char[3][3];
        for (char[] row : board) Arrays.fill(row, EMPTY);
    }

    public void printBoard() {
        for (char[] row : board) {
            for (char cell : row) System.out.print(cell + " ");
            System.out.println();
        }
    }

    public void playerMove(Scanner scanner) {
        int row, col;
        while (true) {
            System.out.print("Enter row (0-2) and column (0-2): ");
            row = scanner.nextInt();
            col = scanner.nextInt();
            if (row >= 0 && row < 3 && col >= 0 && col < 3 && board[row][col] == EMPTY) {
                board[row][col] = PLAYER;
                break;
            }
            System.out.println("Invalid move. Try again.");
        }
    }

    public void aiMove(QLearningAI ai) {
        int[] move = ai.getBestMove(board);
        board[move[0]][move[1]] = AI;
        System.out.println("AI moves to " + move[0] + ", " + move[1]);
    }

    public boolean isGameOver() {
        if (getWinner() != EMPTY) return true;
        for (char[] row : board) {
          for (char cell : row) {
            if (cell == EMPTY) return false;
          }
        }
      return true;
    }


    public char getWinner() {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] != EMPTY && board[i][0] == board[i][1] && board[i][1] == board[i][2]) return board[i][0];
            if (board[0][i] != EMPTY && board[0][i] == board[1][i] && board[1][i] == board[2][i]) return board[0][i];
        }
        if (board[0][0] != EMPTY && board[0][0] == board[1][1] && board[1][1] == board[2][2]) return board[0][0];
        if (board[0][2] != EMPTY && board[0][2] == board[1][1] && board[1][1] == board[2][0]) return board[0][2];
        return EMPTY;
    }

    public char[][] getBoard() {
        return board;
    }
}

