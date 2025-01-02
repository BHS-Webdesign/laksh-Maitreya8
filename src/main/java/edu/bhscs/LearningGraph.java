import javax.swing.*;
import java.awt.*;
import java.util.List;

public class LearningGraph extends JPanel {
  private final List<Double> winRates;
  private final List<Double> explorationRates;

  public LearningGraph(List<Double> winRates, List<Double> explorationRates) {
    this.winRates = winRates;
    this.explorationRates = explorationRates;
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;

    int width = getWidth();
    int height = getHeight();

    g2.drawLine(50, height - 50, width - 50, height - 50); // X-axis
    g2.drawLine(50, 50, 50, height - 50); // Y-axis

    drawGraphLine(g2, winRates, Color.BLUE, width, height, "Win Rate");
    drawGraphLine(g2, explorationRates, Color.RED, width, height, "Exploration Rate");
  }

  private void drawGraphLine(Graphics2D g2, List<Double> data, Color color, int width, int height, String label) {
    g2.setColor(color);
    int graphWidth = width - 100;
    int graphHeight = height - 100;

    for (int i = 0; i < data.size() - 1; i++) {
      int x1 = 50 + (i * graphWidth) / data.size();
      int y1 = height - 50 - (int) (data.get(i) * graphHeight / 100);
      int x2 = 50 + ((i + 1) * graphWidth) / data.size();
      int y2 = height - 50 - (int) (data.get(i + 1) * graphHeight / 100);

      g2.drawLine(x1, y1, x2, y2);
    }

    g2.drawString(label, width - 100, 50);
  }

  public static void displayGraph(List<Double> winRates, List<Double> explorationRates) {
    JFrame frame = new JFrame("AI Learning Graph");
    LearningGraph graph = new LearningGraph(winRates, explorationRates);
    frame.add(graph);
    frame.setSize(800, 600);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
  }
}
