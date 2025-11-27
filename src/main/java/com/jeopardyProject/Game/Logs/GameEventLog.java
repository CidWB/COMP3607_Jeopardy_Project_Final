package com.jeopardyProject.Game.Logs;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import com.jeopardyProject.Game.Question;

import java.time.ZoneId;

/**
 * Event log entry using Builder pattern for flexible construction.
 * <p>
 * This class represents a single logged event during gameplay, such as
 * selecting a category, answering a question, or system events. It uses
 * method chaining (Builder pattern) to allow flexible configuration of
 * optional fields.
 * </p>
 * <p>
 * Timestamps are automatically generated in ISO 8601 format (yyyy-MM-ddTHH:mm:ss)
 * using the America/Port_of_Spain timezone.
 * </p>
 *
 * @see Logger
 * @author COMP3607 Jeopardy Project Team
 * @version 1.0
 */
public class GameEventLog {
    /** Unique identifier for the game session. */
    private String caseId;

    /** ID of the player who performed the action (or "System" for system events). */
    private String playerId;

    /** Description of the action performed (e.g., "Select Category", "Answer Question"). */
    private String action;

    /** Formatter for ISO 8601 timestamps. */
    private static DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    /** Timestamp when the event occurred (format: yyyy-MM-ddTHH:mm:ss). */
    private String timestamp;

    /** Question associated with this event (optional). */
    private Question question;

    /** Category selected (optional). */
    private String category;

    /** Question value in points (optional). */
    private int value;

    /** Answer submitted by player (optional). */
    private String answer;

    /** Whether the action was successful (defaults to true). */
    private boolean result;

    /** Player's score after this event (optional). */
    private int newScore;

    /**
     * Constructs a new GameEventLog with required fields.
     * <p>
     * Automatically generates a timestamp and sets result to true by default.
     * Use builder methods to set optional fields.
     * </p>
     *
     * @param caseId unique game session identifier
     * @param playerId ID of the player or "System"
     * @param action description of the action performed
     */
    public GameEventLog(String caseId, String playerId, String action){
        this.caseId = caseId;
        this.playerId = playerId;
        this.action = action;
        this.timestamp = createTimestamp();
        this.result = true;
    }

    /**
     * Creates an ISO 8601 timestamp for the current moment.
     * <p>
     * Uses America/Port_of_Spain timezone and truncates to seconds precision.
     * </p>
     *
     * @return timestamp string in format yyyy-MM-ddTHH:mm:ss
     */
    private String createTimestamp(){
        ZoneId zone = ZoneId.of("America/Port_of_Spain");
        LocalDateTime now = LocalDateTime.now(zone).truncatedTo(ChronoUnit.SECONDS);
        String timestamp = formatter.format(now);
        return timestamp;   // in the format yyyy-mm-ddTHH:mm:ss as shown in the sample log
    } 

    /**
     * Refreshes the timestamp to the current moment.
     * <p>
     * Builder method for method chaining.
     * </p>
     *
     * @return this GameEventLog instance for method chaining
     */
    public GameEventLog setTimestamp(){
        this.timestamp = createTimestamp();
        return this;
    }

    /**
     * Sets the question associated with this event.
     * <p>
     * Builder method for method chaining.
     * </p>
     *
     * @param question the Question object
     * @return this GameEventLog instance for method chaining
     */
    public GameEventLog setQuestion(Question question){
        this.question = question;
        return this;
    }

    /**
     * Sets the category for this event.
     * <p>
     * Builder method for method chaining.
     * </p>
     *
     * @param category the category name
     * @return this GameEventLog instance for method chaining
     */
    public GameEventLog setCategory(String category){
        this.category = category;
        return this;
    }

    /**
     * Sets the question value (points) for this event.
     * <p>
     * Builder method for method chaining.
     * </p>
     *
     * @param value the point value
     * @return this GameEventLog instance for method chaining
     */
    public GameEventLog setValue(int value){
        this.value = value;
        return this;
    }

    /**
     * Sets the result status for this event.
     * <p>
     * Builder method for method chaining.
     * </p>
     *
     * @param result true if successful, false if failed
     * @return this GameEventLog instance for method chaining
     */
    public GameEventLog setResult(boolean result){
        this.result = result;
        return this;
    }

    /**
     * Sets the answer submitted for this event.
     * <p>
     * Builder method for method chaining.
     * </p>
     *
     * @param answer the answer string
     * @return this GameEventLog instance for method chaining
     */
    public GameEventLog setAnswer(String answer){
        this.answer = answer;
        return this;
    }

    /**
     * Sets answer, result, and new score in a single call.
     * <p>
     * Convenience builder method for answer submission events.
     * </p>
     *
     * @param answer the answer submitted
     * @param result true if correct, false if incorrect
     * @param newScore the player's updated score
     * @return this GameEventLog instance for method chaining
     */
    public GameEventLog setAnswerResultScore(String answer, boolean result, int newScore){
        this.answer = answer;
        this.result = result;
        this.newScore = newScore;
        return this;
    }

    /** @return the case ID for this event */
    public String getCaseId(){
        return this.caseId;
    }

    /** @return the player ID for this event */
    public String getPlayerId(){
        return this.playerId;
    }

    /** @return the action description */
    public String getAction(){
        return this.action;
    }

    /** @return the timestamp string */
    public String getTimestamp(){
        return this.timestamp;
    }

    /** @return the associated Question, or null if none */
    public Question getQuestion(){
        return this.question;
    }

    /** @return the category name, or null if none */
    public String getCategory(){
        return this.category;
    }

    /** @return the question value in points */
    public int getValue(){
        return this.value;
    }

    /** @return the answer submitted, or null if none */
    public String getAnswer(){
        return this.answer;
    }

    /** @return true if action was successful, false otherwise */
    public boolean getResult(){
        return this.result;
    }

    /** @return the player's score after this event */
    public int getNewScore(){
        return this.newScore;
    }

    /**
     * Prints the log entry to standard output in a formatted string.
     * <p>
     * Includes all non-null/non-zero fields separated by pipes (|).
     * </p>
     */
    public void printLog(){
        StringBuilder sb = new StringBuilder();
        sb.append("Case ID: ").append(caseId).append(" | ");
        sb.append("Player ID: ").append(playerId).append(" | ");
        sb.append("Action: ").append(action).append(" | ");
        sb.append("Timestamp: ").append(timestamp).append(" | ");
        
        if (category != null) {
            sb.append("Category: ").append(category).append(" | ");
        }
        if (value != 0) {
            sb.append("Value: ").append(value).append(" | ");
        }
        if (answer != null) {
            sb.append("Answer: ").append(answer).append(" | ");
        }
        sb.append("Result: ").append(result).append(" | ");
        if (newScore != 0) {
            sb.append("New Score: ").append(newScore);
        }
        
        System.out.println(sb.toString());
    }

    /**
     * Returns a string representation of this log entry.
     * <p>
     * Formats all fields into a pipe-separated string with labels.
     * Only includes non-null/non-zero optional fields.
     * </p>
     *
     * @return formatted string representation of the log entry
     */
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Case ID: ").append(caseId).append(" | ");
        sb.append("Player ID: ").append(playerId).append(" | ");
        sb.append("Action: ").append(action).append(" | ");
        sb.append("Timestamp: ").append(timestamp).append(" | ");

        if (category != null) {
            sb.append("Category: ").append(category).append(" | ");
        }
        if (value != 0) {
            sb.append("Value: ").append(value).append(" | ");
        }
        if (answer != null) {
            sb.append("Answer: ").append(answer).append(" | ");
        }
        sb.append("Result: ").append(result).append(" | ");
        if (newScore != 0) {
            sb.append("New Score: ").append(newScore);
        }

        return sb.toString();
    }
}