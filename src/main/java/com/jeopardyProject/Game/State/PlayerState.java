package com.jeopardyProject.Game.State;

import com.jeopardyProject.Game.Command.*;

/**
 * State pattern interface defining player turn states.
 * <p>
 * This interface encapsulates the state-based behavior for controlling which
 * actions a player can perform during their turn. Each state implementation
 * determines:
 * <ul>
 *   <li>Which actions are permitted in this state</li>
 *   <li>Which state to transition to based on action results</li>
 * </ul>
 * <p>
 * State Transition Flow:
 * <pre>
 * WaitingState → SelectCategoryState → SelectQuestionState → AnswerQuestionState → WaitingState
 * </pre>
 *
 * @see StateRegistry
 * @see com.jeopardyProject.Game.Player
 * @author COMP3607 Jeopardy Project Team
 * @version 1.0
 */
public interface PlayerState {
    /**
     * Determines if the given action is allowed in this state.
     *
     * @param action the Action to validate
     * @return true if the action can execute in this state, false otherwise
     */
    public boolean isAllowed(Action action);

    /**
     * Determines the next state based on the action result.
     * <p>
     * Typically transitions to the next state on success, or remains
     * in the current state on failure.
     * </p>
     *
     * @param result the ActionResult from executing an action
     * @return the PlayerState to transition to
     */
    public PlayerState getNextState(ActionResult result);

    /**
     * Gets the name of this state for identification/debugging.
     *
     * @return the state name string
     */
    public String getStateName();
}
