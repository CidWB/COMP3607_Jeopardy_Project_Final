package com.jeopardyProject.Game.State;

import com.jeopardyProject.Game.Command.*;

/**
 * State representing a player selecting a category.
 * <p>
 * This is the first phase of a player's turn. Only {@link ActionType#SELECT_CATEGORY}
 * actions are permitted. On successful category selection, transitions to
 * {@link SelectQuestionState}. On failure, remains in this state for retry.
 * </p>
 *
 * @see PlayerState
 * @see StateRegistry#SELECT_CATEGORY
 * @see com.jeopardyProject.Game.Command.SelectCategoryAction
 * @author COMP3607 Jeopardy Project Team
 * @version 1.0
 */
public class SelectCategoryState implements PlayerState{

    /**
     * Allows only category selection actions.
     *
     * @param action the action to validate
     * @return true if action type is SELECT_CATEGORY, false otherwise
     */
    @Override
    public boolean isAllowed(Action action){
        return action.getType() == ActionType.SELECT_CATEGORY;
    }

    /**
     * Transitions to question selection on success, stays in current state on failure.
     *
     * @param result the action result
     * @return SELECT_QUESTION state if successful, this state otherwise
     */
    @Override
    public PlayerState getNextState(ActionResult result){
        if(result.isSuccess()){
            return StateRegistry.SELECT_QUESTION;
        }
        return this;
    }

    /**
     * Gets the name of this state.
     *
     * @return "SelectCategory"
     */
    @Override
    public String getStateName(){
        return "SelectCategory";
    }
}
