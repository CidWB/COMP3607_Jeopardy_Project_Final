package com.jeopardyProject.Game.Command;

import java.util.ArrayList;

import com.jeopardyProject.Game.GameController;
import com.jeopardyProject.Game.Question;

/**
 * Command implementation for question value selection within a category.
 * <p>
 * After a player selects a category, this action handles the selection of a
 * specific question value (e.g., 100, 200, 300, 400, 500) from that category.
 * Validates that the question exists, is within the selected category, and has
 * not already been answered.
 *
 * @see Action
 * @see GameController#setQuestion(com.jeopardyProject.Game.Question)
 * @author COMP3607 Jeopardy Project Team
 * @version 1.0
 */
public class SelectQuestionAction implements Action{
    /** Reference to game controller for accessing state and updating selection. */
    private final GameController controller;

    /**
     * Constructs a SelectQuestionAction with the given controller.
     *
     * @param controller the GameController managing game state
     */
    public SelectQuestionAction(GameController controller){
        this.controller = controller;
    }

    /**
     * Returns the action type for question selection.
     *
     * @return ActionType.SELECT_QUESTION
     */
    @Override
    public ActionType getType(){
        return ActionType.SELECT_QUESTION;
    }

    /**
     * Validates that questions are loaded and a category has been selected.
     *
     * @return true if both questions and category exist, false otherwise
     */
    @Override
    public boolean isValid(){
        return controller.getQuestions() != null && controller.getCategory() != null;
    }

    /**
     * Executes question value selection within the current category.
     * <p>
     * Validation Steps:
     * <ol>
     *   <li>Parse input as integer value</li>
     *   <li>Search for question matching value in selected category</li>
     *   <li>Check if question has already been answered</li>
     *   <li>Set question in controller if valid</li>
     * </ol>
     *
     * @param input the question value entered by the player (e.g., "200")
     * @return ActionResult.success if valid unanswered question found,
     *         ActionResult.failure for invalid/answered questions or parse errors
     */
    @Override
    public ActionResult execute(String input){
        if(input == null){
            return ActionResult.failure("Input cannot be null");
        }
        if(controller.getQuestions() == null){
            return ActionResult.failure("No questions available");
        }

        String category = controller.getCategory();
        if(category == null){
            return ActionResult.failure("No category selected");
        }

        ArrayList<Question> sortedQuestions = controller.getQuestions().getQuestionsByCategory(category);
        try{
            int value = Integer.parseInt(input);
            for (Question question : sortedQuestions){
                if (value == question.getValue()){
                    if(question.getIsAnswered()){
                        return ActionResult.failure("Question already answered. Please select another question.");
                    }
                    controller.setQuestion(question);
                    return ActionResult.success("Value selected: " + input);
                }
            }
            return ActionResult.failure("Invalid value for selected category. Please try again.");
        } catch (NumberFormatException e){
            return ActionResult.failure("Invalid value entered.");
        }
    }
}
