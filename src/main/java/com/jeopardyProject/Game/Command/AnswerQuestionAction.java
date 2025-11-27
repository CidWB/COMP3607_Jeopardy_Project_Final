package com.jeopardyProject.Game.Command;

import com.jeopardyProject.Game.GameController;
import com.jeopardyProject.Game.Question;

/**
 * Command implementation for submitting an answer to a question.
 * <p>
 * This action validates the player's answer against the correct answer for
 * the currently selected question. Performs case-insensitive comparison and
 * marks the question as answered if correct.
 * <p>
 * Answer Format: Single letter (A, B, C, or D) matching the correct option key.
 *
 * @see Action
 * @see Question#getRightAnswer()
 * @author COMP3607 Jeopardy Project Team
 * @version 1.0
 */
public class AnswerQuestionAction implements Action{
    /** Reference to game controller for accessing the current question. */
    private final GameController controller;

    /**
     * Constructs an AnswerQuestionAction with the given controller.
     *
     * @param controller the GameController managing game state
     */
    public AnswerQuestionAction(GameController controller){
        this.controller = controller;
    }

    /**
     * Returns the action type for answering a question.
     *
     * @return ActionType.ANSWER_QUESTION
     */
    @Override
    public ActionType getType(){
        return ActionType.ANSWER_QUESTION;
    }

    /**
     * Validates that a question has been selected and is ready to be answered.
     *
     * @return true if a question is set, false otherwise
     */
    @Override
    public boolean isValid(){
        return controller.getQuestion() != null;
    }

    /**
     * Executes answer validation and marks question as answered if correct.
     * <p>
     * Compares the player's input against the correct answer (case-insensitive).
     * If correct, marks the question as answered to prevent re-selection.
     *
     * @param input the answer option entered by the player (e.g., "A", "B", "C", "D")
     * @return ActionResult.success("Correct!") if answer matches,
     *         ActionResult.failure("Incorrect.") otherwise
     */
    @Override
    public ActionResult execute(String input){
        if(input == null){
            return ActionResult.failure("Input cannot be null");
        }

        Question question = controller.getQuestion();
        if(question == null){
            return ActionResult.failure("No question set");
        }

        boolean correct = input.equalsIgnoreCase(question.getRightAnswer());
        if(correct){
            question.setIsAnswered(true);
            return ActionResult.success("Correct!");
        }
        return ActionResult.failure("Incorrect.");
    }
}
