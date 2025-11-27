package com.jeopardyProject.Game.Command;

/**
 * Command pattern interface for encapsulating player actions.
 * <p>
 * This interface defines the contract for all game actions (category selection,
 * question selection, and answering questions). Each action implementation
 * validates preconditions, executes the action logic, and returns a result.
 * <p>
 * Implementations:
 * <ul>
 *   <li>{@link SelectCategoryAction} - Handles category selection with partial matching</li>
 *   <li>{@link SelectQuestionAction} - Handles question value selection within category</li>
 *   <li>{@link AnswerQuestionAction} - Handles answer submission and validation</li>
 * </ul>
 *
 * @see ActionType
 * @see ActionResult
 * @author COMP3607 Jeopardy Project Team
 * @version 1.0
 */
public interface Action {
    /**
     * Gets the type of this action.
     *
     * @return the ActionType enum constant identifying this action
     */
    public ActionType getType();

    /**
     * Validates whether this action can be executed given the current game state.
     * <p>
     * Typically checks preconditions like question availability or valid state.
     * </p>
     *
     * @return true if the action is valid and can execute, false otherwise
     */
    public boolean isValid();

    /**
     * Executes the action with the provided user input.
     * <p>
     * Processes the input, updates game state accordingly, and returns a result
     * indicating success or failure with an appropriate message.
     * </p>
     *
     * @param input the user input string to process
     * @return ActionResult containing success status and message
     */
    public ActionResult execute(String input);
}
