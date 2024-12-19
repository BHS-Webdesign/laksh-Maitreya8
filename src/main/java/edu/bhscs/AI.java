import java.util.Random;

public class AI {
  private double explorationRate = 0.3; // Initial exploration rate
  private final Random random = new Random();
  private int gamesPlayed = 0; // Track the number of games played
  private boolean useMinimax = false; // Toggle for Minimax

  // Determines the AI's next move
  public int makeMove(GameLogic game, QTableHandler qTable) {
    gamesPlayed++; // Increment game count

    if (useMinimax) {
      System.out.println("AI is using Minimax!");
      return getBestMoveWithMinimax(game);
    } else {
      // Use Q-learning strategy
      return makeMoveWithQLearning(game, qTable);
    }
  }

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

  private int getRandomValidMove(GameLogic game) {
    int move;
    do {
      move = random.nextInt(9) + 1; // Pick a move between 1-9
    } while (!game.isValidMove(move));
    return move;
  }

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

  private int getBestMoveWithMinimax(GameLogic game) {
    int bestMove = -1;
    int bestScore = Integer.MIN_VALUE;

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

    // Return -1 if no move is valid (edge case)
    return bestMove != -1 ? bestMove : getRandomValidMove(game);
  }

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

  // Toggles Minimax on or off
  public void setUseMinimax(boolean useMinimax) {
    this.useMinimax = useMinimax;
  }

  // Returns whether Minimax is enabled
  public boolean isUsingMinimax() {
    return useMinimax;
  }
}