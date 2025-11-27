package com.jeopardyProject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.jeopardyProject.Game.State.AnswerQuestionState;
import com.jeopardyProject.Game.State.PlayerState;
import com.jeopardyProject.Game.Command.*;
import com.jeopardyProject.Game.GameController;

public class AnswerQuestionStateTest {
    private PlayerState state;
    private GameController controller;

    @BeforeEach
    void setUp(){
        GameController.reset();
        controller = GameController.getInstance();
        state = new AnswerQuestionState();
    }

    @Test
    void testAnswerQuestionStateBlocksSelectCategoryAction(){
        Action action = new SelectCategoryAction(controller);
        assertFalse(state.isAllowed(action));
    }

    @Test
    void testAnswerQuestionStateBlocksSelectQuestionAction(){
        Action action = new SelectQuestionAction(controller);
        assertFalse(state.isAllowed(action));
    }

    @Test
    void testAnswerQuestionStateAllowsAnswerQuestionAction(){
        Action action = new AnswerQuestionAction(controller);
        assertTrue(state.isAllowed(action));
    }

    @Test
    void testOnlyAllowsCorrectAction(){
        assertFalse(state.isAllowed(new SelectCategoryAction(controller)));
        assertFalse(state.isAllowed(new SelectQuestionAction(controller)));
        assertTrue(state.isAllowed(new AnswerQuestionAction(controller)));
    }

    @Test
    void testConsistentBehaviorMultipleCalls(){
        Action action = new AnswerQuestionAction(controller);
        assertTrue(state.isAllowed(action));
        assertTrue(state.isAllowed(action));
    }
}
