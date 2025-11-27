package com.jeopardyProject.Game.State;

import com.jeopardyProject.Game.Command.*;

/**
 * State representing a player who is waiting for their turn.
 * <p>
 * This is the default state for players when it's not their active turn.
 * All actions are blocked in this state, preventing players from acting
 * out of turn. Players transition out of this state when {@link com.jeopardyProject.Game.PlayerList}
 * activates them for their turn.
 * </p>
 *
 * @see PlayerState
 * @see StateRegistry#WAITING
 * @author COMP3607 Jeopardy Project Team
 * @version 1.0
 */
public class WaitingState implements PlayerState{

    /**
     * Blocks all actions in the waiting state.
     *
     * @param action the action to validate (ignored)
     * @return always false - no actions are allowed while waiting
     */
    @Override
    public boolean isAllowed(Action action){
        return false;
    }

    /**
     * Remains in waiting state regardless of result.
     * <p>
     * Players can only leave waiting state through explicit state
     * assignment by {@link com.jeopardyProject.Game.PlayerList}.
     * </p>
     *
     * @param result the action result (ignored)
     * @return this state (WAITING)
     */
    @Override
    public PlayerState getNextState(ActionResult result){
        return this;
    }

    /**
     * Gets the name of this state.
     *
     * @return "Waiting"
     */
    @Override
    public String getStateName(){
        return "Waiting";
    }
}
