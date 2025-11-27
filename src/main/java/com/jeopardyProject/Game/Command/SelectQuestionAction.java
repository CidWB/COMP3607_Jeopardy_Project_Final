package com.jeopardyProject.Game.Command;

import java.util.ArrayList;

import com.jeopardyProject.Game.GameController;
import com.jeopardyProject.Game.Question;

public class SelectQuestionAction implements Action{
    private static GameController controller = GameController.getInstance();
    
    @Override
    public void execute(String input){
        String category = controller.getCategory();
        ArrayList<Question> sortedQuestions = controller.getQuestions().getQuestionsByCategory(category);
        for (Question question : sortedQuestions){
            if (Integer.valueOf(input).intValue() == question.getValue()){
                System.out.println("Value selected: " + input.toLowerCase());
                controller.setQuestion(question);
                return;
            } 
        }
    }
}
