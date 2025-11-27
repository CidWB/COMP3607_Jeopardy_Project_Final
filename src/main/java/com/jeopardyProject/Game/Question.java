package com.jeopardyProject.Game;

import java.util.HashMap;

public class Question {
    private String category;
    private int value;
    private String content;
    private HashMap<String, String> options;
    private String correctAnswer;
    private boolean isAnswered;

    public Question(String category, int value, String content, HashMap<String, String> options, String rightAnswer) {
        this.category = category;
        this.value = value;
        this.content = content;
        this.options = options;
        this.correctAnswer = rightAnswer;
        this.isAnswered = false;
    }

    public String getCategory() {
        return this.category;
    }   

    public int getValue() {
        return this.value;
    }
    
    public String getContent() {
        return this.content;
    }

    public HashMap<String, String> getOptions(){
        return this.options;
    }

    public String getValueGivenKey(String key){
        return this.options.get(key);
    }
    
    public String getRightAnswer() {
        return this.correctAnswer;
    }

    public boolean getIsAnswered() {
        return this.isAnswered;
    }
    
    public void setIsAnswered(boolean answered) {
        this.isAnswered = answered;
    } 

    public void printOptions(){
        for (String option: options.keySet()) {
            String answer = options.get(option);
            System.out.println(option + " " + answer);
        }
    }
}
