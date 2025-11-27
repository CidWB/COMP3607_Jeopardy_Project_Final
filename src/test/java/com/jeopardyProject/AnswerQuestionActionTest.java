package com.jeopardyProject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;

import com.jeopardyProject.Game.Command.AnswerQuestionAction;
import com.jeopardyProject.Game.GameController;
import com.jeopardyProject.Game.Question;

public class AnswerQuestionActionTest {
    private GameController controller;
    private AnswerQuestionAction action;

    @BeforeEach
    void setUp(){
        GameController.reset();
        controller = GameController.getInstance();
        action = new AnswerQuestionAction(controller);
    }

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

    @Test
    void testIncorrectAnswer(){
        HashMap<String,String> options = new HashMap<>();
        options.put("A","Option A");
        options.put("B","Option B");
        options.put("C","Option C");
        options.put("D","Option D");
        Question question = new Question("Arrays", 300, "Q", options, "A");
        controller.setQuestion(question);

        Question q = controller.getQuestion();
        assertFalse(q.getIsAnswered());
        action.execute("B");
        assertFalse(q.getIsAnswered());
    }

    @Test
    void testAnswerMarksQuestionAsAnswered(){
        HashMap<String,String> options = new HashMap<>();
        options.put("A","ans");
        Question question = new Question("Test", 100, "Q", options, "A");
        controller.setQuestion(question);

        assertFalse(question.getIsAnswered());
        action.execute("A");
        assertTrue(question.getIsAnswered());
    }

    @Test
    void testWrongAnswerDoesNotMarkAsAnswered(){
        HashMap<String,String> options = new HashMap<>();
        options.put("A","ans");
        options.put("B","wrong");
        Question question = new Question("Test", 100, "Q", options, "A");
        controller.setQuestion(question);

        action.execute("B");
        assertFalse(question.getIsAnswered());
    }

    @Test
    void testEmptyAnswerDoesNotMarkAsAnswered(){
        HashMap<String,String> options = new HashMap<>();
        options.put("A","ans");
        Question question = new Question("Test", 100, "Q", options, "A");
        controller.setQuestion(question);

        action.execute("");
        assertFalse(question.getIsAnswered());
    }

    @Test
    void testInvalidAnswerDoesNotMarkAsAnswered(){
        HashMap<String,String> options = new HashMap<>();
        options.put("A","ans");
        Question question = new Question("Test", 100, "Q", options, "A");
        controller.setQuestion(question);

        action.execute("Z");
        assertFalse(question.getIsAnswered());
    }

    @Test
    void testCaseInsensitiveAnswer(){
        HashMap<String,String> options = new HashMap<>();
        options.put("A","ans");
        Question question = new Question("Test", 100, "Q", options, "A");
        controller.setQuestion(question);

        action.execute("a");
        assertTrue(question.getIsAnswered());
    }

    @Test
    void testMultipleAnswersOnSameQuestion(){
        HashMap<String,String> options = new HashMap<>();
        options.put("A","ans");
        options.put("B","wrong");
        Question question = new Question("Test", 100, "Q", options, "A");
        controller.setQuestion(question);

        action.execute("B");
        assertFalse(question.getIsAnswered());
        action.execute("A");
        assertTrue(question.getIsAnswered());
    }
}
