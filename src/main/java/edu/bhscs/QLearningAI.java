import java.io.*;
import java.util.*;
import java.io.FileWriter;
import org.json.JSONObject;
import org.json.JSONTokener;

public class QLearningAI {
    private static final double LEARNING_RATE = 0.1;
    private static final double DISCOUNT_FACTOR = 0.9;
    private static final double EXPLORATION_RATE = 0.2;
    private Map<String, double[]> qTable;
    private String filePath;

    public QLearningAI(String filePath) {
        this.filePath = filePath;
        qTable = new HashMap<>();
        loadQTable();
    }

    public int[] getBestMove(char[][] board) {
        String state = boardToString(board);
        double[] qValues = qTable.getOrDefault(state, new double[9]);

        if (Math.random() < EXPLORATION_RATE) {
            return getRandomMove(board);
        }

        int bestMove = -1;
        double bestValue = Double.NEGATIVE_INFINITY;
        for (int i = 0; i < 9; i++) {
            int row = i / 3, col = i % 3;
            if (board[row][col] == '-') {
                if (qValues[i] > bestValue) {
                    bestValue = qValues[i];
                    bestMove = i;
                }
            }
        }
        return bestMove == -1 ? getRandomMove(board) : new int[]{bestMove / 3, bestMove % 3};
    }

    public void learnFromGame(char[][] board, char winner) {
        String state = boardToString(board);
        double reward = (winner == 'O') ? 1 : (winner == 'X') ? -1 : 0;
        double[] qValues = qTable.getOrDefault(state, new double[9]);

        for (int i = 0; i < 9; i++) {
            if (board[i / 3][i % 3] == 'O') {
                qValues[i] = qValues[i] + LEARNING_RATE * (reward + DISCOUNT_FACTOR * maxQValue(board) - qValues[i]);
            }
        }
        qTable.put(state, qValues);
    }

    private double maxQValue(char[][] board) {
        String state = boardToString(board);
        double[] qValues = qTable.getOrDefault(state, new double[9]);
        double max = Double.NEGATIVE_INFINITY;
        for (double value : qValues) {
          max = Math.max(max, value);
        }
      return max;
    }

    private String boardToString(char[][] board) {
        StringBuilder sb = new StringBuilder();
        for (char[] row : board) for (char cell : row) sb.append(cell);
        return sb.toString();
    }

    private int[] getRandomMove(char[][] board) {
        List<int[]> moves = new ArrayList<>();
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (board[i][j] == '-') moves.add(new int[]{i, j});
        return moves.get(new Random().nextInt(moves.size()));
    }

    public void saveQTable() {
      try (FileWriter file = new FileWriter(filePath)) {
        JSONObject json = new JSONObject(qTable);

        file.write(json.toString(4));
        System.out.println("AI knowledge saved as JSON. ");

      } catch (IOException e) {
        System.err.println("Error saving Q-table. ");
      }
    }
        

    @SuppressWarnings("unchecked")
    private void loadQTable() {
        try (FileReader reader = new FileReader(filePath)) {
            JSONObject json = new JSONObject(new JSONTokener(reader));
            qTable = json.toMap();
            System.out.println("AI knowledge loaded from JSON. ");
        } catch (IOException e) {
            qTable = new HashMap<>();
        }
    }
}
