package com.jeopardyProject;

import com.jeopardyProject.Game.GameController;

public class GameRunner {
    private static GameController controller = GameController.getInstance();
    
    // The actual Jeopardy program:
    public static void main(String[] args) {
        controller.startGame();
        controller.setupGame();
        
        System.out.println("Game has started. Type 'QUIT' at any time to exit.");
        controller.playGame();
        controller.exitGame();
    }
}
