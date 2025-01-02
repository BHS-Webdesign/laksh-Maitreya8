import java.util.Random;

public class AI {
  private double explorationRate = 0.3; // Initial exploration rate
  private final Random random = new Random();

  public int makeMove(GameLogic game, QTableHandler qTable) {
    // Use Q-learning strategy for AI move
    return makeMoveWithQLearning(game, qTable);
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

  public double getExplorationRate() {
    return explorationRate;
  }
}
