import java.util.Random;

public class AI {
  private final double learningRate = 0.1;
  private final double discountFactor = 0.9;
  private final double explorationRate = 0.2;
  private final Random random = new Random();

  public void trainAI(GameLogic game, QTableHandler qTable, int episodes) {
    for (int i = 0; i < episodes; i++) {
      game.resetBoard();
      boolean gameOver = false;
      char currentPlayer = 'X';

      while (!gameOver) {
        String state = game.getState();
        int action;

        if (currentPlayer == 'O') {
          if (random.nextDouble() < explorationRate) {
            action = randomMove(game);
          } else {
            action = qTable.getBestMove(state, game);
          }
          game.placeMove(action, currentPlayer);
          String newState = game.getState();
          gameOver = game.checkWin(currentPlayer) || game.isBoardFull();
          double reward = gameOver ? getReward(currentPlayer) : -0.05;
          qTable.updateQTable(state, action, reward, newState, game);
          currentPlayer = 'X';
        } else {
          action = randomMove(game);
          game.placeMove(action, currentPlayer);
          gameOver = game.checkWin(currentPlayer) || game.isBoardFull();
          currentPlayer = 'O';
        }
      }
    }
  }

  public int getBestMove(GameLogic game, QTableHandler qTable, char player) {
    String state = game.getState();
    return qTable.getBestMove(state, game);
  }

  public int randomMove(GameLogic game) {
    int move;
    do {
      move = random.nextInt(9) + 1;
    } while (!game.isValidMove(move));
    return move;
  }

  private double getReward(char player) {
    return player == 'O' ? 1 : -1;
  }
}