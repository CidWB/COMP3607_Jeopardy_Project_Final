package com.jeopardyProject.Game;

import java.util.Scanner;

import com.jeopardyProject.Game.Logs.GameEventLog;
import com.jeopardyProject.Game.Logs.Logger;

/**
 * Central game controller implementing the Singleton pattern.
 * <p>
 * This class serves as the primary orchestrator for the entire Jeopardy game flow,
 * coordinating between players, questions, game state, and logging systems. It manages
 * the complete game lifecycle from initialization through gameplay to report generation.
 * <p>
 * The controller integrates with multiple design patterns:
 * <ul>
 *   <li><b>Singleton Pattern</b>: Ensures only one game instance exists</li>
 *   <li><b>State Pattern</b>: Manages player turn states through the {@link Player} class</li>
 *   <li><b>Command Pattern</b>: Delegates player actions to command objects</li>
 *   <li><b>Template Method Pattern</b>: Uses file readers for question loading</li>
 * </ul>
 *
 * @see GameConfig
 * @see PlayerList
 * @see QuestionList
 * @see Logger
 * @author COMP3607 Jeopardy Project Team
 * @version 1.0
 */
public class GameController {
    /** Singleton logger instance for game event tracking. */
    private static Logger logger = Logger.getInstance();

    /** Singleton controller instance (thread-safe). */
    private static GameController controller;

    /** Counter for total games played across application lifetime. */
    private static int gameCount = 0;

    /** Scanner for reading user input from console. */
    private Scanner scanner;

    /** Configuration manager for game setup operations. */
    private GameConfig config;

    /** View layer for console output rendering. */
    private GameView view;

    /** Unique identifier for the current game session (format: "Game_XXX"). */
    private String caseId;

    /** System player ID used for logging system-level events. */
    private static String playerId = "System";

    /** Collection of all questions loaded for the current game. */
    private QuestionList questions;

    /** Currently selected category during gameplay. */
    private String category;

    /** Currently selected question being answered. */
    private Question question;

    /** Manages the list of players and turn rotation. */
    private PlayerList players;

    /** Reference to the player whose turn is currently active. */
    private Player currentPlayer;

    /** Current turn number in the game sequence. */
    private int turnNum = 1;

    /** Flag indicating whether the game loop should continue running. */
    private boolean gameRunning = true;

    /**
     * Private constructor to enforce Singleton pattern.
     * Initializes the scanner for user input and the view for display output.
     */
    private GameController(){
        this.scanner = new Scanner(System.in);
        this.view = new GameView();
    }

    /**
     * Returns the singleton instance of the GameController.
     * <p>
     * Thread-safe implementation using synchronized method to prevent
     * multiple instances in concurrent environments.
     * </p>
     *
     * @return the single GameController instance
     */
    public static synchronized GameController getInstance(){
        if (controller == null)
            controller = new GameController();
        return controller;
    }

    /**
     * Gets the view component for rendering game output.
     *
     * @return the GameView instance used for console display
     */
    public GameView getView(){
        return view;
    }

    /**
     * Resets the singleton controller and game count.
     * <p>
     * Primarily used for testing purposes to ensure clean state between test runs.
     * </p>
     */
    public static void reset(){
        controller = null;
        gameCount = 0;
    }

    /**
     * Sets the question list for the current game.
     * <p>
     * Typically used for dependency injection in testing scenarios.
     * </p>
     *
     * @param questions the QuestionList to use for this game session
     */
    public void setQuestions(QuestionList questions){
        this.questions = questions;
    }

    /**
     * Initializes a new game session with unique case ID and logging.
     * <p>
     * Increments the game counter, generates a unique case ID (e.g., "Game_001"),
     * creates a new GameConfig, clears previous reports, and logs the game start event.
     * </p>
     */
    public void startGame(){
        gameCount++;
        String gameNumber = String.format("%03d", gameCount);
        this.caseId = "Game_" + gameNumber;
        this.config = new GameConfig(this.caseId);
        logger.clearReports();
        logger.log(
            new GameEventLog(this.caseId, GameController.playerId, "Start Game")
        );
    }

    /**
     * Configures the game by loading questions and registering players.
     * <p>
     * Delegates to {@link GameConfig} to:
     * <ol>
     *   <li>Load questions from user-specified file (CSV/JSON/XML)</li>
     *   <li>Register 2-4 players with their chosen names</li>
     * </ol>
     * Exits early if either operation fails.
     */
    public void setupGame(){
        this.questions = config.loadQuestions();
        if (this.questions == null){
            System.out.println("Failed to load questions. Exiting.");
            return;
        }
        this.players = config.addPlayers();
        if (this.players == null){
            System.out.println("Failed to add players. Exiting.");
            return;
        }
    }

    /**
     * Reads and processes user input from the console.
     * <p>
     * Handles the "QUIT" command by setting {@code gameRunning} to false
     * and returning null. All other input is trimmed and converted to uppercase.
     * </p>
     *
     * @return the processed user input in uppercase, or null if user typed "QUIT"
     */
    private String getUserInput(){
        String input = scanner.nextLine().trim();
        if ("QUIT".equalsIgnoreCase(input)){
            gameRunning = false;
            return null;
        }
        return input.toUpperCase();
    }

    /**
     * Gets the unique case ID for the current game session.
     *
     * @return the case ID string (format: "Game_XXX")
     */
    public String getCaseId(){
        return this.caseId;
    }

    /**
     * Gets the question list for the current game.
     *
     * @return the QuestionList containing all loaded questions
     */
    public QuestionList getQuestions(){
        return this.questions;
    }

    /**
     * Gets the player list managing turn rotation.
     *
     * @return the PlayerList containing all registered players
     */
    public PlayerList getPlayers(){
        return this.players;
    }

    /**
     * Sets the currently selected category.
     *
     * @param category the category name chosen by the player
     */
    public void setCategory(String category){
        this.category = category;
    }

    /**
     * Gets the currently selected category.
     *
     * @return the active category name
     */
    public String getCategory(){
        return this.category;
    }

    /**
     * Sets the currently active question.
     *
     * @param question the Question object selected by the player
     */
    public void setQuestion(Question question){
        this.question = question;
    }

    /**
     * Gets the currently active question.
     *
     * @return the Question object being answered
     */
    public Question getQuestion(){
        return this.question;
    }

    /**
     * Executes the main game loop managing turn-by-turn gameplay.
     * <p>
     * Game Flow:
     * <ol>
     *   <li>Display question grid and current player</li>
     *   <li>Player selects category (State: SelectCategoryState)</li>
     *   <li>Player selects question value (State: SelectQuestionState)</li>
     *   <li>Player submits answer (State: AnswerQuestionState)</li>
     *   <li>If incorrect, other players get attempts in queue order</li>
     *   <li>Rotate to next player and repeat until all questions answered or "QUIT"</li>
     * </ol>
     * All actions are validated by the State pattern and logged for report generation.
     */
    public void playGame(){
        String input;
        String currPlayerId;
        logger.initTurnReport(this.caseId, this.players.getAllPlayers());

        while (gameRunning){
            this.currentPlayer = this.players.getCurrentPlayer();
            if (this.currentPlayer == null){
                view.displayError("Error: No current player. Exiting game.");
                break;
            }
            currPlayerId = this.currentPlayer.getPlayerId();
            view.displayQuestionGrid(this.questions);
            view.displayCurrentPlayer(this.currentPlayer.getPlayerId());
            view.displayPrompt("Choose a Category. (You may input only the first word)");
            input = getUserInput();
            if(input == null) break;
            this.players.selectCategory(input);
            logger.log(
                new GameEventLog(this.caseId, this.currentPlayer.getPlayerId(), "Select Category")
                .setCategory(this.category)
            );

            view.displayPrompt("Choose a value.");
            input = getUserInput();
            if(input == null) break;
            this.players.selectQuestion(input);
            try{
                logger.log(
                    new GameEventLog(this.caseId, currentPlayer.getPlayerId(), "Select Question")
                    .setCategory(this.category)
                    .setValue(Integer.parseInt(input))
                );
            } catch (NumberFormatException e){
                view.displayError("Invalid number format for question value");
            }
            logger.addReportQuestion(turnNum);

            view.displayQuestion(this.question);
            input = getUserInput();
            if(input == null) break;
            this.players.answerQuestion(input);
            this.players.endTurn(this.question.getValue(), this.question.getIsAnswered());

            logger.log(
            new GameEventLog(this.caseId, this.currentPlayer.getPlayerId(), "Answer Question")
            .setQuestion(this.question)
            .setCategory(this.category)
            .setValue(this.question.getValue())
            .setAnswerResultScore(input, this.question.getIsAnswered(), this.currentPlayer.getScore())
            );
            logger.addReportAnswer();

            while(!this.question.getIsAnswered()){
                this.currentPlayer = players.getCurrentPlayer();
                view.displayCurrentPlayer(this.currentPlayer.getPlayerId());
                view.displayPrompt("Input your answer:");
                input = getUserInput();
                if(input == null) break;
                this.players.answerQuestion(input);
                this.players.endTurn(this.question.getValue(), this.question.getIsAnswered());

                logger.log(
                new GameEventLog(this.caseId, this.currentPlayer.getPlayerId(), "Answer Question")
                .setCategory(this.category)
                .setValue(this.question.getValue())
                .setAnswerResultScore(input, this.question.getIsAnswered(), this.currentPlayer.getScore())
                );
                logger.addReportAnswer();

                Player nextPlayer = this.players.getCurrentPlayer();
                if(nextPlayer != null && currPlayerId.equals(nextPlayer.getPlayerId()))
                    break;
            }
            turnNum++;
            if(!this.questions.hasQuestions()){
                view.displayMessage("Congrats! You made it to the end of the game.");
                gameRunning = false;
            }
        }
        exitGame();
    }

    /**
     * Finalizes the game session and generates comprehensive reports.
     * <p>
     * Cleanup Operations:
     * <ol>
     *   <li>Generate TurnReport.txt with human-readable game summary and final scores</li>
     *   <li>Generate LogReport.csv with timestamped event log for analysis</li>
     *   <li>Log all report generation events</li>
     *   <li>Display report file locations to users</li>
     * </ol>
     * Both reports are saved to the project root directory.
     *
     * @see Logger#generateTurnReport(java.util.ArrayList)
     * @see Logger#generateLogReport()
     */
    public void exitGame(){
        String txtFileName = logger.generateTurnReport(this.players.getAllPlayers());
        logger.log(
            new GameEventLog(this.caseId, GameController.playerId, "Generate report")
            );

        String csvFileName = logger.generateLogReport();
        logger.log(
            new GameEventLog(this.caseId, GameController.playerId, "Generate event log")
        );
        logger.log(
            new GameEventLog(this.caseId, GameController.playerId, "Exit game")
        );

        view.displayMessage("The turn-by-turn summary report can be found in the file " + txtFileName);
        view.displayMessage("The game event log report can be found in the file " + csvFileName);
        view.displayMessage("Thanks for playing!");
    }
}
