import java.util.Random;

public class AI {
  private double explorationRate = 0.3; // Initial exploration rate
  // How often the AI picks a random move instead of the best-known move 
  private final Random random = new Random();
  private int gamesPlayed = 0; // Track the number of games played

  // Determines the AI’s move based on the current number of games played
  public int makeMove(GameLogic game, QTableHandler qTable) {
    gamesPlayed++; // Increment game count

    // After 10 games, switch to Minimax for optimal play
    if (gamesPlayed > 10) {
      System.out.println("AI is now using Minimax!");
      return getBestMoveWithMinimax(game);
    } else {
      // Use Q-learning strategy
      return makeMoveWithQLearning(game, qTable);
    }
  }

  // Implements the Q-Learning strategy to decide the AI’s move
  private int makeMoveWithQLearning(GameLogic game, QTableHandler qTable) {
    String state = game.getState();
    qTable.initState(state);

    int move;
    if (random.nextDouble() < explorationRate) {
      // Exploration: pick a random valid move
      move = getRandomValidMove(game);
    } else {
      // Exploitation: pick the best move from Q-table
      move = getBestMoveFromQTable(state, qTable, game);
    }

    // Decay exploration rate over time
    if (explorationRate > 0.05) {
      explorationRate *= 0.99;
    }

    return move;
  }

  // Picks a random valid move for the AI to play
  private int getRandomValidMove(GameLogic game) {
    int move;
    do {
      move = random.nextInt(9) + 1; // Pick a move between 1-9
    } while (!game.isValidMove(move));
    return move;
  }

  // Selects the best move based on the Q-values for the current board state
  private int getBestMoveFromQTable(String state, QTableHandler qTable, GameLogic game) {
    double[] qValues = qTable.getQValues(state);
    int bestMove = -1;
    double bestValue = Double.NEGATIVE_INFINITY;

    for (int i = 0; i < qValues.length; i++) {
      if (game.isValidMove(i + 1) && qValues[i] > bestValue) {
        bestMove = i + 1;
        bestValue = qValues[i];
      }
    }

    // Fallback to random move if no good move is found
    if (bestMove == -1) {
      bestMove = getRandomValidMove(game);
    }
    return bestMove;
  }

  // Implements the Minimax algorithm to determine the best possible move
  private int getBestMoveWithMinimax(GameLogic game) {
    int bestMove = -1;
    int bestScore = Integer.MIN_VALUE;

    // Iterate over all possible moves
    for (int i = 1; i <= 9; i++) {
      if (game.isValidMove(i)) {
        game.placeMove(i, 'O'); // Make the move for AI
        int score = minimax(game, false); // Call Minimax for opponent
        game.undoMove(i); // Undo the move

        if (score > bestScore) {
          bestScore = score;
          bestMove = i;
        }
      }
    }
    return bestMove;
  }

  // Recursively evaluates moves using the Minimax algorithm
  private int minimax(GameLogic game, boolean isMaximizing) {
    if (game.checkWin('O')) return 10; // AI wins
    if (game.checkWin('X')) return -10; // Player wins
    if (game.isBoardFull()) return 0; // Tie

    if (isMaximizing) {
      int bestScore = Integer.MIN_VALUE;
      for (int i = 1; i <= 9; i++) {
        if (game.isValidMove(i)) {
          game.placeMove(i, 'O');
          int score = minimax(game, false);
          game.undoMove(i);
          bestScore = Math.max(bestScore, score);
        }
      }
      return bestScore;
    } else {
      int bestScore = Integer.MAX_VALUE;
      for (int i = 1; i <= 9; i++) {
        if (game.isValidMove(i)) {
          game.placeMove(i, 'X');
          int score = minimax(game, true);
          game.undoMove(i);
          bestScore = Math.min(bestScore, score);
        }
      }
      return bestScore;
    }
  }
}
