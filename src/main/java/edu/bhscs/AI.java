import java.util.Random;

public class AI {
  private double explorationRate = 0.2; // Start with high exploration
  private final double learningRate = 0.1;
  private final double discountFactor = 0.9;
  private final Random random = new Random();

  public int makeMove(GameLogic game, QTableHandler qTable) {
    String state = game.getState();
    qTable.initState(state);

    int move;
    if (random.nextDouble() < explorationRate) {
      move = game.randomMove();
    } else {
      move = bestMove(state, qTable, game);
    }

    // Decay exploration rate gradually
    if (explorationRate > 0.05) {
      explorationRate *= 0.99;
    }

    return move;
  }

  private int bestMove(String state, QTableHandler qTable, GameLogic game) {
    double[] qValues = qTable.getQValues(state);
    int bestMove = -1;
    double bestValue = Double.NEGATIVE_INFINITY;

    for (int i = 0; i < qValues.length; i++) {
      if (qValues[i] > bestValue && game.isValidMove(i + 1)) {
        bestMove = i + 1;
        bestValue = qValues[i];
      }
    }

    // If no valid move found (edge case), pick random
    if (bestMove == -1) {
      bestMove = game.randomMove();
    }
    return bestMove;
  }
}