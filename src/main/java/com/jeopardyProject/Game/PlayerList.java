package com.jeopardyProject.Game;

import java.util.ArrayDeque;
import java.util.ArrayList;

import com.jeopardyProject.Game.State.*;
import com.jeopardyProject.Game.Command.*;

/**
 * Manages the collection of players and implements turn rotation logic.
 * <p>
 * This class uses an {@link ArrayDeque} as a queue to automatically rotate
 * player turns. After each turn completes, the current player is moved from
 * the front to the back of the queue via {@link #switchPlayer()}, ensuring
 * fair turn distribution.
 * </p>
 * <p>
 * PlayerList also serves as a facade for player actions, handling state
 * transitions and action execution for category selection, question selection,
 * and answer submission.
 * </p>
 *
 * @see Player
 * @see ArrayDeque
 * @author COMP3607 Jeopardy Project Team
 * @version 1.0
 */
public class PlayerList {
    /** Queue-based collection for automatic turn rotation. */
    private ArrayDeque<Player> players;

    /** Reference to the game controller for accessing game state. */
    private GameController controller;

    /** View component for displaying action results. */
    private GameView view;

    /**
     * Constructs a new PlayerList with the specified capacity.
     *
     * @param numPlayers initial capacity for the player queue (typically 2-4)
     */
    public PlayerList(int numPlayers){
        this.players = new ArrayDeque<>(numPlayers);
        this.controller = GameController.getInstance();
        this.view = controller.getView();
    }

    /**
     * Adds a new player to the queue.
     * <p>
     * Creates a new {@link Player} instance and adds it to the end of the queue.
     * </p>
     *
     * @param playerId unique identifier for the player (typically their name)
     */
    public void addPlayer(String playerId){
        this.players.add(new Player(playerId));
    }

    /**
     * Rotates turn to the next player in the queue.
     * <p>
     * Removes the current player from the front of the queue and adds them to
     * the back, then resets their state to {@link WaitingState}. This implements
     * a round-robin turn rotation system.
     * </p>
     */
    private void switchPlayer(){
        Player player = this.players.pollFirst();
        if(player != null){
            this.players.add(player);
            player.setState(StateRegistry.WAITING);
        }
    }

    /**
     * Gets the player whose turn is currently active.
     * <p>
     * Returns the player at the front of the queue without removing them.
     * </p>
     *
     * @return the current Player, or null if no players exist
     */
    public Player getCurrentPlayer(){
        return this.players.peek();
    }

    /**
     * Gets a snapshot of all players in the game.
     * <p>
     * Returns a new ArrayList to prevent external modification of the internal queue.
     * </p>
     *
     * @return ArrayList containing all Player objects
     */
    public ArrayList<Player> getAllPlayers(){
        return new ArrayList<>(this.players);
    }

    /**
     * Handles category selection for the current player.
     * <p>
     * Workflow:
     * <ol>
     *   <li>Transition current player to {@link SelectCategoryState}</li>
     *   <li>Create and assign {@link SelectCategoryAction}</li>
     *   <li>Execute action with user input</li>
     *   <li>Display result to console</li>
     * </ol>
     *
     * @param input the category name entered by the player
     * @see SelectCategoryAction
     */
    public void selectCategory(String input){
        Player current = getCurrentPlayer();
        current.setState(StateRegistry.SELECT_CATEGORY);
        Action action = new SelectCategoryAction(controller);
        current.setAction(action);
        ActionResult result = current.doAction(input);
        view.displayActionResult(result);
    }

    /**
     * Handles question value selection for the current player.
     * <p>
     * Workflow:
     * <ol>
     *   <li>Transition current player to {@link SelectQuestionState}</li>
     *   <li>Create and assign {@link SelectQuestionAction}</li>
     *   <li>Execute action with user input</li>
     *   <li>Display result to console</li>
     * </ol>
     *
     * @param input the question value entered by the player (e.g., "200")
     * @see SelectQuestionAction
     */
    public void selectQuestion(String input){
        Player current = getCurrentPlayer();
        current.setState(StateRegistry.SELECT_QUESTION);
        Action action = new SelectQuestionAction(controller);
        current.setAction(action);
        ActionResult result = current.doAction(input);
        view.displayActionResult(result);
    }

    /**
     * Handles answer submission for the current player.
     * <p>
     * Workflow:
     * <ol>
     *   <li>Transition current player to {@link AnswerQuestionState}</li>
     *   <li>Create and assign {@link AnswerQuestionAction}</li>
     *   <li>Execute action with user input</li>
     *   <li>Return result without displaying (caller handles display)</li>
     * </ol>
     *
     * @param input the answer option entered by the player (e.g., "A", "B", "C", "D")
     * @return ActionResult indicating whether the answer was correct
     * @see AnswerQuestionAction
     */
    public ActionResult answerQuestion(String input){
        Player current = getCurrentPlayer();
        current.setState(StateRegistry.ANSWER_QUESTION);
        Action action = new AnswerQuestionAction(controller);
        current.setAction(action);
        return current.doAction(input);
    }

    /**
     * Concludes the current player's turn by updating score and rotating turns.
     * <p>
     * Score Update Rules:
     * <ul>
     *   <li><b>Correct Answer</b>: score += value</li>
     *   <li><b>Wrong Answer</b>: score -= value</li>
     * </ul>
     * After updating the score, displays the result and calls {@link #switchPlayer()}
     * to rotate to the next player.
     *
     * @param value the point value of the question answered
     * @param correct true if the answer was correct, false otherwise
     */
    public void endTurn(int value, boolean correct){
        Player current = getCurrentPlayer();
        int score = current.getScore();

        if(correct){
            current.setScore(score + value);
        } else {
            current.setScore(score - value);
        }

        view.displayScore(current.getPlayerId(), current.getScore(), correct);
        switchPlayer();
    }
}
