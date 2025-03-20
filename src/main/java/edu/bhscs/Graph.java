public class Graph {
  public void display(int aiWins, int gamesPlayed) {
      int percentage = (int) ((double) aiWins / gamesPlayed * 20);
      System.out.println("AI Win Rate:");
      System.out.println("[" + "#".repeat(percentage) + "-".repeat(20 - percentage) + "] " + (aiWins * 100 / gamesPlayed) + "%");
  }
}
