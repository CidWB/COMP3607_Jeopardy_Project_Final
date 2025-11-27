package com.jeopardyProject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.HashMap;

import com.jeopardyProject.Game.Command.SelectQuestionAction;
import com.jeopardyProject.Game.GameController;
import com.jeopardyProject.Game.Question;
import com.jeopardyProject.Game.QuestionList;

public class SelectQuestionActionTest {
    private GameController controller;
    private SelectQuestionAction action;
    private Question q1;
    private Question q2;
    private Question q3;
    private QuestionList questions;

    @BeforeEach
    void setUp() throws Exception{
        GameController.reset();
        controller = GameController.getInstance();
        action = new SelectQuestionAction(controller);

        questions = new QuestionList();
        HashMap<String,String> options = new HashMap<>();
        options.put("A","a");
        options.put("B","b");
        options.put("C","c");
        options.put("D","d");
        q1 = new Question("Functions", 100, "Q1", options, "A");
        q2 = new Question("Functions", 200, "Q2", options, "B");
        q3 = new Question("Arrays", 300, "Q3", options, "C");
        questions.addQuestion(q1);
        questions.addQuestion(q2);
        questions.addQuestion(q3);
        controller.setQuestions(questions);
        controller.setCategory("Functions");
    }

    @Test
    void testSelectQuestionByValue(){
        action.execute("100");
        Question selected = controller.getQuestion();
        assertNotNull(selected);
        assertEquals(100, selected.getValue());
    }

    @Test
    void testSelectNullQuestion(){
        controller.setQuestion(null);
        action.execute("500");
        assertNull(controller.getQuestion());
    }

    @Test
    void testSelectQuestionByDifferentValues(){
        action.execute("100");
        assertEquals(100, controller.getQuestion().getValue());

        action.execute("200");
        assertEquals(200, controller.getQuestion().getValue());
    }

    @Test
    void testSelectQuestionInvalidValue(){
        controller.setQuestion(null);
        action.execute("999");
        assertNull(controller.getQuestion());
    }

    @Test
    void testSelectQuestionNonNumericInput(){
        controller.setQuestion(null);
        action.execute("abc");
        assertNull(controller.getQuestion());
    }

    @Test
    void testSelectQuestionEmptyString(){
        controller.setQuestion(null);
        action.execute("");
        assertNull(controller.getQuestion());
    }

    @Test
    void testSelectQuestionNullInput(){
        controller.setQuestion(q1);
        action.execute(null);
        assertEquals(q1, controller.getQuestion());
    }

    @Test
    void testSelectQuestionNegativeValue(){
        controller.setQuestion(null);
        action.execute("-100");
        assertNull(controller.getQuestion());
    }

    @Test
    void testSelectQuestionZeroValue(){
        controller.setQuestion(null);
        action.execute("0");
        assertNull(controller.getQuestion());
    }

    @Test
    void testSelectQuestionMatchesCategory(){
        controller.setCategory("Functions");
        action.execute("100");
        Question selected = controller.getQuestion();
        assertNotNull(selected);
        assertEquals("Functions", selected.getCategory());
    }

    @Test
    void testSelectQuestionFromDifferentCategory(){
        controller.setCategory("Arrays");
        action.execute("300");
        Question selected = controller.getQuestion();
        assertNotNull(selected);
        assertEquals("Arrays", selected.getCategory());
        assertEquals(300, selected.getValue());
    }

    @Test
    void testSelectQuestionAlreadyAnswered(){
        q1.setIsAnswered(true);
        controller.setQuestion(null);
        action.execute("100");
        assertNull(controller.getQuestion());
    }

    @Test
    void testSelectQuestionOnlyUnanswered(){
        q1.setIsAnswered(true);
        action.execute("200");
        Question selected = controller.getQuestion();
        assertNotNull(selected);
        assertEquals(200, selected.getValue());
        assertFalse(selected.getIsAnswered());
    }

    @Test
    void testSelectQuestionWithWhitespace(){
        controller.setQuestion(null);
        action.execute(" 100 ");
        assertNull(controller.getQuestion());
    }

    @Test
    void testSelectQuestionPreservesCategory(){
        String category = controller.getCategory();
        action.execute("100");
        assertEquals(category, controller.getCategory());
    }
}
