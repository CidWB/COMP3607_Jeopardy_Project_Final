package com.jeopardyProject.Game;

import java.util.Scanner;

import com.jeopardyProject.Game.Logs.GameEventLog;
import com.jeopardyProject.Game.Logs.Logger;
import com.jeopardyProject.Game.Template.*;

/**
 * Handles game setup and configuration operations.
 * <p>
 * This class manages two critical setup tasks:
 * <ol>
 *   <li><b>Question Loading</b>: Prompts user for file path and instantiates
 *       appropriate file reader (CSV/JSON/XML) using the Template Method pattern</li>
 *   <li><b>Player Registration</b>: Validates player count (2-4) and collects player names</li>
 * </ol>
 * All configuration actions are logged to the game event log for traceability.
 *
 * @see FileReaderTemplate
 * @see CsvFileReader
 * @see JsonFileReader
 * @see XmlFileReader
 * @author COMP3607 Jeopardy Project Team
 * @version 1.0
 */
public class GameConfig {
    /** Minimum number of players required to start a game. */
    private static final int MIN_PLAYERS = 2;

    /** Maximum number of players allowed in a game. */
    private static final int MAX_PLAYERS = 4;

    /** Scanner for reading user input during setup. */
    private Scanner scanner;

    /** Template Method pattern instance for reading question files. */
    private FileReaderTemplate fileReader;

    /** Absolute file path to the question data file. */
    private String filepath;

    /** Unique identifier for the current game session. */
    private String caseId;

    /** System player ID for logging setup events. */
    private static String playerId = "System";

    /** Logger instance for tracking configuration events. */
    private Logger logger = Logger.getInstance();

    /** Number of players selected for the game. */
    private int numPlayers;

    /**
     * Constructs a new GameConfig with the specified case ID.
     *
     * @param caseId unique identifier for the game session (e.g., "Game_001")
     */
    public GameConfig(String caseId){
        this.caseId = caseId;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Prompts user for file path and instantiates the appropriate file reader.
     * <p>
     * Supports three file formats:
     * <ul>
     *   <li><b>.csv</b> - Creates {@link CsvFileReader}</li>
     *   <li><b>.json</b> - Creates {@link JsonFileReader}</li>
     *   <li><b>.xml</b> - Creates {@link XmlFileReader}</li>
     * </ul>
     * Loops until a valid file path with supported extension is provided.
     * </p>
     *
     * @throws IllegalArgumentException if file extension is not .csv, .json, or .xml
     */
    private void setFileReader(){
        boolean success = false;
        while(!success){
            System.out.print("Enter absolute filepath to load questions: ");
            this.filepath = scanner.nextLine();
            try{
                if(filepath.endsWith(".csv")){
                    this.fileReader = new CsvFileReader(filepath);
                    success=true;
                }
                else if(filepath.endsWith(".json")){
                    this.fileReader = new JsonFileReader(filepath);
                    success=true;
                }
                else if(filepath.endsWith(".xml")){
                    this.fileReader = new XmlFileReader(filepath);
                    success=true;
                }
                else
                    throw new IllegalArgumentException("File format not supported. Supported formats: .csv, .json, .xml");
            } catch (Exception e) {
                System.out.println("Invalid file or format. Please enter a valid filepath.");
            }
        }
    }

    /**
     * Loads questions from user-specified file using Template Method pattern.
     * <p>
     * Workflow:
     * <ol>
     *   <li>Prompt user for file path via {@link #setFileReader()}</li>
     *   <li>Invoke template method {@link FileReaderTemplate#readFile()}</li>
     *   <li>Initialize question grid for formatted display</li>
     *   <li>Log success or failure event</li>
     * </ol>
     *
     * @return QuestionList containing all parsed questions, or null if loading fails
     * @see FileReaderTemplate#readFile()
     */
    public QuestionList loadQuestions(){
        try{
            setFileReader();
        } catch (IllegalArgumentException e){
            System.err.println("Error: " + e.getMessage()); // try to log error
            logger.log(
                new GameEventLog(this.caseId, GameConfig.playerId, "Load file")
                .setResult(false)
            );
            return null;
        }

        QuestionList questions;
        try{
            questions = this.fileReader.readFile();
        } catch (java.io.FileNotFoundException e){
            System.err.println("Error: file not found - " + this.filepath);
            logger.log(
                new GameEventLog(this.caseId, GameConfig.playerId, "Load file")
                .setResult(false)
            );
            return null;
        }
        logger.log(
            new GameEventLog(this.caseId, GameConfig.playerId, "Load file")
            .setResult(true)
        );
        System.out.println("Questions loaded successfully.");
        questions.createQuestionGrid();     // initializes nested map for later formatting and printing
        return questions;
    }

    /**
     * Prompts user for number of players and validates input.
     * <p>
     * Validation Rules:
     * <ul>
     *   <li>Must be an integer between {@value #MIN_PLAYERS} and {@value #MAX_PLAYERS}</li>
     *   <li>Non-numeric input triggers re-prompt</li>
     *   <li>Out-of-range values trigger re-prompt with error message</li>
     * </ul>
     * Logs the selected player count upon successful validation.
     * </p>
     *
     * @throws java.util.InputMismatchException if input is not an integer (caught internally)
     * @throws IllegalArgumentException if number is outside valid range (caught internally)
     */
    private void setNumPlayers(){
        boolean validInput = false;
        while (!validInput) {
            try {
                System.out.print("Enter number of players: ");
                this.numPlayers = scanner.nextInt();
                scanner.nextLine();

                if(this.numPlayers >= MIN_PLAYERS && this.numPlayers <= MAX_PLAYERS) {
                    logger.log(
                        new GameEventLog(this.caseId, GameConfig.playerId, "Select player count")
                        .setAnswer("" + this.numPlayers)
                    );
                    validInput = true;
                } else {
                    throw new IllegalArgumentException("Number of players must be " + MIN_PLAYERS + "-" + MAX_PLAYERS + ".");
                }
            } catch (java.util.InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
    
    /**
     * Registers all players for the game session.
     * <p>
     * Process:
     * <ol>
     *   <li>Validate and set number of players via {@link #setNumPlayers()}</li>
     *   <li>Create {@link PlayerList} with specified capacity</li>
     *   <li>Prompt for each player's name and add to list</li>
     *   <li>Log each player registration event</li>
     * </ol>
     *
     * @return PlayerList containing all registered players
     * @see PlayerList#addPlayer(String)
     */
    public PlayerList addPlayers(){
        setNumPlayers();

        PlayerList players = new PlayerList(this.numPlayers);
        String playerId;

        for(int i=1; i<=this.numPlayers; i++){
            System.out.print("Enter player" + i + " name: ");
            playerId = scanner.nextLine();
            players.addPlayer(playerId);
            logger.log(
                new GameEventLog(this.caseId, playerId, "Enter player name")
                .setResult(true)
            );
        }
        return players;
    }
}
