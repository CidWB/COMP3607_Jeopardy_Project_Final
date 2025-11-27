package com.jeopardyProject;

import com.jeopardyProject.Game.GameController;

/**
 * Main entry point for the Jeopardy game application.
 * <p>
 * This class serves as the bootstrap for the Jeopardy game simulation.
 * It initializes the singleton {@link GameController} and orchestrates
 * the game lifecycle through three main phases: game initialization,
 * setup (loading questions and players), and gameplay execution.
 * </p>
 *
 * @author COMP3607 Jeopardy Project Team
 * @version 1.0
 */
public class GameRunner {
    /**
     * Singleton instance of the game controller that manages the entire game flow.
     */
    private static GameController controller = GameController.getInstance();

    /**
     * Main entry point for the Jeopardy game application.
     * <p>
     * Executes the game lifecycle in sequence:
     * <ol>
     *   <li>{@code startGame()} - Initializes game session and logging</li>
     *   <li>{@code setupGame()} - Loads questions from file and registers players</li>
     *   <li>{@code playGame()} - Runs the main game loop until completion or user quits</li>
     * </ol>
     * Users can type "QUIT" at any prompt to exit the game early.
     *
     * @param args command-line arguments (not used in current implementation)
     */
    public static void main(String[] args) {
        controller.startGame();
        controller.setupGame();

        System.out.println("Game has started. Type 'QUIT' at any time to exit.");
        controller.playGame();
    }
}
