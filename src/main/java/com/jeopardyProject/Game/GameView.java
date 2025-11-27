package com.jeopardyProject.Game;

import com.jeopardyProject.Game.Command.ActionResult;

/**
 * View layer for rendering game information to the console.
 * <p>
 * This class encapsulates all console output operations, providing a clean
 * separation between game logic ({@link GameController}) and presentation.
 * It handles display of messages, errors, prompts, questions, scores, and
 * the question grid.
 * </p>
 *
 * @author COMP3607 Jeopardy Project Team
 * @version 1.0
 */
public class GameView {

    /**
     * Displays a standard informational message to the console.
     *
     * @param message the message text to display
     */
    public void displayMessage(String message){
        System.out.println(message);
    }

    /**
     * Displays an error message to the standard error stream.
     *
     * @param error the error message text to display
     */
    public void displayError(String error){
        System.err.println(error);
    }

    /**
     * Displays the result of a player action (success or failure).
     * <p>
     * Currently outputs the message regardless of success status.
     * Future enhancement could differentiate formatting based on result type.
     * </p>
     *
     * @param result the ActionResult containing success status and message
     * @see ActionResult
     */
    public void displayActionResult(ActionResult result){
        if(result.isSuccess()){
            System.out.println(result.getMessage());
        } else {
            System.out.println(result.getMessage());
        }
    }

    /**
     * Displays the formatted question grid showing all available questions.
     * <p>
     * Delegates to {@link QuestionList#display()} to render the grid
     * organized by category and value, marking answered questions.
     * </p>
     *
     * @param questions the QuestionList to display
     * @see QuestionList#display()
     */
    public void displayQuestionGrid(QuestionList questions){
        questions.display();
    }

    /**
     * Displays a question with its content and multiple-choice options.
     * <p>
     * Format:
     * <pre>
     * The question is: [question content]
     * Select an option from the following:
     * [options A-D]
     * </pre>
     *
     * @param question the Question object to display
     * @see Question#printOptions()
     */
    public void displayQuestion(Question question){
        System.out.println("The question is: " + question.getContent());
        System.out.println("Select an option from the following:");
        question.printOptions();
    }

    /**
     * Displays feedback on answer correctness.
     * <p>
     * Shows "Correct!" for right answers or "Not quite." for wrong answers.
     * Note: score parameter is currently unused but available for future enhancements.
     * </p>
     *
     * @param playerId the ID of the player who answered (currently unused)
     * @param score the player's new score (currently unused)
     * @param correct true if the answer was correct, false otherwise
     */
    public void displayScore(String playerId, int score, boolean correct){
        if(correct){
            System.out.println("Correct!");
        } else {
            System.out.println("Not quite.");
        }
    }

    /**
     * Displays the ID of the player whose turn is currently active.
     *
     * @param playerId the player identifier to display
     */
    public void displayCurrentPlayer(String playerId){
        System.out.println("Current player: " + playerId);
    }

    /**
     * Displays a prompt requesting user input.
     *
     * @param prompt the prompt text to display
     */
    public void displayPrompt(String prompt){
        System.out.println(prompt);
    }
}
