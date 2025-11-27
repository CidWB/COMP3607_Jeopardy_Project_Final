package com.jeopardyProject.Game;

import com.jeopardyProject.Game.Command.*;
import com.jeopardyProject.Game.State.*;

/**
 * Represents a player in the Jeopardy game with state-based turn control.
 * <p>
 * This class integrates the <b>State Pattern</b> and <b>Command Pattern</b> to manage
 * player actions during gameplay. Each player has a current state that determines
 * which actions are valid, and transitions between states as they progress through
 * their turn (selecting category → selecting question → answering question).
 * <p>
 * State Transitions:
 * <pre>
 * WaitingState → SelectCategoryState → SelectQuestionState → AnswerQuestionState → WaitingState
 * </pre>
 *
 * @see PlayerState
 * @see Action
 * @see StateRegistry
 * @author COMP3607 Jeopardy Project Team
 * @version 1.0
 */
public class Player {
    /** Unique identifier for this player (player name). */
    private String playerId;

    /** Current score (increases on correct answers, decreases on wrong answers). */
    private int score;

    /** Current state controlling which actions are allowed. */
    private PlayerState state;

    /** Currently assigned action command to execute. */
    private Action action;

    /**
     * Constructs a new player with the specified ID.
     * <p>
     * Player is initialized in {@link WaitingState} with a score of 0.
     * </p>
     *
     * @param playerId unique identifier for the player (typically their name)
     */
    public Player(String playerId){
        this.playerId = playerId;
        this.setState(StateRegistry.WAITING);
    }

    /**
     * Gets the player's unique identifier.
     *
     * @return the player ID string
     */
    public String getPlayerId(){
        return this.playerId;
    }

    /**
     * Sets the player's current state.
     * <p>
     * State changes control which actions the player can perform during their turn.
     * </p>
     *
     * @param state the new PlayerState to transition to
     * @see PlayerState
     */
    public void setState(PlayerState state){
        this.state = state;
    }

    /**
     * Gets the player's current state.
     *
     * @return the active PlayerState
     */
    public PlayerState getState(){
        return this.state;
    }

    /**
     * Sets the action to be executed by this player.
     * <p>
     * The action is only assigned if the current state allows it.
     * If the action is not permitted by the current state, {@code action} is set to null.
     * </p>
     *
     * @param action the Action command to assign
     * @see PlayerState#isAllowed(Action)
     */
    public void setAction(Action action){
        if(state.isAllowed(action)){
            this.action = action;
        } else {
            this.action = null;
        }
    }

    /**
     * Executes the currently assigned action with the given input.
     * <p>
     * Workflow:
     * <ol>
     *   <li>Validate that an action is set (returns failure if null)</li>
     *   <li>Execute the action with the provided input</li>
     *   <li>Transition to the next state based on action result</li>
     * </ol>
     *
     * @param input user input string to pass to the action
     * @return ActionResult indicating success or failure with a message
     * @see Action#execute(String)
     * @see PlayerState#getNextState(ActionResult)
     */
    public ActionResult doAction(String input){
        if(this.action == null){
            return ActionResult.failure("No action set for " + playerId);
        }

        ActionResult result = this.action.execute(input);
        this.state = this.state.getNextState(result);
        return result;
    }

    /**
     * Gets the player's current score.
     *
     * @return the score value (can be negative)
     */
    public int getScore(){
        return this.score;
    }

    /**
     * Sets the player's score to a new value.
     * <p>
     * Typically called after answering a question to update score
     * (+value for correct, -value for incorrect).
     * </p>
     *
     * @param score the new score value
     */
    public void setScore(int score){
        this.score = score;
    }
}
