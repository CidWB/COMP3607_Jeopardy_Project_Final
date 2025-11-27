package com.jeopardyProject;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;

import com.jeopardyProject.Game.Command.AnswerQuestionAction;
import com.jeopardyProject.Game.GameController;
import com.jeopardyProject.Game.Question;

public class AnswerQuestionActionTest {
    private GameController controller = GameController.getInstance();
    private AnswerQuestionAction action = new AnswerQuestionAction();

    @Test
    void testCorrectAnswer(){
        HashMap<String,String> options = new HashMap<>();
        options.put("A","Option A"); 
        options.put("B","Option B"); 
        options.put("C","Option C"); 
        options.put("D","Option D");
        Question question = new Question("Arrays", 300, "Q", options, "A");
        controller.setQuestion(question);

        Question q = controller.getQuestion();
        assertFalse(q.getIsAnswered());
        action.execute("A");
        assertTrue(q.getIsAnswered());
    }

    void testIncorrectAnswer(){
        HashMap<String,String> options = new HashMap<>();
        options.put("A","Option A"); 
        options.put("B","Option B"); 
        options.put("C","Option C"); 
        options.put("D","Option D");
        Question question = new Question("Arrays", 300, "Q", options, "A");
        controller.setQuestion(question);

        Question q = controller.getQuestion();
        assertTrue(q.getIsAnswered());
        action.execute("B");
        assertFalse(q.getIsAnswered());
    }
}
