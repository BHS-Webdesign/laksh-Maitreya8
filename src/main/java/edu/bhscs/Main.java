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
      System.out.println("New game started!");
      game.printBoard(); // Display the board

      char currentPlayer = 'X'; // User starts
      boolean gameOver = false;

      while (!gameOver) {
        if (currentPlayer == 'X') { // User's turn
          System.out.println("Enter your move (1-9): ");
          int move = scanner.nextInt();
          if (game.isValidMove(move)) {
            game.placeMove(move, currentPlayer);
            game.printBoard(); // Show updated board

            if (game.checkWin('X')) {
              System.out.println("Player X wins!");
              gameOver = true;
            } else if (game.isBoardFull()) {
              System.out.println("It's a tie!");
              gameOver = true;
            }
            currentPlayer = 'O'; // Switch to AI
          } else {
            System.out.println("Invalid move. Try again.");
          }
        } else { // AI's turn
          int move = ai.makeMove(game, qTable);
          System.out.println("Computer chose: " + move);
          game.placeMove(move, currentPlayer);
          game.printBoard(); // Show updated board

          if (game.checkWin('O')) {
            System.out.println("Computer (O) wins!");
            gameOver = true;
          } else if (game.isBoardFull()) {
            System.out.println("It's a tie!");
            gameOver = true;
          }
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