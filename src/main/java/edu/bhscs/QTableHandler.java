import java.util.HashMap;

public class QTableHandler {
  private final HashMap<String, double[]> qTable = new HashMap<>();
  private final double learningRate = 0.5;
  private final double discountFactor = 0.9;

  // Overloaded version: Updates Q-values for mid-game moves
  public void updateQTable(String state, int action, double reward, String newState) {
    initState(state);
    initState(newState);

    double[] qValues = qTable.get(state);
    double[] newQValues = qTable.get(newState);

    // Q-learning update formula
    qValues[action - 1] += learningRate * 
      (reward + discountFactor * maxQValue(newQValues) - qValues[action - 1]);
  }

    // Overloaded version: Updates Q-values for final states (win, loss, tie)
  public void updateQTable(String state, double reward) {
    initState(state);
    double[] qValues = qTable.get(state);

    for (int i = 0; i < qValues.length; i++) {
      qValues[i] += learningRate * (reward - qValues[i]);
    }
  
  }

  // Initialize state if it's not in the Q-table
  public void initState(String state) {
    qTable.putIfAbsent(state, new double[9]);
  }

  // Get Q-values for a state
  public double[] getQValues(String state) {
    return qTable.getOrDefault(state, new double[9]);
  }

  // Find the maximum Q-value for a state
  private double maxQValue(double[] qValues) {
    double max = Double.NEGATIVE_INFINITY;
    for (double q : qValues) {
      max = Math.max(max, q);
    }
    return max;
  }
}
