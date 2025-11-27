package com.jeopardyProject.Game.State;

/**
 * Registry providing singleton instances of all player states.
 * <p>
 * This class implements the Flyweight pattern by maintaining single instances
 * of each state that can be shared across all players. States are stateless,
 * so sharing instances is safe and memory-efficient.
 * <p>
 * Available States:
 * <ul>
 *   <li>{@link #WAITING} - Default state when player's turn is not active</li>
 *   <li>{@link #SELECT_CATEGORY} - Player is choosing a category</li>
 *   <li>{@link #SELECT_QUESTION} - Player is choosing a question value</li>
 *   <li>{@link #ANSWER_QUESTION} - Player is submitting an answer</li>
 * </ul>
 *
 * @see PlayerState
 * @author COMP3607 Jeopardy Project Team
 * @version 1.0
 */
public class StateRegistry {
    /** Waiting state - blocks all actions until player's turn becomes active. */
    public static final PlayerState WAITING = new WaitingState();

    /** Category selection state - allows only category selection actions. */
    public static final PlayerState SELECT_CATEGORY = new SelectCategoryState();

    /** Question selection state - allows only question value selection actions. */
    public static final PlayerState SELECT_QUESTION = new SelectQuestionState();

    /** Answer submission state - allows only answer submission actions. */
    public static final PlayerState ANSWER_QUESTION = new AnswerQuestionState();

    /**
     * Private constructor to prevent instantiation.
     * This is a utility class providing only static constants.
     */
    private StateRegistry(){}
}
