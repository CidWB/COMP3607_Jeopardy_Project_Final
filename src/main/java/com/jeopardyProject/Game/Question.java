package com.jeopardyProject.Game;

import java.util.HashMap;

/**
 * Represents a single Jeopardy question with multiple-choice options.
 * <p>
 * Each question belongs to a category and has an associated point value
 * (typically 100, 200, 300, 400, or 500). Questions include four answer
 * options (A, B, C, D) with one correct answer. Once answered correctly,
 * the question is marked as unavailable for future selection.
 * </p>
 *
 * @author COMP3607 Jeopardy Project Team
 * @version 1.0
 */
public class Question {
    /** Category this question belongs to (e.g., "Variables & Data Types"). */
    private String category;

    /** Point value of this question (100-500). */
    private int value;

    /** The question text/content to be displayed. */
    private String content;

    /** Multiple-choice options mapped as (key → answer text), typically A-D. */
    private HashMap<String, String> options;

    /** The correct answer key (e.g., "A", "B", "C", or "D"). */
    private String correctAnswer;

    /** Flag indicating whether this question has been correctly answered. */
    private boolean isAnswered;

    /**
     * Constructs a new Question with all required fields.
     *
     * @param category the category name this question belongs to
     * @param value the point value for this question
     * @param content the question text to display
     * @param options HashMap of answer choices (key → answer text)
     * @param rightAnswer the correct answer key (e.g., "A")
     */
    public Question(String category, int value, String content, HashMap<String, String> options, String rightAnswer) {
        this.category = category;
        this.value = value;
        this.content = content;
        this.options = options;
        this.correctAnswer = rightAnswer;
        this.isAnswered = false;
    }

    /**
     * Gets the category this question belongs to.
     *
     * @return the category name
     */
    public String getCategory() {
        return this.category;
    }

    /**
     * Gets the point value of this question.
     *
     * @return the point value (typically 100-500)
     */
    public int getValue() {
        return this.value;
    }

    /**
     * Gets the question text/content.
     *
     * @return the question content string
     */
    public String getContent() {
        return this.content;
    }

    /**
     * Gets all answer options for this question.
     *
     * @return HashMap mapping option keys to answer text
     */
    public HashMap<String, String> getOptions(){
        return this.options;
    }

    /**
     * Gets the answer text for a specific option key.
     *
     * @param key the option key (e.g., "A", "B", "C", "D")
     * @return the answer text associated with that key, or null if key doesn't exist
     */
    public String getValueGivenKey(String key){
        return this.options.get(key);
    }

    /**
     * Gets the correct answer key for this question.
     *
     * @return the correct answer option (e.g., "A")
     */
    public String getRightAnswer() {
        return this.correctAnswer;
    }

    /**
     * Checks whether this question has been correctly answered.
     *
     * @return true if answered correctly, false otherwise
     */
    public boolean getIsAnswered() {
        return this.isAnswered;
    }

    /**
     * Marks this question as answered or unanswered.
     * <p>
     * Once set to true, the question is typically marked as "X" in the grid
     * and cannot be selected again.
     * </p>
     *
     * @param answered true to mark as answered, false to mark as unanswered
     */
    public void setIsAnswered(boolean answered) {
        this.isAnswered = answered;
    }

    /**
     * Prints all answer options to the console.
     * <p>
     * Format: "[key] [answer text]" for each option (e.g., "A The answer is...")
     * </p>
     */
    public void printOptions(){
        for (String option: options.keySet()) {
            String answer = options.get(option);
            System.out.println(option + " " + answer);
        }
    }
}
