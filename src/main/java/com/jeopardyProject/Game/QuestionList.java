package com.jeopardyProject.Game;

import java.util.*;

/**
 * Manages a collection of questions and provides formatted grid display.
 * <p>
 * This class stores all questions for a game session and provides methods to:
 * <ul>
 *   <li>Add questions to the collection</li>
 *   <li>Filter questions by category</li>
 *   <li>Check if unanswered questions remain</li>
 *   <li>Display questions in a formatted Jeopardy-style grid</li>
 * </ul>
 * The grid organizes questions by category (columns) and value (rows), marking
 * answered questions with "X" and displaying values for available questions.
 *
 * @author COMP3607 Jeopardy Project Team
 * @version 1.0
 */
public class QuestionList {
    /** Minimum column width for grid formatting. */
    private static final int MIN_COLUMN_WIDTH = 3;

    /** ArrayList storing all Question objects. */
    private ArrayList<Question> questions = new ArrayList<>();

    /** Set of unique category names (maintains insertion order). */
    private LinkedHashSet<String> categorySet;

    /** Ordered list of categories for grid column headers. */
    private List<String> categories;

    /** Set of unique question values in descending order (500, 400, 300, 200, 100). */
    private TreeSet<Integer> valueSet;

    /** Nested map structure: category → (value → Question) for grid lookup. */
    private Map<String, Map<Integer, Question>> grid;

    /**
     * Adds a question to the collection.
     *
     * @param question the Question object to add
     */
    public void addQuestion(Question question){
        this.questions.add(question);
    }

    /**
     * Gets the complete array of all questions.
     *
     * @return ArrayList containing all Question objects
     */
    public ArrayList<Question> getQuestionArray(){
        return this.questions;
    }

    /**
     * Retrieves all questions belonging to a specific category.
     * <p>
     * Performs case-insensitive category matching.
     * </p>
     *
     * @param category the category name to filter by
     * @return ArrayList of questions in the specified category (empty if none found)
     */
    public ArrayList<Question> getQuestionsByCategory(String category){
        ArrayList<Question> newQuestions = new ArrayList<>();
        for(Question question : this.questions){
            if (question == null)   // just in case
                continue;

            if (question.getCategory().equalsIgnoreCase(category))
                newQuestions.add(question);
        }
        return newQuestions;
    }

    /**
     * Checks if any unanswered questions remain in the collection.
     *
     * @return true if at least one question is unanswered, false if all answered
     */
    public boolean hasQuestions(){
        for (Question q : questions){
            if(q.getIsAnswered() == false)
                return true;
        }
        return false;       // if all questions have been answered
    }

    /**
     * Initializes the nested grid data structure for question organization.
     * <p>
     * Creates a two-dimensional mapping of category → value → Question to enable
     * efficient lookup and formatted display. Also extracts unique categories
     * and values from the question collection.
     * </p>
     * <p>
     * Data Structures Created:
     * <ul>
     *   <li>{@code categorySet} - LinkedHashSet preserving category order</li>
     *   <li>{@code valueSet} - TreeSet with descending value order</li>
     *   <li>{@code grid} - Nested HashMap for O(1) question lookup</li>
     * </ul>
     * Must be called after loading all questions and before displaying the grid.
     */
    public void createQuestionGrid(){       // creates nested map that can be used for user selection
        if (questions == null || questions.isEmpty()) {
            System.out.println("No questions available.");
        }

        this.categorySet = new LinkedHashSet<>();
        this.valueSet = new TreeSet<>(Collections.reverseOrder());     // descending order
        this.grid = new HashMap<>();     // nested map for (category : (value : question))

        for (Question q : questions) {
            if (q == null) continue;
            String category = q.getCategory();
            int value = q.getValue();
            this.categorySet.add(category);
            this.valueSet.add(value);
            
            Map<Integer, Question> inner = grid.get(category);
            if (inner == null) {
                inner = new HashMap<>();
                grid.put(category, inner);
            }
            inner.put(value, q);
        }
    }

    /**
     * Calculates column widths for properly aligned grid formatting.
     * <p>
     * Determines the maximum width needed for each category column by comparing:
     * <ol>
     *   <li>Length of the category name</li>
     *   <li>Length of question value strings in that column</li>
     *   <li>Minimum column width constant ({@value #MIN_COLUMN_WIDTH})</li>
     * </ol>
     * Returns an array of widths corresponding to each category for use in
     * formatted string output.
     * </p>
     *
     * @return int array containing the width for each category column
     */
    private int[] formatGrid(){
        this.categories = new ArrayList<>(categorySet);

        int[] categoryWidths = new int[this.categories.size()];
        for (int i = 0; i < this.categories.size(); i++) {
            int width = this.categories.get(i).length();         // num of chars in category string
            Map<Integer, Question> map = this.grid.get(this.categories.get(i));
            if (map != null) {
                for (Integer value : valueSet) {
                    Question q = map.get(value);
                    String cell = "";
                    if (q != null && !q.getIsAnswered()){        // answered questions will have a smaller width than category
                            cell = "" + q.getValue();
                    }
                    if (cell.length() > width)
                        width = cell.length();
                }
            }
            categoryWidths[i] = Math.max(width, MIN_COLUMN_WIDTH);
        }
        return categoryWidths;
    }

    /**
     * Displays the question grid in a formatted table to the console.
     * <p>
     * Renders a Jeopardy-style game board with:
     * <ul>
     *   <li>Category names as column headers</li>
     *   <li>Question values in descending order (500 → 100) as rows</li>
     *   <li>"X" marking answered questions</li>
     *   <li>Value numbers for available questions</li>
     *   <li>Border lines separating rows and columns</li>
     * </ul>
     * Example output:
     * <pre>
     * +------------------+------------------+
     * | Variables        | Control Flow     |
     * +------------------+------------------+
     * | 500              | X                |
     * | 400              | 400              |
     * | 300              | X                |
     * +------------------+------------------+
     * </pre>
     */
    public void display(){
        int[] categoryWidths = formatGrid();    // must be called each time since table has to be updated

        StringBuilder sep = new StringBuilder();
        for (int width : categoryWidths) {
            sep.append("+");
            for (int i = 0; i < width + 2; i++) sep.append("-");
        }
        sep.append("+");
        System.out.println(sep.toString());

        StringBuilder fmt = new StringBuilder();
        for (int width : categoryWidths) {
            fmt.append("| %-").append(width).append("s ");
        }
        fmt.append("|\n");
        String format = fmt.toString();

        System.out.printf(format, categories.toArray());

        StringBuilder sep1 = new StringBuilder();
        for (int width : categoryWidths) {
            sep1.append("+");
            for (int i = 0; i < width + 2; i++) sep1.append("-");
        }
        sep1.append("+");
        System.out.println(sep1.toString());

        for (Integer v : valueSet) {
            Object[] row = new Object[categories.size()];
            for (int i = 0; i < categories.size(); i++) {
                Map<Integer, Question> map = grid.get(categories.get(i));
                String cell = "";
                if (map != null && map.containsKey(v)) {
                    Question q = map.get(v);
                    if(q.getIsAnswered())
                        cell = "X";
                    else
                        cell = String.valueOf(q.getValue());
                }
                row[i] = cell;
            }
            System.out.printf(format, row);
        }

        StringBuilder sep2 = new StringBuilder();
        for (int width : categoryWidths) {
            sep2.append("+");
            for (int i = 0; i < width + 2; i++) sep2.append("-");
        }
        sep2.append("+");
        System.out.println(sep2.toString());
    }
}
