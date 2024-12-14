import java.util.HashMap;

public class QTableHandler {
  private final HashMap<String, double[]> QTable = new HashMap<>();

  public void updateQTable(String state, int action, double reward, String newState, GameLogic game) {
    QTable.putIfAbsent(state, new double[9]);
    QTable.putIfAbsent(newState, new double[9]);
    double[] qValues = QTable.get(state);
    double[] newQValues = QTable.get(newState);

    qValues[action - 1] = qValues[action - 1] + 0.1 * (reward + 0.9 * maxQValue(newQValues) - qValues[action - 1]);
  }

  public int getBestMove(String state, GameLogic game) {
    QTable.putIfAbsent(state, new double[9]);
    double[] qValues = QTable.get(state);
    int bestMove = 1;
    double bestValue = qValues[0];

    for (int i = 1; i < 9; i++) {
      if (qValues[i] > bestValue && game.isValidMove(i + 1)) {
        bestMove = i + 1;
        bestValue = qValues[i];
      }
    }
    return bestMove;
  }

  private double maxQValue(double[] qValues) {
    double max = Double.NEGATIVE_INFINITY;
    for (double q : qValues) {
      max = Math.max(max, q);
    }
    return max;
  }
}
