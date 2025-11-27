package com.jeopardyProject.Game;

import java.util.Scanner;

import com.jeopardyProject.Game.Logs.GameEventLog;
import com.jeopardyProject.Game.Logs.Logger;
import com.jeopardyProject.Game.Template.*;

public class GameConfig {
    private static Scanner scanner = new Scanner(System.in);
    private FileReaderTemplate fileReader;
    private String filepath;
    private String caseId;
    private static String playerId = "System";
    private Logger logger = Logger.getInstance();
    private int numPlayers;

    public GameConfig(String caseId){
        this.caseId = caseId;
    }
    
    private void setFileReader(){
        boolean success = false;
        while(!success){
            System.out.print("Enter absolute filepath to load questions: ");
            this.filepath = scanner.nextLine();
            try{
                if(filepath.endsWith(".csv")){
                    this.fileReader = new CsvFileReader();
                    success=true;
                }
                else if(filepath.endsWith(".json")){
                    this.fileReader = new JsonFileReader();
                    success=true;
                }
                else if(filepath.endsWith(".xml")){
                    this.fileReader = new XmlFileReader();
                    success=true;
                }
                else 
                    throw new IllegalArgumentException("File format not supported.");
            } catch (Exception e) {
                System.out.println(filepath + " is invalid. Please reenter existing filepath.");
            }
        }
    }

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
            questions = this.fileReader.readFile(this.filepath);
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

    private void setNumPlayers(){
        System.out.print("Enter number of players: ");
        this.numPlayers = scanner.nextInt();
        scanner.nextLine();

        if(this.numPlayers >= 2 && this.numPlayers <=4)
            logger.log(
                new GameEventLog(this.caseId, GameConfig.playerId, "Select player count")
                .setAnswer("" + this.numPlayers)
            );
        else
            throw new IllegalArgumentException("Number of players must be 2-4.");
    }
    
    public PlayerList addPlayers(){
        try{
            setNumPlayers();
        } catch (IllegalArgumentException e){
            System.err.println("Error: " + e.getMessage()); // try to log error
            logger.log(
                new GameEventLog(this.caseId, GameConfig.playerId, "Select player count")
                .setResult(false)
            );
            System.exit(0);
            return null;
        }
        
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
