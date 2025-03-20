import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Game game = new Game();
        QLearningAI ai = new QLearningAI("qtable.txt");
        Graph graph = new Graph();

        int gamesPlayed = 0;
        int aiWins = 0;

        while (true) {
            game.reset();
            System.out.println("New Game! You are 'X'. AI is 'O'.");

            if (gamesPlayed % 2 == 1) game.aiMove(ai);

            while (!game.isGameOver()) {
                game.printBoard();
                game.playerMove(scanner);
                if (game.isGameOver()) break;
                game.aiMove(ai);
            }

            game.printBoard();
            char winner = game.getWinner();
            if (winner == 'O') {
                aiWins++;
                System.out.println("AI wins!");
            } else if (winner == 'X') {
                System.out.println("You win!");
            } else {
                System.out.println("It's a draw!");
            }

            ai.learnFromGame(game.getBoard(), winner);
            gamesPlayed++;

            graph.display(aiWins, gamesPlayed);

            System.out.print("Play again? (y/n): ");
            if (!scanner.next().equalsIgnoreCase("y")) break;
        }

        ai.saveQTable();
        scanner.close();
        System.out.println("Game Over. AI knowledge saved.");
    }
}