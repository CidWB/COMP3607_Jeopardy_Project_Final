package com.jeopardyProject;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;

import com.jeopardyProject.Game.Question;
import com.jeopardyProject.Game.Logs.GameEventLog;

public class GameEventLogTest {
    
    @Test
    void testConstructor(){
        GameEventLog log = new GameEventLog("Game_001", "Player1", "Test Action");
        assertEquals("Game_001", log.getCaseId());
        assertEquals("Player1", log.getPlayerId());
        assertEquals("Test Action", log.getAction());
        assertNotNull(log.getTimestamp());
        assertTrue(log.getResult());    // true by default
    }

    @Test
    void testSetAndGetCategory(){
        GameEventLog log = new GameEventLog("Game_001", "Player1", "Select Category");
        assertNull(log.getCategory());
        log.setCategory("Arrays");
        assertEquals("Arrays", log.getCategory());
    }

    @Test
    void testSetAndGetValue(){
        GameEventLog log = new GameEventLog("Game_001", "Player1", "Select Question");
        assertEquals(0, log.getValue());
        log.setValue(200);
        assertEquals(200, log.getValue());
    }

    @Test
    void testSetAndGetAnswer(){
        GameEventLog log = new GameEventLog("Game_001", "Player1", "Answer Question");
        assertNull(log.getAnswer());
        log.setAnswer("B");
        assertEquals("B", log.getAnswer());
    }

    @Test
    void testSetAndGetResult(){
        GameEventLog log = new GameEventLog("Game_001", "Player1", "Answer Question");
        assertTrue(log.getResult());
        log.setResult(false);
        assertFalse(log.getResult());
        log.setResult(true);
        assertTrue(log.getResult());
    }

    @Test
    void testSetAndGetNewScore(){
        GameEventLog log = new GameEventLog("Game_001", "Player1", "Answer Question");
        assertEquals(0, log.getNewScore());
        log.setAnswerResultScore("A", true, 500);
        assertEquals(500, log.getNewScore());
    }

    @Test
    void testSetAnswerResultScore(){
        GameEventLog log = new GameEventLog("Game_001", "Player1", "Answer Question");
        log.setAnswerResultScore("C", false, 200);
        assertEquals("C", log.getAnswer());
        assertFalse(log.getResult());
        assertEquals(200, log.getNewScore());
    }

    @Test
    void testMethodChaining(){
        GameEventLog log = new GameEventLog("Game_001", "Player1", "Answer Question");
        
        GameEventLog result = log.setCategory("Variables")
                                  .setValue(300)
                                  .setAnswer("A")
                                  .setResult(true)
                                  .setAnswerResultScore("A", true, 600);
        
        assertSame(log, result);
        assertEquals("Variables", log.getCategory());
        assertEquals(300, log.getValue());
        assertEquals("A", log.getAnswer());
        assertTrue(log.getResult());
        assertEquals(600, log.getNewScore());
    }

    @Test
    void testSetTimestamp(){
        GameEventLog log = new GameEventLog("Game_001", "Player1", "Test");
        String timestamp = log.getTimestamp();
        assertNotNull(timestamp);
        try{
            Thread.sleep(2000);
            log.setTimestamp();
            String newTimestamp = log.getTimestamp();
            assertFalse(timestamp.equalsIgnoreCase(newTimestamp));
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    @Test
    void testSetQuestion(){
        GameEventLog log = new GameEventLog("Game_001", "Player1", "Answer Question");
        assertNull(log.getQuestion());
        HashMap<String, String> options = new HashMap<>();
        options.put("A", "Option A");
        options.put("B", "Option B");
        Question question = new Question("Arrays", 200, "What is the index of the first element in a C++ array?", options, "A");
        log.setQuestion(question);
        assertEquals(question, log.getQuestion());
    }

    @Test
    void testGettersWithNullOrZeroValues(){
        GameEventLog log = new GameEventLog("Game_001", "Player1", "Action");
        assertNull(log.getCategory());
        assertEquals(0, log.getValue());
        assertNull(log.getAnswer());
        assertNull(log.getQuestion());
        assertEquals(0, log.getNewScore());
    }

    @Test
    void testGetAction(){
        GameEventLog log = new GameEventLog("Game_001", "Player1", "Test Action");
        assertEquals("Test Action", log.getAction());
    }

    @Test
    void testTimestampFormat(){
        GameEventLog log = new GameEventLog("Game_001", "Player1", "Test");
        String timestamp = log.getTimestamp();    
        assertNotNull(timestamp);
        assertTrue(timestamp.matches("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}"),
                   "Timestamp should be in ISO_LOCAL_DATE_TIME format");
    }
}
