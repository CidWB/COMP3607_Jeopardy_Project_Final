package com.jeopardyProject.Game.Logs;

import java.io.*;
import java.util.ArrayList;

import com.jeopardyProject.Game.Player;

/**
 * Singleton logger for generating game reports in CSV and TXT formats.
 * <p>
 * This class manages two types of reports:
 * <ul>
 *   <li><b>LogReport.csv</b>: Timestamped event log with all game actions</li>
 *   <li><b>TurnReport.txt</b>: Human-readable turn-by-turn gameplay summary</li>
 * </ul>
 * Both files are created in the project root directory.
 * <p>
 * The logger uses thread-safe double-checked locking for singleton initialization
 * and provides methods for logging events, building turn reports, and generating
 * final output files.
 *
 * @see GameEventLog
 * @author COMP3607 Jeopardy Project Team
 * @version 1.0
 */
public class Logger{
    /** Singleton instance (thread-safe). */
    private static Logger logger;

    /** Default filename for CSV event log. */
    private static String csvFileName = "LogReport.csv";

    /** Default filename for TXT turn report. */
    private static String txtFileName = "TurnReport.txt";

    /** File object for CSV report. */
    private File csvFile;

    /** File object for TXT report. */
    private File txtFile;

    /** BufferedWriter for writing to TXT report. */
    private BufferedWriter writer;

    /** BufferedWriter for writing to CSV report. */
    private BufferedWriter csvWriter;

    /** Current log entry being processed. */
    private GameEventLog log;

    /**
     * Private constructor to enforce Singleton pattern.
     * <p>
     * Initializes CSV and TXT files. If files don't exist, creates them.
     * Writes CSV header row on new file creation.
     * </p>
     */
    private Logger(){
        try{
            this.csvFile = new File(Logger.csvFileName);
            boolean isNewFile = csvFile.createNewFile();
            if(isNewFile)
                System.out.println("CSV file created.");
            else{
                System.out.println("CSV file already exists.");
            }

            csvWriter = new BufferedWriter(new FileWriter(csvFile, true));
            if(isNewFile){
                csvWriter.write("Case ID,Timestamp,Player ID,Event Type,Category,Value,Question,Answer,Result,Score");
                csvWriter.newLine();
                csvWriter.flush();
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

    /**
     * Returns the singleton Logger instance using double-checked locking.
     * <p>
     * Thread-safe lazy initialization ensures only one logger exists.
     * </p>
     *
     * @return the singleton Logger instance
     */
    public static Logger getInstance(){
        if (logger == null) {
            synchronized (Logger.class) {
                if (logger == null) {
                    logger = new Logger();
                }
            }
        }
        return logger;
    }

    /**
     * Clears and reinitializes both report files for a new game session.
     * <p>
     * Closes existing writers, deletes old files, creates new files,
     * and writes CSV header row. Used at the start of each game to
     * ensure clean reports.
     * </p>
     */
    public void clearReports(){
        try{
            if(csvWriter!=null){
                try {
                    csvWriter.close();
                } catch (IOException e) {
                    System.err.println("Error closing CSV writer: " + e.getMessage());
                } finally {
                    csvWriter = null;
                }
            }
            if(writer!=null){
                try {
                    writer.close();
                } catch (IOException e) {
                    System.err.println("Error closing TXT writer: " + e.getMessage());
                } finally {
                    writer = null;
                }
            }

            if(csvFile.exists())
                csvFile.delete();
            if(txtFile.exists())
                txtFile.delete();

            csvFile.createNewFile();
            txtFile.createNewFile();

            csvWriter = new BufferedWriter(new FileWriter(csvFile, true));
            csvWriter.write("Case ID,Timestamp,Player ID,Event Type,Category,Value,Question,Answer,Result,Score");
            csvWriter.newLine();
            csvWriter.flush();

        } catch (IOException e){
            System.err.println("Error clearing reports: " + e.getMessage());
        }
    } 

    /**
     * Writes the current log entry to the CSV file.
     * <p>
     * Formats the entry as a CSV row with proper escaping for commas,
     * quotes, and newlines. Flushes immediately to ensure data persistence.
     * </p>
     */
    private void updateCsv(){
        try{
            if(csvWriter==null){
                csvWriter = new BufferedWriter(new FileWriter(csvFile, true));
            }

            StringBuilder sb = new StringBuilder();
            sb.append(escapeCsv(this.log.getCaseId())).append(",");
            sb.append(escapeCsv(this.log.getTimestamp())).append(",");
            sb.append(escapeCsv(this.log.getPlayerId())).append(",");
            sb.append(escapeCsv(this.log.getAction())).append(",");
            sb.append(escapeCsv(this.log.getCategory())).append(",");
            sb.append(this.log.getValue()).append(",");
            sb.append(escapeCsv(this.log.getQuestion()!=null ? this.log.getQuestion().getContent() : "")).append(",");
            sb.append(escapeCsv(this.log.getAnswer())).append(",");
            sb.append(this.log.getResult()).append(",");
            sb.append(this.log.getNewScore());

            csvWriter.write(sb.toString());
            csvWriter.newLine();
            csvWriter.flush();
        } catch (IOException e){
            System.err.println("Error writing to CSV: " + e.getMessage());
        }
    }

    /**
     * Escapes special characters in CSV values.
     * <p>
     * Wraps values in quotes if they contain commas, quotes, or newlines.
     * Doubles internal quotes per CSV spec.
     * </p>
     *
     * @param value the string to escape
     * @return escaped CSV value, or empty string if null
     */
    private String escapeCsv(String value){
        if(value==null)
            return "";
        if(value.contains(",") || value.contains("\"") || value.contains("\n")){
            return "\"" + value.replace("\"", "\"\"") + "\"";
        }
        return value;
    }

    /**
     * Initializes the turn report file with header information.
     * <p>
     * Writes the game title, case ID, player list, and section header
     * to TurnReport.txt. Should be called before logging any turns.
     * </p>
     *
     * @param caseId unique game session identifier
     * @param players list of all players in the game
     */
    public void initTurnReport(String caseId, ArrayList<Player> players){
        try{
            if(writer==null)
                writer = new BufferedWriter(new FileWriter("TurnReport.txt", true));
            writer.write("JEOPARDY PROGRAMMING GAME REPORT");
            writer.newLine();
            writer.write("================================");
            writer.newLine();

            if(caseId!=null)
                writer.write("Case ID: " + caseId);
            else
                writer.write("Case ID: GAME_001");
            writer.newLine();

            StringBuilder sb = new StringBuilder();
            for(Player player : players){
                sb.append(player.getPlayerId() + ", ");
            }
            writer.write("Players: " + sb);
            writer.newLine();
            writer.write("Gameplay Summary:");
            writer.newLine();
            writer.write("================================");
            writer.flush();

        } catch (IOException e) {
            System.out.println("Report.txt could not be opened.");
        }
    }

    /**
     * Appends question selection information to the turn report.
     * <p>
     * Logs which player selected which category and value for the given turn.
     * Uses data from the current log entry.
     * </p>
     *
     * @param turn the turn number to display
     */
    public void addReportQuestion(int turn){
        try{
            if(writer==null)
                writer = new BufferedWriter(new FileWriter("TurnReport.txt", true));
            writer.newLine();
            writer.newLine();
            writer.write("Turn " + turn + ": " + this.log.getPlayerId() + " selected " + this.log.getCategory() + " for " + this.log.getValue() + " pts");
            writer.newLine();
            if(this.log.getQuestion() != null) {
                writer.write("Question: " + this.log.getQuestion().getContent());
                writer.newLine();
            }
            writer.flush();
        } catch (IOException e) {
            System.out.println("Report.txt could not be opened for addReportQuestion.");
        }
    }

    /**
     * Appends answer results to the turn report.
     * <p>
     * Logs the answer text, whether it was correct/incorrect,
     * point adjustment, and updated player score.
     * </p>
     */
    public void addReportAnswer(){
        try{
            if(writer==null)
                writer = new BufferedWriter(new FileWriter("TurnReport.txt", true));
            if(this.log.getQuestion() != null) {
                String correctAnswer = this.log.getQuestion().getValueGivenKey(this.log.getAnswer());
                writer.write("Answer: " + correctAnswer + " - ");
                if(this.log.getResult()==true)
                    writer.write("Correct (+" + this.log.getValue() + " pts)");
                else
                    writer.write("Incorrect (-" + this.log.getValue() + " pts)");

                writer.newLine();
                writer.write("Score after turn:" + this.log.getPlayerId() + " = " + this.log.getNewScore());
            }
            writer.flush();

        } catch (IOException e) {
            System.out.println("Report.txt could not be opened for addReportAnswer().");
        }
    }

    /**
     * Finalizes and closes the turn report with final scores.
     * <p>
     * Writes the final scores section, footer, and closes the file.
     * Returns the filename for user display.
     * </p>
     *
     * @param players list of all players with final scores
     * @return the TXT filename ("TurnReport.txt")
     */
    public String generateTurnReport(ArrayList<Player> players){
        try{
            if(writer==null)
                writer = new BufferedWriter(new FileWriter("TurnReport.txt", true));
            writer.newLine();
            writer.write("Final Scores:");
            writer.newLine();

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
            writer = null;

        } catch (IOException e) {
            System.out.println("Report.txt could not be written: " + e.getMessage());
        }
        return Logger.txtFileName;
    }
    
    /**
     * Finalizes and closes the CSV event log.
     * <p>
     * Flushes and closes the CSV writer. Returns the filename for user display.
     * </p>
     *
     * @return the CSV filename ("LogReport.csv")
     */
    public String generateLogReport(){
        try{
            if(csvWriter!=null){
                csvWriter.flush();
                csvWriter.close();
                csvWriter = null;
            }
        } catch (IOException e){
            System.err.println("Error closing CSV: " + e.getMessage());
        }
        return Logger.csvFileName;
    }

    /**
     * Logs a game event to the CSV report.
     * <p>
     * Accepts a fully-constructed {@link GameEventLog} and writes it
     * to the CSV file immediately (with flush).
     * </p>
     *
     * @param log the GameEventLog to write
     * @see GameEventLog
     */
    public void log(GameEventLog log){
        this.log = log;
        updateCsv();
    }
}
