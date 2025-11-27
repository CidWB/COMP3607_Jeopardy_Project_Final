package com.jeopardyProject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.jeopardyProject.Game.Player;
import com.jeopardyProject.Game.State.WaitingState;

public class PlayerTest {
    private Player player;

    @BeforeEach
    void setUp(){
        player = new Player("player1");
    }

    @Test
    void testConstructor(){
        assertEquals("player1", player.getPlayerId());
        assertEquals(0, player.getScore());
        assertNotNull(player.getPlayerId());
    }

    @Test
    void testGetPlayerId(){
        assertEquals("player1", player.getPlayerId());
    }

    @Test
    void testSetScore(){
        player.setScore(100);
        assertEquals(100, player.getScore());
        player.setScore(-200);
        assertEquals(-200, player.getScore());
    }

    @Test
    void testSetState(){
        player.setState(new WaitingState());
        assertNotNull(player);
    }

    @Test
    void testPlayerWithEmptyName(){
        Player p = new Player("");
        assertEquals("", p.getPlayerId());
    }

    @Test
    void testScoreUpdate(){
        player.setScore(0);
        player.setScore(player.getScore() + 200);
        assertEquals(200, player.getScore());
        
        player.setScore(player.getScore() + 300);
        assertEquals(500, player.getScore());
        
        player.setScore(player.getScore() - 100);
        assertEquals(400, player.getScore());
    }
}
