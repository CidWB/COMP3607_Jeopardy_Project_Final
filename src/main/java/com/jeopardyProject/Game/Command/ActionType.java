package com.jeopardyProject.Game.Command;

/**
 * Enumeration of all possible player action types in the game.
 * <p>
 * These action types correspond to the three phases of a player's turn:
 * <ol>
 *   <li>{@link #SELECT_CATEGORY} - Choose a category from available options</li>
 *   <li>{@link #SELECT_QUESTION} - Choose a question value within the category</li>
 *   <li>{@link #ANSWER_QUESTION} - Submit an answer to the selected question</li>
 * </ol>
 *
 * @see Action
 * @author COMP3607 Jeopardy Project Team
 * @version 1.0
 */
public enum ActionType {
    /** Action to select a question category. */
    SELECT_CATEGORY,

    /** Action to select a question value within the chosen category. */
    SELECT_QUESTION,

    /** Action to submit an answer to the selected question. */
    ANSWER_QUESTION
}
