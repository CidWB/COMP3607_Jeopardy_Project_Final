package com.jeopardyProject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.jeopardyProject.Game.Player;
import com.jeopardyProject.Game.State.*;
import com.jeopardyProject.Game.Command.*;
import com.jeopardyProject.Game.GameController;

public class PlayerStateIntegrationTest {
    private Player player;
    private GameController controller;

    @BeforeEach
    void setUp(){
        GameController.reset();
        controller = GameController.getInstance();
        player = new Player("TestPlayer");
    }

    @Test
    void testPlayerInitiallyInWaitingState(){
        player.setState(new WaitingState());
        assertFalse(player.getState().isAllowed(new SelectCategoryAction(controller)));
    }

    @Test
    void testPlayerTransitionToSelectCategory(){
        player.setState(new SelectCategoryState());
        assertTrue(player.getState().isAllowed(new SelectCategoryAction(controller)));
        assertFalse(player.getState().isAllowed(new SelectQuestionAction(controller)));
    }

    @Test
    void testPlayerTransitionToSelectQuestion(){
        player.setState(new SelectQuestionState());
        assertFalse(player.getState().isAllowed(new SelectCategoryAction(controller)));
        assertTrue(player.getState().isAllowed(new SelectQuestionAction(controller)));
        assertFalse(player.getState().isAllowed(new AnswerQuestionAction(controller)));
    }

    @Test
    void testPlayerTransitionToAnswerQuestion(){
        player.setState(new AnswerQuestionState());
        assertFalse(player.getState().isAllowed(new SelectCategoryAction(controller)));
        assertFalse(player.getState().isAllowed(new SelectQuestionAction(controller)));
        assertTrue(player.getState().isAllowed(new AnswerQuestionAction(controller)));
    }

    @Test
    void testCompleteStateCycle(){
        player.setState(new WaitingState());
        assertFalse(player.getState().isAllowed(new SelectCategoryAction(controller)));

        player.setState(new SelectCategoryState());
        assertTrue(player.getState().isAllowed(new SelectCategoryAction(controller)));

        player.setState(new SelectQuestionState());
        assertTrue(player.getState().isAllowed(new SelectQuestionAction(controller)));

        player.setState(new AnswerQuestionState());
        assertTrue(player.getState().isAllowed(new AnswerQuestionAction(controller)));

        player.setState(new WaitingState());
        assertFalse(player.getState().isAllowed(new AnswerQuestionAction(controller)));
    }

    @Test
    void testMultiplePlayersIndependentStates(){
        Player player1 = new Player("Player1");
        Player player2 = new Player("Player2");

        player1.setState(new SelectCategoryState());
        player2.setState(new WaitingState());

        assertTrue(player1.getState().isAllowed(new SelectCategoryAction(controller)));
        assertFalse(player2.getState().isAllowed(new SelectCategoryAction(controller)));
    }

    @Test
    void testStateChangeDoesNotAffectScore(){
        player.setScore(100);
        player.setState(new SelectCategoryState());
        assertEquals(100, player.getScore());

        player.setState(new AnswerQuestionState());
        assertEquals(100, player.getScore());
    }

    @Test
    void testStateTransitionSequence(){
        player.setState(new SelectCategoryState());
        Action selectCategory = new SelectCategoryAction(controller);
        assertTrue(player.getState().isAllowed(selectCategory));

        player.setState(new SelectQuestionState());
        Action selectQuestion = new SelectQuestionAction(controller);
        assertTrue(player.getState().isAllowed(selectQuestion));
        assertFalse(player.getState().isAllowed(selectCategory));
    }

    @Test
    void testBackToWaitingStateBlocksAll(){
        player.setState(new AnswerQuestionState());
        assertTrue(player.getState().isAllowed(new AnswerQuestionAction(controller)));

        player.setState(new WaitingState());
        assertFalse(player.getState().isAllowed(new SelectCategoryAction(controller)));
        assertFalse(player.getState().isAllowed(new SelectQuestionAction(controller)));
        assertFalse(player.getState().isAllowed(new AnswerQuestionAction(controller)));
    }
}
