package com.jeopardyProject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import com.jeopardyProject.Game.Player;
import com.jeopardyProject.Game.PlayerList;

public class PlayerListTest {
    private PlayerList playerList;

    @BeforeEach
    void setUp(){
        playerList = new PlayerList(3);
    }

    @Test
    void testConstructor(){
        assertNotNull(playerList);
    }

    @Test
    void testAddSinglePlayer(){
        playerList.addPlayer("Player1");
        ArrayList<Player> players = playerList.getAllPlayers();
        
        assertEquals(1, players.size());
        assertTrue("Player1".equalsIgnoreCase(players.remove(0).getPlayerId()));
    }

    @Test
    void testAddMultiplePlayers(){
        playerList.addPlayer("Player1");
        playerList.addPlayer("Player2");
        playerList.addPlayer("Player3");
        
        ArrayList<Player> players = playerList.getAllPlayers();
        assertEquals(3, players.size());
        assertTrue("Player1".equalsIgnoreCase(players.remove(0).getPlayerId()));
        assertTrue("Player2".equalsIgnoreCase(players.remove(1).getPlayerId()));
        assertTrue("Player3".equalsIgnoreCase(players.remove(2).getPlayerId()));
    }

    @Test
    void testGetAllPlayers(){
        playerList.addPlayer("Player1");
        playerList.addPlayer("Player2");
        ArrayList<Player> names = playerList.getAllPlayers();
        assertNotNull(names);
        assertEquals(2, names.size());
    }

    @Test
    void testGetCurrentPlayer(){
        playerList.addPlayer("Player1");
        playerList.addPlayer("Player2");
        
        Player current = playerList.getCurrentPlayer();
        assertNotNull(current);
        assertEquals("Player1", current.getPlayerId());
    }

    @Test
    void testGetPlayersSnapshot(){
        playerList.addPlayer("Player1");
        playerList.addPlayer("Player2");
        playerList.addPlayer("Player3");
        
        ArrayList<Player> players = playerList.getAllPlayers();
        assertNotNull(players);
        assertEquals(3, players.size());
    }

    @Test
    void testGetAllPlayersNotNull(){
        playerList.addPlayer("Alice");
        ArrayList<Player> players = playerList.getAllPlayers();
        assertNotNull(players);
        assertFalse(players.isEmpty());
    }

    @Test
    void testPlayerScoreTracking(){
        playerList.addPlayer("player1");
        playerList.addPlayer("player2");
        
        Player currPlayer = playerList.getCurrentPlayer();
        currPlayer.setScore(100);
        
        Player player = playerList.getAllPlayers().get(0);
        assertEquals(100, player.getScore());
    }

    @Test
    void testEndTurnCorrectAnswer(){
        playerList.addPlayer("player1");
        playerList.addPlayer("player2");
        
        Player currPlayer = playerList.getCurrentPlayer();
        currPlayer.setScore(0);
        playerList.endTurn(200, true);  // simulated correct ans
        
        assertEquals(200, currPlayer.getScore());
        currPlayer = playerList.getCurrentPlayer();
        assertEquals("player2", currPlayer.getPlayerId());
    }

    @Test
    void testEndTurnIncorrectAnswer(){
        playerList.addPlayer("player1");
        playerList.addPlayer("player2");
        
        Player currPlayer = playerList.getCurrentPlayer();
        currPlayer.setScore(300);
        
        playerList.endTurn(200, false);     // simulated wrong ans
        assertEquals(100, currPlayer.getScore());
    }

    @Test
    void testPlayerRotation(){
        playerList.addPlayer("player1");
        playerList.addPlayer("player2");
        playerList.addPlayer("player3");
        
        Player p1 = playerList.getCurrentPlayer();
        assertEquals("player1", p1.getPlayerId());
        
        playerList.endTurn(100, true);
        Player p2 = playerList.getCurrentPlayer();
        assertEquals("player2", p2.getPlayerId());
        
        playerList.endTurn(100, true);
        Player p3 = playerList.getCurrentPlayer();
        assertEquals("player3", p3.getPlayerId());
    }

    @Test
    void testPlayerRotationCycle(){
        playerList.addPlayer("player1");
        playerList.addPlayer("player2");
        
        assertEquals("player1", playerList.getCurrentPlayer().getPlayerId());
        playerList.endTurn(100, true);
        
        assertEquals("player2", playerList.getCurrentPlayer().getPlayerId());
        playerList.endTurn(100, true);
        
        assertEquals("player1", playerList.getCurrentPlayer().getPlayerId()); // should return to player1
    }

    @Test
    void testPlayerListWithMultiplePlayers(){
        PlayerList fourPlayerList = new PlayerList(4);
        fourPlayerList.addPlayer("P1");
        fourPlayerList.addPlayer("P2");
        fourPlayerList.addPlayer("P3");
        fourPlayerList.addPlayer("P4");
        
        ArrayList<Player> names = fourPlayerList.getAllPlayers();
        assertEquals(4, names.size());
    }
}
