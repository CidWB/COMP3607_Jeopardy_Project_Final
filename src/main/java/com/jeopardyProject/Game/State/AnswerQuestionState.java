package com.jeopardyProject.Game.State;

import com.jeopardyProject.Game.Command.*;

/**
 * State representing a player submitting an answer to a question.
 * <p>
 * This is the final phase of a player's turn (after selecting category and question).
 * Only {@link ActionType#ANSWER_QUESTION} actions are permitted. After answering
 * (regardless of correctness), always transitions to {@link WaitingState} to
 * end the player's turn.
 * </p>
 *
 * @see PlayerState
 * @see StateRegistry#ANSWER_QUESTION
 * @see com.jeopardyProject.Game.Command.AnswerQuestionAction
 * @author COMP3607 Jeopardy Project Team
 * @version 1.0
 */
public class AnswerQuestionState implements PlayerState{

    /**
     * Allows only answer submission actions.
     *
     * @param action the action to validate
     * @return true if action type is ANSWER_QUESTION, false otherwise
     */
    @Override
    public boolean isAllowed(Action action){
        return action.getType() == ActionType.ANSWER_QUESTION;
    }

    /**
     * Always transitions to waiting state after answering.
     * <p>
     * Regardless of whether the answer is correct or incorrect,
     * the player's turn phase ends and they return to waiting.
     * </p>
     *
     * @param result the action result (ignored)
     * @return WAITING state
     */
    @Override
    public PlayerState getNextState(ActionResult result){
        return StateRegistry.WAITING;
    }

    /**
     * Gets the name of this state.
     *
     * @return "AnswerQuestion"
     */
    @Override
    public String getStateName(){
        return "AnswerQuestion";
    }
}
