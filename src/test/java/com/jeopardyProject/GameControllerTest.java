package com.jeopardyProject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;

import com.jeopardyProject.Game.GameController;
import com.jeopardyProject.Game.Question;

public class GameControllerTest {
    private GameController controller;

    @BeforeEach     // get new instance so that separate tests are true unit tests
    void setUp(){
        controller = null;
        controller = GameController.getInstance();
    }

    @Test 
    void testGetInstance(){
        assertNotNull(controller);
    }

    @Test
    void testGetInstanceSingleton(){
        GameController controller2 = GameController.getInstance();
        assertSame(controller, controller2, "Same singleton instance");
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
        assertNotNull(caseId, "caseId should not be null after startGame");
    }

    @Test
    void testStartGameIncrementsCaseId(){
        controller.startGame();
        String firstId = controller.getCaseId();
        assertEquals("Game_001", firstId);  // this is fine since it would otherwise have started at 000
    }

    // All the lovely tedious getters and setters
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
}
