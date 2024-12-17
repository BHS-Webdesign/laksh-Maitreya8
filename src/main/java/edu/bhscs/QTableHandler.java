import java.util.HashMap;

public class QTableHandler {
  private final HashMap<String, double[]> qTable = new HashMap<>();
  private final double learningRate = 0.1;
  private final double discountFactor = 0.9;

  public void initState(String state) {
    qTable.putIfAbsent(state, new double[9]);
  }

  public double[] getQValues(String state) {
    return qTable.getOrDefault(state, new double[9]);
  }

  public void updateQTable(String state, int action, double reward, String newState) {
    initState(state);
    initState(newState);

    double[] qValues = qTable.get(state);
    double[] newQValues = qTable.get(newState);

    qValues[action - 1] += learningRate * (reward + discountFactor * maxQValue(newQValues) - qValues[action - 1]);
  }

  public void updateQTable(String state, double reward) {
    initState(state);
    double[] qValues = qTable.get(state);

    for (int i = 0; i < qValues.length; i++) {
      qValues[i] += learningRate * (reward - qValues[i]);
    }
  }

  private double maxQValue(double[] qValues) {
    double max = Double.NEGATIVE_INFINITY;
    for (double q : qValues) {
      max = Math.max(max, q);
    }
    return max;
  }
}
