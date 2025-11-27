package com.jeopardyProject.Game.Logs;

import java.io.*;
import java.util.ArrayList;

import com.jeopardyProject.Game.GameController;
import com.jeopardyProject.Game.Player;

// Singleton class
public class Logger{  
    private static Logger logger; 
    private static GameController controller = GameController.getInstance();
    private static String csvFileName = "LogReport.csv";
    private static String txtFileName = "TurnReport.txt"; 
    private File csvFile;
    private File txtFile;
    BufferedWriter writer; 
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
        try{
            if(writer==null)
                writer = new BufferedWriter(new FileWriter("TurnReport.txt"));
            writer.write("JEOPARDY PROGRAMMING GAME REPORT");
            writer.newLine();
            writer.write("================================");
            writer.newLine();

            if(controller!=null)
                writer.write("Case ID: " + controller.getCaseId());
            else
                writer.write("Case ID: GAME_001");
            writer.newLine();

            ArrayList<Player> players = controller.getPlayers().getAllPlayers();
            StringBuilder sb = new StringBuilder();
            for(Player player : players){
                sb.append(player.getPlayerId() + ", ");
            }
            writer.write("Players: " + sb);
            writer.write("Gameplay Summary:");
            writer.newLine();
            writer.write("================================");

        } catch (IOException e) {
            System.out.println("Report.txt could not be opened.");
        }
    }

    public void addReportQuestion(int turn){
        try{
            if(writer==null)
                writer = new BufferedWriter(new FileWriter("TurnReport.txt"));
            writer.newLine();
            writer.newLine();
            writer.write("Turn " + turn + ": " + this.log.getPlayerId() + " selected " + this.log.getCategory() + " for " + this.log.getValue() + " pts");
            writer.newLine();
            writer.write("Question: " + this.log.getQuestion().getContent());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Report.txt could not be opened for addReportQuestion.");
        }
    }

    public void addReportAnswer(){
        try{
            if(writer==null)
                writer = new BufferedWriter(new FileWriter("TurnReport.txt"));
            String correctAnswer = this.log.getQuestion().getValueGivenKey(this.log.getAnswer());
            writer.write("Answer: " + correctAnswer + " - ");
            if(this.log.getResult()==true)
                writer.write("Correct (+" + this.log.getValue() + " pts");
            else
                writer.write("Incorrect (-" + this.log.getValue() + " pts");

            writer.newLine();
            writer.write("Score after turn:" + this.log.getPlayerId() + " = " + this.log.getNewScore());

        } catch (IOException e) {
            System.out.println("Report.txt could not be opened for addReportAnswer().");
        }
    }

    public String generateTurnReport(){
        try{
            if(writer==null)
                writer = new BufferedWriter(new FileWriter("TurnReport.txt"));
            writer.newLine();
            writer.write("Final Scores:");
            writer.newLine();

            ArrayList<Player> players = controller.getPlayers().getAllPlayers();
            for (Player player : players) {
                writer.write(player.getPlayerId() + ": " + player.getScore());
                writer.newLine();
            }

            writer.write("================================");
            writer.newLine();
            writer.write("END OF REPORT");
            writer.newLine();
            writer.flush();
            writer.close();

        } catch (IOException e) {
            System.out.println("Report.txt could not be written: " + e.getMessage());
        }
        return Logger.txtFileName;
    }
    
    public String generateLogReport(){
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
