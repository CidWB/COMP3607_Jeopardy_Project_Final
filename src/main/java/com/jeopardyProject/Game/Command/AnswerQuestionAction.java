package com.jeopardyProject.Game.Command;

import com.jeopardyProject.Game.GameController;
import com.jeopardyProject.Game.Question;

public class AnswerQuestionAction implements Action{
    private static GameController controller = GameController.getInstance();
    
    @Override
    public void execute(String input){
        Question question = controller.getQuestion();
        if(input.equalsIgnoreCase(question.getRightAnswer()))
            question.setIsAnswered(true);
    }
}
