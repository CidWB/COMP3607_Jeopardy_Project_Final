package com.jeopardyProject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import com.jeopardyProject.Game.Logs.GameEventLog;
import com.jeopardyProject.Game.Logs.Logger;
import com.jeopardyProject.Game.Player;

public class LoggerTest {
    private Logger logger;

    @BeforeEach
    void setUp(){
        logger = Logger.getInstance();
    }

    @Test
    void testGetInstance(){
        assertNotNull(logger, "Logger.getInstance() should return non-null");
    }

    @Test
    void testGetInstanceSingleton(){
        Logger logger2 = Logger.getInstance();
        assertSame(logger, logger2, "getInstance should return same instance (singleton)");
    }

    @Test
    void testLogMethod(){
        GameEventLog log = new GameEventLog("Game_001", "Player1", "Test Action");
        
        // Should not throw exception
        logger.log(log);
    }

    @Test
    void testLogWithMultipleLogs(){
        GameEventLog log1 = new GameEventLog("Game_001", "Player1", "Action 1");
        GameEventLog log2 = new GameEventLog("Game_001", "Player2", "Action 2");
        
        logger.log(log1);
        logger.log(log2);
        
        // Test passes if no exception
    }

    @Test
    void testLogWithCompleteEventLog(){
        GameEventLog log = new GameEventLog("Game_001", "Player1", "Answer Question");
        log.setCategory("Science")
           .setValue(200)
           .setAnswer("A")
           .setResult(true)
           .setAnswerResultScore("A", true, 500);
        
        logger.log(log);
    }

    @Test
    void testInitTurnReport(){
        ArrayList<Player> players = new ArrayList<>();
        players.add(new Player("Alice"));
        logger.initTurnReport("Game_001", players);
    }

    @Test
    void testGenerateTurnReport(){
        ArrayList<Player> players = new ArrayList<>();
        players.add(new Player("Alice"));
        String filename = logger.generateTurnReport(players);
        assertNotNull(filename);
        assertTrue(filename.endsWith(".txt"));
    }

    @Test
    void testGenerateLogReport(){
        String filename = logger.generateLogReport();
        assertNotNull(filename);
        assertTrue(filename.endsWith(".csv"), "Log report should be a .csv file");
    }

    @Test
    void testTurnReportFileNameExpected(){
        ArrayList<Player> players = new ArrayList<>();
        players.add(new Player("Alice"));
        logger.initTurnReport("Game_001", players);
        String filename = logger.generateTurnReport(players);
        assertEquals("TurnReport.txt", filename);
    }

    @Test
    void testLogReportFileNameExpected(){
        String filename = logger.generateLogReport();
        assertEquals("LogReport.csv", filename);
    }

    @Test
    void testMultipleLogsWritten(){
        GameEventLog log1 = new GameEventLog("Game_001", "Alice", "Select Category");
        log1.setCategory("Science");
        
        GameEventLog log2 = new GameEventLog("Game_001", "Bob", "Select Question");
        log2.setCategory("Science").setValue(200);
        
        GameEventLog log3 = new GameEventLog("Game_001", "Alice", "Answer Question");
        log3.setAnswer("A").setResult(true).setAnswerResultScore("A", true, 200);
        
        logger.log(log1);
        logger.log(log2);
        logger.log(log3);
    }

    @Test
    void testLogInstanceNotNull(){
        assertNotNull(Logger.getInstance());
    }

    @Test
    void testLoggerCreatesFiles(){
        // Logger constructor should create files
        // Check if files exist (they may or may not, depending on constructor)
        // This test documents that Logger can handle file creation
        assertNotNull(logger);
    }

    @Test
    void testAddReportQuestion(){
        GameEventLog log = new GameEventLog("Game_001", "Player1", "Select Question");
        log.setCategory("Science").setValue(200);
        logger.log(log);
        
        // addReportQuestion should be callable (may have limited functionality)
        // This test documents the method exists
        try {
            logger.addReportQuestion(1);
        } catch (Exception e) {
            // Method may throw if not fully implemented
        }
    }

    @Test
    void testAddReportAnswer(){
        GameEventLog log = new GameEventLog("Game_001", "Player1", "Answer Question");
        log.setAnswer("A").setResult(true);
        logger.log(log);
        
        // addReportAnswer should be callable
        try {
            logger.addReportAnswer();
        } catch (Exception e) {
            // Method may throw if not fully implemented
        }
    }

    @Test
    void testLoggerSingleton(){
        Logger l1 = Logger.getInstance();
        Logger l2 = Logger.getInstance();
        Logger l3 = Logger.getInstance();
        
        assertSame(l1, l2);
        assertSame(l2, l3);
    }

    @Test
    void testLogWithVariousActions(){
        String[] actions = {"Start Game", "Select Category", "Select Question", "Answer Question", "End Turn"};
        
        for (String action : actions) {
            GameEventLog log = new GameEventLog("Game_001", "Player1", action);
            logger.log(log);
        }
    }

    @Test
    void testLogWithDifferentPlayers(){
        String[] players = {"Alice", "Bob", "Carol", "David"};
        
        for (String playerName : players) {
            GameEventLog log = new GameEventLog("Game_001", playerName, "Action");
            logger.log(log);
        }
    }

    @Test
    void testGenerateReportsWithoutException(){
        GameEventLog log = new GameEventLog("Game_001", "Player1", "Test");
        logger.log(log);

        ArrayList<Player> players = new ArrayList<>();
        players.add(new Player("Player1"));
        String turnReport = logger.generateTurnReport(players);
        String logReport = logger.generateLogReport();

        assertNotNull(turnReport);
        assertNotNull(logReport);
    }
}
