package com.jeopardyProject.Game.Command;

import java.util.ArrayList;

import com.jeopardyProject.Game.GameController;
import com.jeopardyProject.Game.Question;

/**
 * Command implementation for category selection action.
 * <p>
 * This action allows players to select a category by entering the full name
 * or just the first word. Uses <b>partial matching</b> to find categories
 * that start with the user's input (case-insensitive).
 * <p>
 * Example: Input "VARIABLES" matches "Variables &amp; Data Types"
 *
 * @see Action
 * @see GameController#setCategory(String)
 * @author COMP3607 Jeopardy Project Team
 * @version 1.0
 */
public class SelectCategoryAction implements Action{
    /** Reference to game controller for accessing questions and updating state. */
    private final GameController controller;

    /**
     * Constructs a SelectCategoryAction with the given controller.
     *
     * @param controller the GameController managing game state
     */
    public SelectCategoryAction(GameController controller){
        this.controller = controller;
    }

    /**
     * Returns the action type for category selection.
     *
     * @return ActionType.SELECT_CATEGORY
     */
    @Override
    public ActionType getType(){
        return ActionType.SELECT_CATEGORY;
    }

    /**
     * Validates that questions are loaded and available.
     *
     * @return true if question list exists and is not empty, false otherwise
     */
    @Override
    public boolean isValid(){
        return controller.getQuestions() != null &&
               !controller.getQuestions().getQuestionArray().isEmpty();
    }

    /**
     * Executes category selection using partial matching.
     * <p>
     * Searches through all questions to find the first category that starts with
     * the provided input (case-insensitive). If found, sets the category in the
     * controller and returns success.
     *
     * @param input the category name or prefix entered by the player
     * @return ActionResult.success if category found, ActionResult.failure otherwise
     */
    @Override
    public ActionResult execute(String input){
        if(input == null){
            return ActionResult.failure("Input cannot be null");
        }
        if(controller.getQuestions() == null){
            return ActionResult.failure("No questions available");
        }

        ArrayList<Question> questions = controller.getQuestions().getQuestionArray();
        for (Question question : questions){
            String category = question.getCategory().toUpperCase();
            if (category.startsWith(input)){
                controller.setCategory(question.getCategory());
                return ActionResult.success("Category selected: " + category.toLowerCase());
            }
        }
        return ActionResult.failure("Invalid category selection. Please try again.");
    }
}
