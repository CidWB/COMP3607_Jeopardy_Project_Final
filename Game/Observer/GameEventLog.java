package Observer;
import Command.Action;
import Game.Question; // fix

public class GameEventLog {
    private int caseId;
    private String playerId;
    private Action action;
    private String timestamp;
    private String category;
    private int value;
    private String content; // check if this is needed
    private char answer;
    private boolean isCorrect;
    private int newScore;

    public GameEventLog(int caseId, String playerId, String timestamp, Action action){
        this.caseId = caseId;
        this.playerId = playerId;
        this.timestamp = timestamp;
        this.action = action;
    }

    public void setCategory(String category){
        this.category = category;
    }
    
    public void setValue(int value){
        this.value = value;
    }

    public void setAnswer(char answer){
        this.answer = answer;
    }

    public void setScore(int score){
        this.newScore = score;
    }

    public void displayLog(){
        System.out.println("Case ID: " + this.caseId);
        System.out.println("Player ID: " + this.playerId);
        System.out.println("Timestamp: " + this.timestamp);
        System.out.println("Action: " + this.action.toString());
            
        // add other actions for System
        
        if(this.action instanceof SelectCategoryAction){
            System.out.println("Question category: " + this.category);
        }   

        if(this.action instanceof SelectQuestionAction){
            System.out.println("Question value: " + this.value);
        } 

        if(this.action instanceof AnswerQuestionAction){
            System.out.println("Answer given: " + this.answer);
            System.out.println("Result: " + this.isCorrect);
            System.out.println("New score: " + this.newScore);
        } 
    }
}