import java.util.ArrayList;
import java.util.List;

public class GameTracker {
  private int gamesPlayed = 0;
  private int wins = 0;
  private List<Double> winRates = new ArrayList<>();
  private List<Double> explorationRates = new ArrayList<>();

  // Records the result of a game
  public void recordGame(boolean aiWon, boolean playerWon, double explorationRate) {
    gamesPlayed++;
    if (aiWon) {
      wins++;
    }
    // Calculate win rate (percentage) and store it
    winRates.add((wins * 100.0) / gamesPlayed);

    // Track exploration rate over time
    explorationRates.add(explorationRate);
  }

  // Getter for win rates over time
  public List<Double> getWinRates() {
    return winRates;
  }

  // Getter for exploration rates over time
  public List<Double> getExplorationRates() {
    return explorationRates;
  }
}