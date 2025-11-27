package com.jeopardyProject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.jeopardyProject.Game.GameConfig;

public class GameConfigTest {
    private GameConfig config;

    @BeforeEach
    void setUp(){
        config = new GameConfig("Game_001");
    }

    @Test
    void testGameConfigConstructor(){
        assertNotNull(config);
    }

    @Test
    void testGameConfigWithDifferentCaseIds(){
        GameConfig config1 = new GameConfig("Game_001");
        GameConfig config2 = new GameConfig("Game_002");
        GameConfig config3 = new GameConfig("Game_999");

        assertNotNull(config1);
        assertNotNull(config2);
        assertNotNull(config3);
    }

    @Test
    void testGameConfigWithNullCaseId(){
        GameConfig nullConfig = new GameConfig(null);
        assertNotNull(nullConfig);
    }

    @Test
    void testGameConfigWithEmptyCaseId(){
        GameConfig emptyConfig = new GameConfig("");
        assertNotNull(emptyConfig);
    }

    @Test
    void testMultipleGameConfigInstances(){
        GameConfig c1 = new GameConfig("Game_001");
        GameConfig c2 = new GameConfig("Game_002");

        assertNotNull(c1);
        assertNotNull(c2);
        assertNotSame(c1, c2);
    }
}
