import java.util.HashMap;

public class QTableHandler {
  private final HashMap<String, double[]> qTable = new HashMap<>();
  private final double learningRate = 0.5;
  private final double discountFactor = 0.9;

  // This method updates the Q-value for a specific action taken in a given state. It considers both the immediate reward and the potential for future rewards based on the next state.
  public void updateQTable(String state, int action, double reward, String newState) {
    initState(state);
    initState(newState);

    double[] qValues = qTable.get(state);
    double[] newQValues = qTable.get(newState);

    // Q-learning update formula
    qValues[action - 1] += learningRate * 
      (reward + discountFactor * maxQValue(newQValues) - qValues[action - 1]);
  }

  // This method updates the Q-values for a terminal state (e.g., when the game ends with a win, loss, or tie). Unlike the first method, no future states are considered since the game is over.
  public void updateQTable(String state, double reward) {
    initState(state);
    double[] qValues = qTable.get(state);

    for (int i = 0; i < qValues.length; i++) {
      qValues[i] += learningRate * (reward - qValues[i]);
    }
  
  }

  // Initializes a new state in the Q-table if it doesn’t already exist.
  public void initState(String state) {
    qTable.putIfAbsent(state, new double[9]);
  }

  // Retrieves the Q-values for a given state from the Q-table.
  public double[] getQValues(String state) {
    return qTable.getOrDefault(state, new double[9]);
  }

  // Finds the highest Q-value in a given array of Q-values.
  private double maxQValue(double[] qValues) {
    double max = Double.NEGATIVE_INFINITY;
    for (double q : qValues) {
      max = Math.max(max, q);
    }
    return max;
  }
}
