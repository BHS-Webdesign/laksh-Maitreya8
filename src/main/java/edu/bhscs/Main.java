//Structure of the Game
//This is TicTacToe(TTT)
//User is X and AI is O
//AI uses Qlearning(reinforced learning technique)
//2 main phasess: learning phase and game phase



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

    while (!gameOver) {
      if (currentPlayer == 'X') { // User's turn
        System.out.println("Enter your move (1-9): ");
        int move = scanner.nextInt();
        if (game.isValidMove(move)) {
          game.placeMove(move, currentPlayer);
          game.printBoard();
          if (game.checkWin('X')) {
            System.out.println("Player X wins!");
            qTable.updateQTable(game.getState(), -1); // Negative reward for AI
            gameOver = true;
          } else if (game.isBoardFull()) {
            System.out.println("It's a tie!");
            qTable.updateQTable(game.getState(), 0); // Neutral reward
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
        game.printBoard();

        if (game.checkWin('O')) {
          System.out.println("Computer (O) wins!");
          qTable.updateQTable(game.getState(), 1); // Positive reward for AI
          gameOver = true;
        } else if (game.isBoardFull()) {
          System.out.println("It's a tie!");
          qTable.updateQTable(game.getState(), 0); // Neutral reward
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
