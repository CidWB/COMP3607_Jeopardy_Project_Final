package com.jeopardyProject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.HashMap;

import com.jeopardyProject.Game.Command.SelectCategoryAction;
import com.jeopardyProject.Game.GameController;
import com.jeopardyProject.Game.Question;
import com.jeopardyProject.Game.QuestionList;

public class SelectCategoryActionTest {
    private GameController controller;
    private SelectCategoryAction action;
    private QuestionList questions;

    @BeforeEach
    void setUp() throws Exception{
        GameController.reset();
        controller = GameController.getInstance();
        action = new SelectCategoryAction(controller);

        questions = new QuestionList();
        HashMap<String,String> options = new HashMap<>();
        options.put("A","a");
        options.put("B","b");
        options.put("C","c");
        options.put("D","d");
        Question q1 = new Question("Variables & Data Types", 100, "Q1", options, "A");
        Question q2 = new Question("Control Structures", 200, "Q2", options, "B");
        Question q3 = new Question("Functions", 300, "Q3", options, "A");
        questions.addQuestion(q1);
        questions.addQuestion(q2);
        questions.addQuestion(q3);
        controller.setQuestions(questions);
    }

    @Test
    void testSelectCategoryMatchesStartOfWord(){
        action.execute("VAR");
        assertEquals("Variables & Data Types", controller.getCategory());
    }

    @Test
    void testSelectCategoryNoMatch(){
        controller.setCategory(null);
        action.execute("NonExisting");
        assertNull(controller.getCategory());
    }

    @Test
    void testSelectCategoryExactMatch(){
        action.execute("FUNCTIONS");
        assertEquals("Functions", controller.getCategory());
    }

    @Test
    void testSelectCategoryPartialMatch(){
        action.execute("CONTROL");
        assertEquals("Control Structures", controller.getCategory());
    }


    @Test
    void testSelectCategoryWithSpaces(){
        action.execute("VARIABLES & DATA");
        assertEquals("Variables & Data Types", controller.getCategory());
    }

    @Test
    void testSelectCategoryMultipleCategories(){
        action.execute("VARIABLES");
        assertEquals("Variables & Data Types", controller.getCategory());

        action.execute("CONTROL");
        assertEquals("Control Structures", controller.getCategory());

        action.execute("FUNCTIONS");
        assertEquals("Functions", controller.getCategory());
    }

    @Test
    void testSelectCategoryConsecutiveCalls(){
        action.execute("VARIABLES");
        String first = controller.getCategory();

        action.execute("FUNCTIONS");
        String second = controller.getCategory();

        assertNotEquals(first, second);
        assertEquals("Functions", second);
    }

    @Test
    void testSelectCategoryInvalidInput(){
        controller.setCategory(null);
        action.execute("XYZ123");
        assertNull(controller.getCategory());
    }

    @Test
    void testSelectCategoryWithSpecialCharacters(){
        action.execute("VARIABLES &");
        assertEquals("Variables & Data Types", controller.getCategory());
    }

    @Test
    void testSelectCategorySingleChar(){
        action.execute("F");
        assertEquals("Functions", controller.getCategory());
    }

    @Test
    void testSelectCategoryNullInput(){
        controller.setCategory("Variables & Data Types");
        action.execute(null);
        assertEquals("Variables & Data Types", controller.getCategory());
    }

    @Test
    void testSelectCategoryPreservesQuestions(){
        action.execute("VARIABLES");
        assertNotNull(controller.getQuestions());
        assertEquals(3, controller.getQuestions().getQuestionArray().size());
    }
}
