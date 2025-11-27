package com.jeopardyProject.Game.State;

import com.jeopardyProject.Game.Command.*;

/**
 * State representing a player selecting a question value.
 * <p>
 * This is the second phase of a player's turn (after category selection).
 * Only {@link ActionType#SELECT_QUESTION} actions are permitted. On successful
 * question selection, transitions to {@link AnswerQuestionState}. On failure
 * (e.g., invalid value, already answered), remains in this state for retry.
 * </p>
 *
 * @see PlayerState
 * @see StateRegistry#SELECT_QUESTION
 * @see com.jeopardyProject.Game.Command.SelectQuestionAction
 * @author COMP3607 Jeopardy Project Team
 * @version 1.0
 */
public class SelectQuestionState implements PlayerState{

    /**
     * Allows only question selection actions.
     *
     * @param action the action to validate
     * @return true if action type is SELECT_QUESTION, false otherwise
     */
    @Override
    public boolean isAllowed(Action action){
        return action.getType() == ActionType.SELECT_QUESTION;
    }

    /**
     * Transitions to answer submission on success, stays in current state on failure.
     *
     * @param result the action result
     * @return ANSWER_QUESTION state if successful, this state otherwise
     */
    @Override
    public PlayerState getNextState(ActionResult result){
        if(result.isSuccess()){
            return StateRegistry.ANSWER_QUESTION;
        }
        return this;
    }

    /**
     * Gets the name of this state.
     *
     * @return "SelectQuestion"
     */
    @Override
    public String getStateName(){
        return "SelectQuestion";
    }
}
