package com.jeopardyProject.Game.Command;

import java.util.ArrayList;

import com.jeopardyProject.Game.GameController;
import com.jeopardyProject.Game.Question;

public class SelectCategoryAction implements Action{
    private static GameController controller = GameController.getInstance();

    @Override
    public void execute(String input){
        ArrayList<Question> questions = controller.getQuestions().getQuestionArray();
        for (Question question : questions){
            String category = question.getCategory().toUpperCase();
            if (category.startsWith(input))
                System.out.println("Category selected: " + category.toLowerCase());
                controller.setCategory(category);
        }
    }
}
