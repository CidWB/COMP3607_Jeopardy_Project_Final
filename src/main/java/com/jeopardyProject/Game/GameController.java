package com.jeopardyProject.Game;

import java.util.Scanner;

import com.jeopardyProject.Game.Logs.GameEventLog;
import com.jeopardyProject.Game.Logs.Logger;

public class GameController {
    private static Logger logger = Logger.getInstance();
    private static GameController controller; 
    private static Scanner scanner = new Scanner(System.in);
    private static int gameCount = 0; 
    private GameConfig config;
    private String caseId;
    private static String playerId = "System";
    private QuestionList questions;
    private String category;
    private Question question;
    private PlayerList players;
    private Player currentPlayer;
    private int turnNum = 1;    // for turnReport file

    private GameController(){}

    public static GameController getInstance(){
        if (controller == null)
            controller = new GameController();
        return controller;
    }

    public void startGame(){
        gameCount++;    // increments for each new game
        String gameNumber = String.format("%03d", gameCount);   // formats to 3 digits
        this.caseId = "Game_" + gameNumber;
        this.config = new GameConfig(this.caseId);
        logger.log(
            new GameEventLog(this.caseId, GameController.playerId, "Start Game")
        );
    }

    public void setupGame(){
        this.questions = config.loadQuestions();
        if (this.questions == null)
            exitGame();
        this.players = config.addPlayers();
    }

    private String getUserInput(){
        String input = scanner.nextLine().trim();
        if ("QUIT".equalsIgnoreCase(input)){
            exitGame();
            return null;
        }
        return input.toUpperCase();
    }

    public String getCaseId(){
        return this.caseId;
    }

    public QuestionList getQuestions(){
        return this.questions;
    }

    public PlayerList getPlayers(){
        return this.players;
    }

    public void setCategory(String category){
        this.category = category;
    }

    public String getCategory(){
        return this.category;
    }

    public void setQuestion(Question question){
        this.question = question;
    }

    public Question getQuestion(){
        return this.question;
    }

    public void playGame(){
        String input;
        String currPlayerId;
        logger.initTurnReport();

        while (true){
            this.currentPlayer = this.players.getCurrentPlayer();
            currPlayerId = this.currentPlayer.getPlayerId();
            this.questions.display(); 
            System.out.println("Current player: " + this.currentPlayer.getPlayerId());
            System.out.println("Choose a Category. (You may input only the first word)");
            input = getUserInput();
            this.players.selectCategory(input);
            logger.log(
                new GameEventLog(this.caseId, this.currentPlayer.getPlayerId(), "Select Category")
                .setCategory(this.category)
            );

            System.out.println("Choose a value.");
            input = getUserInput();
            System.out.println("Value chosen");
            this.players.selectQuestion(input); 
            logger.log(
                new GameEventLog(this.caseId, currentPlayer.getPlayerId(), "Select Question")
                .setCategory(this.category)
                .setValue(Integer.valueOf(input)) // check
            );
            logger.addReportQuestion(turnNum);

            System.out.println("The question is: " + this.question.getContent());
            System.out.println("Select an option from the following:");
            this.question.printOptions();
            input = getUserInput();
            this.players.answerQuestion(input);
            this.players.endTurn(this.question.getValue(), this.question.getIsAnswered());
            
            logger.log(
            new GameEventLog(this.caseId, this.currentPlayer.getPlayerId(), "Answer Question")
            .setQuestion(this.question)
            .setCategory(this.category)
            .setValue(this.question.getValue())
            .setAnswerResultScore(input, this.question.getIsAnswered(), this.currentPlayer.getScore())   // currentPlayer not updated yet
            );
            logger.addReportAnswer();

            while(!this.question.getIsAnswered()){
                this.currentPlayer = players.getCurrentPlayer();        // players.endTurn() switches out other player
                System.out.println("Current player: " + this.currentPlayer.getPlayerId());
                System.out.println("Input your answer:");
                input = getUserInput();
                this.players.answerQuestion(input);
                this.players.endTurn(this.question.getValue(), this.question.getIsAnswered());
                
                logger.log(
                new GameEventLog(this.caseId, this.currentPlayer.getPlayerId(), "Answer Question")
                .setCategory(this.category)
                .setValue(this.question.getValue())
                .setAnswerResultScore(input, this.question.getIsAnswered(), this.currentPlayer.getScore()) 
                );
                logger.addReportAnswer();
                
                if(currPlayerId == this.players.getCurrentPlayer().getPlayerId())   // will only happen after everyone has gone
                    break;
            }
            turnNum++;
            if(!this.questions.hasQuestions()){
                System.out.println("Congrats! You made it to the end of the game.");
                exitGame();
            }
        }
    }
  
    public void exitGame(){
        String txtFileName = logger.generateTurnReport();
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

        System.out.println("The turn-by-turn summary report can be found in the file " + txtFileName);
        System.out.println("The game event log report can be found in the file " + csvFileName);
        System.out.println("Thanks for playing!");
        System.exit(0);
    }
}
