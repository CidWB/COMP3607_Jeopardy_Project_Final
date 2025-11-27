package com.jeopardyProject.Game.Logs;

import java.io.File;
import java.io.IOException;
// Singleton class
public class Logger {  
    private static Logger logger; 
    private static String csvFileName = "LogReport.csv";
    private static String txtFileName = "TurnReport.txt"; 
    private File csvFile;
    private File txtFile;
    private GameEventLog log;

    private Logger(){
        try{
            this.csvFile = new File(Logger.csvFileName);
            if(csvFile.createNewFile())
                System.out.println("CSV file created.");
            else{
                System.out.println("CSV file already exists.");
            }
                
        } catch (IOException e){
            System.err.println(e);
        }

        try{
            this.txtFile = new File(Logger.txtFileName);
            if(txtFile.createNewFile())
                System.out.println("TXT file created.");
            else{
                System.out.println("TXT file already exists.");
            }
                
        } catch (IOException e){
            System.err.println(e);
        }
    }

    public static Logger getInstance(){
        if (logger == null)
            logger = new Logger();
        return logger;
    } 

    private void updateCsv(){ // add actual logic
        System.out.println(this.log);
    }

    // Turn methods
    public void initTurnReport(){
        // write initial details eg:
        /*
        JEOPARDY PROGRAMMING GAME REPORT
        ================================

        Case ID: GAME001

        Players: Alice, Bob

        Gameplay Summary:
        -----------------
        */
    }

    public void addReportQuestion(int turn){
        // add current log as formatted string to .txt file
    }

    public void addReportAnswer(){
        // add current log as formatted string to .txt file
    }

    public String generateTurnReport(){
        // write final details eg:
        /*
        Final Scores:
        Alice: 500
        Bob: 700
        ================================
        END OF REPORT
        */
        
        // close file
        return Logger.txtFileName;
    }
    
    public String generateLogReport(){
        // close file
        return Logger.csvFileName;
    }

    // Best alternative I had to creating multiple method signatures with telescoping parameters
    // Does and doesn't handle GameEventLog creation but at least it's more customizable now
    public void log(GameEventLog log){
        this.log = log;
        updateCsv();
    /*  for example: 
        logger.log(
            new GameEventLog(String caseId, String playerId, String action)
            .setCategory(String category)
            .setValue(int value)
            .setAnswer(String answer)
            .setResult(boolean result)
            .setNewScore(int newScore)
        )
    */
    }

    public void printLog(){
        this.log.printLog();
    }


    // // Logs have different signatures depending on where logger is invoked
    // // System logs
    // public void logSystem(String caseId, String playerId, String action){     
    //     String timestamp = createTimestamp();
    //     GameEventLog log = new GameEventLog(caseId, playerId, timestamp, action);
    //     updateCsv(log);
    // }

    // public void logSystem(String caseId, String playerId, String action, boolean result){     
    //     String timestamp = createTimestamp();
    //     GameEventLog log = new GameEventLog(caseId, playerId, timestamp, action);
    //     log.setResult(result);
    //     updateCsv(log);
    // }

    // // Choose category
    // public void logPlayer(String caseId, String playerId, String action, String category){     
    //     String timestamp = createTimestamp();
    //     GameEventLog log = new GameEventLog(caseId, playerId, timestamp, action);
    //     log.setCategory(category);
    //     updateCsv(log);
    // }

    // // Choose question
    // public void logPlayer(String caseId, String playerId, String action, String category, int value){     
    //     String timestamp = createTimestamp();
    //     GameEventLog log = new GameEventLog(caseId, playerId, timestamp, action);
    //     log.setCategory(category);
    //     log.setValue(value);
    //     updateCsv(log);
    // }

    // // Answer question
    // public void logPlayer(String caseId, String playerId, String action, String category, int value, String answer, boolean result, int newScore){     
    //     String timestamp = createTimestamp();
    //     GameEventLog log = new GameEventLog(caseId, playerId, timestamp, action);
    //     log.setCategory(category);
    //     log.setValue(value);
    //     log.setAnswerResultScore(answer, result, newScore);
    //     updateCsv(log);
    // }
}
