package com.jeopardyProject.Game.Logs;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import com.jeopardyProject.Game.Question;

import java.time.ZoneId;

public class GameEventLog {
    private String caseId;
    private String playerId;
    private String action;
    private static DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    private String timestamp;
    private Question question;
    private String category;
    private int value;
    private String answer;
    private boolean result;
    private int newScore;

    public GameEventLog(String caseId, String playerId, String action){
        this.caseId = caseId;
        this.playerId = playerId;
        this.action = action;
        this.timestamp = createTimestamp();
        this.result = true;
    }

    private String createTimestamp(){
        ZoneId zone = ZoneId.of("America/Port_of_Spain");
        LocalDateTime now = LocalDateTime.now(zone).truncatedTo(ChronoUnit.SECONDS);
        String timestamp = formatter.format(now);
        return timestamp;   // in the format yyyy-mm-ddTHH:mm:ss as shown in the sample log
    } 

    public GameEventLog setTimestamp(){
        this.timestamp = createTimestamp();
        return this;
    }

    public GameEventLog setQuestion(Question question){
        this.question = question;
        return this;
    }

    public GameEventLog setCategory(String category){
        this.category = category;
        return this;
    }
    
    public GameEventLog setValue(int value){
        this.value = value;
        return this;
    }

    public GameEventLog setResult(boolean result){
        this.result = result;
        return this;
    }

    public GameEventLog setAnswer(String answer){
        this.answer = answer;
        return this;
    }

    public GameEventLog setAnswerResultScore(String answer, boolean result, int newScore){
        this.answer = answer;
        this.result = result;
        this.newScore = newScore;
        return this;
    }

    public String getCaseId(){
        return this.caseId;
    }

    public String getPlayerId(){
        return this.playerId;
    }

    public String getAction(){
        return this.action;
    }

    public String getTimestamp(){
        return this.timestamp;
    }

    public Question getQuestion(){
        return this.question;
    }

    public String getCategory(){
        return this.category;
    }

    public int getValue(){
        return this.value;
    }

    public String getAnswer(){
        return this.answer;
    }

    public boolean getResult(){
        return this.result;
    }

    public int getNewScore(){
        return this.newScore;
    }

    public void printLog(){
        StringBuilder sb = new StringBuilder();
        sb.append("Case ID: ").append(caseId).append(" | ");
        sb.append("Player ID: ").append(playerId).append(" | ");
        sb.append("Action: ").append(action).append(" | ");
        sb.append("Timestamp: ").append(timestamp).append(" | ");
        
        if (category != null) {
            sb.append("Category: ").append(category).append(" | ");
        }
        if (value != 0) {
            sb.append("Value: ").append(value).append(" | ");
        }
        if (answer != null) {
            sb.append("Answer: ").append(answer).append(" | ");
        }
        sb.append("Result: ").append(result).append(" | ");
        if (newScore != 0) {
            sb.append("New Score: ").append(newScore);
        }
        
        System.out.println(sb.toString());
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Case ID: ").append(caseId).append(" | ");
        sb.append("Player ID: ").append(playerId).append(" | ");
        sb.append("Action: ").append(action).append(" | ");
        sb.append("Timestamp: ").append(timestamp).append(" | ");
        
        if (category != null) {
            sb.append("Category: ").append(category).append(" | ");
        }
        if (value != 0) {
            sb.append("Value: ").append(value).append(" | ");
        }
        if (answer != null) {
            sb.append("Answer: ").append(answer).append(" | ");
        }
        sb.append("Result: ").append(result).append(" | ");
        if (newScore != 0) {
            sb.append("New Score: ").append(newScore);
        }
        
        return sb.toString();
    }
}