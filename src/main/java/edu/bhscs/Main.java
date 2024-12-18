import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    GameLogic game = new GameLogic();
    AI ai = new AI();
    QTableHandler qTable = new QTableHandler();

    boolean playAgain = true;

    while (playAgain) {
      game.resetBoard(); // Reset the board for a new game
      game.printBoard();
      char currentPlayer = 'X'; // User starts
      boolean gameOver = false;

      String previousState = null; // To track the AI's previous state
      int previousMove = -1; // To track the AI's previous action

      while (!gameOver) {
        if (currentPlayer == 'X') { // User's turn
          System.out.println("Enter your move (1-9): ");
          int move = scanner.nextInt();

          if (game.isValidMove(move)) {
            game.placeMove(move, currentPlayer);
            game.printBoard();

            if (game.checkWin('X')) {
              System.out.println("Player X wins!");
              // Penalize AI for losing
              if (previousState != null && previousMove != -1) {
                qTable.updateQTable(previousState, previousMove, -10, game.getState());
              }
              gameOver = true;
            } else if (game.isBoardFull()) {
              System.out.println("It's a tie!");
              // Neutral reward for tie
              if (previousState != null && previousMove != -1) {
                qTable.updateQTable(previousState, previousMove, 0, game.getState());
              }
              gameOver = true;
            }

            currentPlayer = 'O'; // Switch to AI
          } else {
            System.out.println("Invalid move. Try again.");
          }
        } else { // AI's turn
          String currentState = game.getState();
          int move = ai.makeMove(game, qTable); // AI chooses a move
          System.out.println("Computer chose: " + move);

          game.placeMove(move, currentPlayer);
          game.printBoard();

          if (game.checkWin('O')) {
            System.out.println("Computer (O) wins!");
            // Reward AI for winning
            qTable.updateQTable(currentState, move, 10, game.getState());
            gameOver = true;
          } else if (game.isBoardFull()) {
            System.out.println("It's a tie!");
            // Neutral reward for tie
            qTable.updateQTable(currentState, move, 0, game.getState());
            gameOver = true;
          }

          // Save current state and move for the next iteration
          previousState = currentState;
          previousMove = move;

          currentPlayer = 'X'; // Switch back to user
        }
      }

      System.out.println("Play again? (yes/no)");
      String response = scanner.next().toLowerCase();
      playAgain = response.equals("yes");
    }

    System.out.println("Thanks for playing!");
    scanner.close();
  }
}