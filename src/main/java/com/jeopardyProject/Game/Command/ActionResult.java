package com.jeopardyProject.Game.Command;

/**
 * Immutable result object returned from action execution.
 * <p>
 * This class encapsulates the outcome of an {@link Action} execution,
 * containing both a success/failure flag and a descriptive message.
 * Uses static factory methods to enforce immutability and provide
 * clear intent when creating instances.
 * </p>
 *
 * @see Action#execute(String)
 * @author COMP3607 Jeopardy Project Team
 * @version 1.0
 */
public class ActionResult {
    /** Indicates whether the action executed successfully. */
    private final boolean success;

    /** Descriptive message about the action outcome. */
    private final String message;

    /**
     * Private constructor to enforce use of static factory methods.
     *
     * @param success true if action succeeded, false otherwise
     * @param message descriptive message about the outcome
     */
    private ActionResult(boolean success, String message){
        this.success = success;
        this.message = message;
    }

    /**
     * Creates a successful action result with the given message.
     *
     * @param message success message to display (e.g., "Category selected: Variables")
     * @return new ActionResult with success = true
     */
    public static ActionResult success(String message){
        return new ActionResult(true, message);
    }

    /**
     * Creates a failed action result with the given message.
     *
     * @param message failure message to display (e.g., "Invalid category selection")
     * @return new ActionResult with success = false
     */
    public static ActionResult failure(String message){
        return new ActionResult(false, message);
    }

    /**
     * Checks if the action executed successfully.
     *
     * @return true if successful, false if failed
     */
    public boolean isSuccess(){
        return success;
    }

    /**
     * Gets the descriptive message about the action outcome.
     *
     * @return the result message
     */
    public String getMessage(){
        return message;
    }
}
