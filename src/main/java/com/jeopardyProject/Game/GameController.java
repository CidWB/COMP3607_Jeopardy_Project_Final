package com.jeopardyProject.Game;

import java.util.Scanner;

import com.jeopardyProject.Game.Player;
import com.jeopardyProject.Game.Logs.GameEventLog;
import com.jeopardyProject.Game.Logs.Logger;

public class GameController {
    private static Logger logger = Logger.getInstance(); // handles log generation
    private static GameController controller; 
    private static Scanner scanner = new Scanner(System.in);
    private GameConfig config;
    private String caseId = "Game_";
    private static String playerId = "System";
    private QuestionList questions;
    private String category;
    private Question question;
    private boolean questionAnswered;
    private PlayerList players;
    private Player currentPlayer;
    private int turnNum;

    private GameController(){}

    public static GameController getInstance(){
        if (controller == null)
            controller = new GameController();
        return controller;
    }

    public void startGame(){
        this.caseId = this.caseId + "001";  // figure out how to increment with each game
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
        String input = scanner.nextLine().toUpperCase();
        if (input == "QUIT"){
            exitGame();
            return null;    // maybe not necessary
        }
        return input;
    }

    public QuestionList getQuestions(){
        return this.questions;
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

    public void setQuestionAnswered(boolean bool){
        this.questionAnswered = bool;
    }

    public void playGame(){
        String input;

        while (true){
            this.currentPlayer = this.players.getCurrentPlayer();
            this.questions.display();       // select category
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
            this.players.selectQuestion(input); 
            logger.log(
                new GameEventLog(this.caseId, currentPlayer.getPlayerId(), "Select Question")
                .setCategory(this.category)
                .setValue(this.question.getValue())
            );

            System.out.println("The question is: " + this.question.getContent());
            System.out.println("Select an option from the following:");
            this.question.printOptions();
            input = getUserInput();
            this.players.answerQuestion(input);

            this.players.endTurn(this.question.getValue(), this.questionAnswered);
            logger.log(
            new GameEventLog(this.caseId, this.currentPlayer.getPlayerId(), "Answer Question")
            .setCategory(this.category)
            .setValue(this.question.getValue())
            .setAnswerResultScore(input, this.questionAnswered, this.currentPlayer.getScore())   // currentPlayer not updated yet
            );

            while(!this.questionAnswered){
                this.currentPlayer = players.getCurrentPlayer();        // players.endTurn() switches out other player
            }
            
            

        }
    }

    /*
    GAME LOOP FOR 1 TURN
    

    while (input != "QUIT" || all questions not answered){      // build turn
        playerList.selectCategory();{
            player.setState()
            player.request()
        }
        logger.log()

        player.selectQuestion();
        logger.log()
        logger.addReportQuestion()

        while (count <= numPlayers || question.getIsAnswered == false){    // either loop back to first player or answer correctly
            player.answerQuestion();
            player.updateScore();
            logger.log()
            logger.addReportAnswer()
            changePlayer();
            count ++;       // starts at 1
        }   
        question.setIsAnswered(true);   
    }
    
    */
    

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
    }
}
