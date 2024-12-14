//Structure of the Game
//This is TicTacToe(TTT)
//User is X and AI is O
//AI uses Qlearning(reinforced learning technique)
//2 main phasess: learning phase and game phase



import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    GameLogic game = new GameLogic();
    AI ai = new AI();
    QTableHandler qTableHandler = new QTableHandler();

    // Train the AI
    System.out.println("Training AI...");
    ai.trainAI(game, qTableHandler, 10000);
    System.out.println("Training completed!");

    // Start the game
    Scanner scanner = new Scanner(System.in);
    boolean playAgain = true;

    while (playAgain) {
      game.resetBoard();
      game.printBoard();
      char currentPlayer = 'X';
      boolean gameOver = false;

      while (!gameOver) {
        if (currentPlayer == 'X') {
          System.out.print("Enter a move (1-9): ");
          int move = scanner.nextInt();
          if (game.isValidMove(move)) {
            game.placeMove(move, currentPlayer);
            game.printBoard();
            if (game.checkWin(currentPlayer)) {
              System.out.println("Player X wins!");
              gameOver = true;
            } else if (game.isBoardFull()) {
              System.out.println("It's a tie!");
              gameOver = true;
            }
            currentPlayer = 'O';
          } else {
            System.out.println("Invalid move, try again.");
          }
        } else {
          int move = ai.getBestMove(game, qTableHandler, currentPlayer);
          game.placeMove(move, currentPlayer);
          game.printBoard();
          if (game.checkWin(currentPlayer)) {
            System.out.println("Computer (O) wins!");
            gameOver = true;
          } else if (game.isBoardFull()) {
            System.out.println("It's a tie!");
            gameOver = true;
          }
          currentPlayer = 'X';
        }
      }

      System.out.print("Do you want to play again? (yes/no): ");
      playAgain = scanner.next().trim().equalsIgnoreCase("yes");
    }
    System.out.println("Thanks for playing!");
    scanner.close();
  }
}
