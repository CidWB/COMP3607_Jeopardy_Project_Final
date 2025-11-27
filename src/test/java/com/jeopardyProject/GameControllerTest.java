package com.jeopardyProject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;

import com.jeopardyProject.Game.GameController;
import com.jeopardyProject.Game.Question;
import com.jeopardyProject.Game.QuestionList;

public class GameControllerTest {
    private GameController controller;

    @BeforeEach
    void setUp(){
        GameController.reset();
        controller = GameController.getInstance();
    }

    @Test
    void testGetInstance(){
        assertNotNull(controller);
    }

    @Test
    void testGetInstanceSingleton(){
        GameController controller2 = GameController.getInstance();
        assertSame(controller, controller2);
    }

    @Test
    void testGetInstanceMultipleCalls(){
        GameController c1 = GameController.getInstance();
        GameController c2 = GameController.getInstance();
        GameController c3 = GameController.getInstance();

        assertSame(c1, c2);
        assertSame(c2, c3);
    }

    @Test
    void testStartGame(){
        controller.startGame();
        String caseId = controller.getCaseId();
        assertNotNull(caseId);
    }

    @Test
    void testStartGameIncrementsCaseId(){
        controller.startGame();
        String firstId = controller.getCaseId();
        assertEquals("Game_001", firstId);
    }

    @Test
    void testGetCaseId(){
        controller.startGame();
        String caseId = controller.getCaseId();
        assertNotNull(caseId);
        assertEquals("Game_001", caseId);
    }

    @Test
    void testSetCategoryAndGetCategory(){
        String testCategory = "Science";
        controller.setCategory(testCategory);
        assertEquals(testCategory, controller.getCategory());
    }

    @Test
    void testSetQuestionAndGetQuestion(){
        HashMap<String, String> options = new HashMap<>();
        options.put("A", "Option A");
        options.put("B", "Option B");
        Question question = new Question("Science", 200, "TestContent", options, "A");
        controller.setQuestion(question);
        assertEquals(question, controller.getQuestion());
    }

    @Test
    void testMultipleGamesIncrementCaseId(){
        controller.startGame();
        String id1 = controller.getCaseId();

        GameController.reset();
        controller = GameController.getInstance();
        controller.startGame();
        String id2 = controller.getCaseId();

        assertEquals("Game_001", id1);
        assertEquals("Game_001", id2);
    }

    @Test
    void testSetQuestionsAndGetQuestions(){
        QuestionList questions = new QuestionList();
        HashMap<String,String> options = new HashMap<>();
        options.put("A","a");
        Question q = new Question("Test", 100, "Q", options, "A");
        questions.addQuestion(q);

        controller.setQuestions(questions);
        assertNotNull(controller.getQuestions());
        assertEquals(1, controller.getQuestions().getQuestionArray().size());
    }

    @Test
    void testGetPlayersNull(){
        assertNull(controller.getPlayers());
    }

    @Test
    void testCategoryInitiallyNull(){
        assertNull(controller.getCategory());
    }

    @Test
    void testQuestionInitiallyNull(){
        assertNull(controller.getQuestion());
    }

    @Test
    void testQuestionsInitiallyNull(){
        assertNull(controller.getQuestions());
    }

    @Test
    void testCaseIdBeforeStart(){
        assertNull(controller.getCaseId());
    }

    @Test
    void testSetCategoryNull(){
        controller.setCategory("Test");
        controller.setCategory(null);
        assertNull(controller.getCategory());
    }

    @Test
    void testSetQuestionNull(){
        HashMap<String,String> options = new HashMap<>();
        options.put("A","a");
        Question q = new Question("Test", 100, "Q", options, "A");
        controller.setQuestion(q);
        controller.setQuestion(null);
        assertNull(controller.getQuestion());
    }


    @Test
    void testControllerStateAfterReset(){
        controller.startGame();
        QuestionList questions = new QuestionList();
        controller.setQuestions(questions);
        controller.setCategory("Test");

        GameController.reset();
        controller = GameController.getInstance();

        assertNull(controller.getCaseId());
        assertNull(controller.getCategory());
        assertNull(controller.getQuestions());
    }

    @Test
    void testMultipleQuestionsInController(){
        QuestionList questions = new QuestionList();
        HashMap<String,String> options = new HashMap<>();
        options.put("A","a");
        options.put("B","b");

        questions.addQuestion(new Question("Cat1", 100, "Q1", options, "A"));
        questions.addQuestion(new Question("Cat1", 200, "Q2", options, "B"));
        questions.addQuestion(new Question("Cat2", 300, "Q3", options, "A"));

        controller.setQuestions(questions);
        assertEquals(3, controller.getQuestions().getQuestionArray().size());
    }

    @Test
    void testControllerQuestionCategorySync(){
        HashMap<String,String> options = new HashMap<>();
        options.put("A","a");
        Question q = new Question("Science", 100, "Q", options, "A");

        controller.setCategory("Science");
        controller.setQuestion(q);

        assertEquals(controller.getCategory(), q.getCategory());
    }
}
