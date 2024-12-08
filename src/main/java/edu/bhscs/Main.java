//Structure of the Game
//This is TicTacToe(TTT)
//User is X and AI is O
//AI uses Qlearning(reinforced learning technique)
//2 main phasess: learning phase and game phase



import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class Main {
  static char[][] board = {//board holds #s 1-9 to indicate position and is what we play TTT on
    {'1', '2', '3'},
    {'4', '5', '6'},
    {'7', '8', '9'}

  };
  
  static HashMap<String, double[]> QTable = new HashMap<>();
  //QTable is essentiall a hashmap where the Keys are board states and values are arrays of QValues
  static double learningRate = 0.1;//rate that AI learns
  static double discountFactor = 0.9;//importance of future rewards compared to immediate rewards
  static double explorationRate = 0.2;//chance that AI does a dumb move
  static Random random = new Random();

  public static void main(String[] args) {//controls flow of the game, trains AI then starts game
    trainAI(10000);
    playGame();//start game
  }

  // AI explores random moves(exploration rate) or uses what it knows(QTable)
  //"rewards" are assigned for winning, losing, or tieing, which are used to update QValues
  public static void trainAI(int episodes) {
    for (int i = 0; i < episodes; i++) {
      resetBoard();
      boolean gameOver = false;
      char currentPlayer = 'X';
      while (!gameOver) {
        String state = getState();
        int action;
        if (currentPlayer == 'O') {
          if (random.nextDouble() < explorationRate) {
            action = randomMove();
          } else {
            action = bestMove(state);
          }
          placeMove(action, currentPlayer);
          String newState = getState();
          gameOver = checkWin(currentPlayer) || isBoardFull();
          double reward = gameOver ? getReward(currentPlayer) : -0.05;
          updateQTable(state, action, reward, newState);
          currentPlayer = 'X';
        } else {
          action = randomMove();
          placeMove(action, currentPlayer);
          gameOver = checkWin(currentPlayer) || isBoardFull();
          currentPlayer = 'O';
        }
      }
    }
  }

  // Plays the game between the user and the AI
  public static void playGame() {
    Scanner scanner = new Scanner(System.in);
    boolean playAgain = true;

    while (playAgain) {
      resetBoard(); // Reset the board for a new game
      printBoard(); // Display the empty board
      boolean gameOver = false;
      char currentPlayer = 'X'; // User starts

      while (!gameOver) {
        if (currentPlayer == 'X') { // User's turn
          System.out.println("Enter a move (1-9): ");
          int move = scanner.nextInt();
          if (isValidMove(move)) {
            placeMove(move, currentPlayer);
            printBoard();
            gameOver = checkWin(currentPlayer) || isBoardFull();
            currentPlayer = 'O'; // Switch to AI
          } else {
            System.out.println("Invalid move, try again.");
          }
        } else { // AI's turn
          int move = random.nextDouble() < 0.3 ? randomMove() : bestMove(getState());
          placeMove(move, currentPlayer);
          printBoard();
          gameOver = checkWin(currentPlayer) || isBoardFull();
          currentPlayer = 'X'; // Switch to user
        }

        if (gameOver) {
          if (checkWin('X')) {
            System.out.println("Player X wins!");
          } else if (checkWin('O')) {
            System.out.println("Computer (O) wins!");
          } else {
            System.out.println("It's a tie!");
          }
        }
      }

      // Ask the player if they want to play again
      System.out.println("Do you want to play again? (yes/no)");
      String response = scanner.next().trim().toLowerCase();
      playAgain = response.equals("yes");
    }
    System.out.println("Thanks for playing! Goodbye.");
    scanner.close();
  }


  public static void updateQTable(String state, int action, double reward, String newState) {
    QTable.putIfAbsent(state, new double[9]);
    QTable.putIfAbsent(newState, new double[9]);
    double[] qValues = QTable.get(state);
    double[] newQValues = QTable.get(newState);
    qValues[action - 1] = qValues[action - 1] + learningRate * (reward + discountFactor * maxQValue(newQValues) - qValues[action - 1]);
    //Calculates how much to adjust the Q-value for the given state-action pair.
    //Encourages moves that lead to higher rewards (winning).

  }

   // Looks at all Q-values for the current state and chooses the move with the highest value.
  public static int bestMove(String state) {
    QTable.putIfAbsent(state, new double[9]);
    double[] qValues = QTable.get(state);
    int bestMove = 1;
    double bestValue = qValues[0];
    for (int i = 1; i < 9; i++) {
      if (qValues[i] > bestValue && isValidMove(i + 1)) {
        bestMove = i + 1;
        bestValue = qValues[i];
      }
    }
    return bestMove;
  }

  public static int minimax(boolean isMaximizing) {
    if (checkWin('O')) return 1;
    if (checkWin('X')) return -1;
    if (isBoardFull()) return 0;

    if (isMaximizing) {
      int bestScore = Integer.MIN_VALUE;
      for (int i = 1; i <= 9; i++) {
        if (isValidMove(i)) {
          placeMove(i, 'O');
          int score = minimax(false);
          placeMove(i, (char) (i + '0'));
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
          placeMove(i, (char) (i + '0'));
          bestScore = Math.min(score, bestScore);
        }
      }
      return bestScore;
    }
  } 

  // Finds the best move using Minimax
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

  // Randomly selects a valid move
  public static int randomMove() {
    int move;
    do {
      move = random.nextInt(9) + 1;
    } while (!isValidMove(move));
    return move;
  }

  public static double maxQValue(double[] qValues) {//Returns the highest Q-value in an array.

    double max = Double.NEGATIVE_INFINITY;
    for (double q : qValues) {
      max = Math.max(max, q);
    }
    return max;
  }


  public static double getReward(char player) {
    return player == 'O' ? 1 : -1;
  }

  public static void resetBoard() {//Resets the board to its initial state.
    board = new char[][] {
      {'1', '2', '3'},
      {'4', '5', '6'},
      {'7', '8', '9'}
    };
  }

  public static String getState() {//Converts the board into a string (used as the state for Q-Learning)
    StringBuilder state = new StringBuilder();
    for (char[] row : board) {
      for (char cell : row) {
        state.append(cell);
      }
    }
    return state.toString();
  }
  
  public static void placeMove(int move, char player) {//Marks the board at the given position
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

  public static boolean isValidMove(int move) {//Checks if a move is valid 
    return board[(move - 1) / 3][(move - 1) % 3] != 'X' && board[(move - 1) / 3][(move - 1) % 3] != 'O';
  }

  public static boolean isBoardFull() {//Returns true if no more moves are possible
    for (char[] row : board) {
      for (char cell : row) {
        if (cell != 'X' && cell != 'O') return false;
      }
    }
    return true;
  }

  public static boolean checkWin(char player) {//Checks if the given player has won
    return (board[0][0] == player && board[0][1] == player && board[0][2] == player) ||
    (board[1][0] == player && board[1][1] == player && board[1][2] == player) ||
    (board[2][0] == player && board[2][1] == player && board[2][2] == player) ||
    (board[0][0] == player && board[1][0] == player && board[2][0] == player) ||
    (board[0][1] == player && board[1][1] == player && board[2][1] == player) ||
    (board[0][2] == player && board[1][2] == player && board[2][2] == player) ||
    (board[0][0] == player && board[1][1] == player && board[2][2] == player) ||
    (board[2][0] == player && board[1][1] == player && board[0][2] == player);
  }

  public static void printBoard() {//  // Prints the current state of the board
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        System.out.print(board[i][j]);
        if (j < 2) System.out.print(" | ");
      }
      if (i < 2) System.out.println("\n---------");
    }
    System.out.println();
  }
}