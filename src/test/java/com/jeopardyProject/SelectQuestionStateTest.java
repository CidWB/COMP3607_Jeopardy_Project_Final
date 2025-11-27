package com.jeopardyProject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.jeopardyProject.Game.State.SelectQuestionState;
import com.jeopardyProject.Game.State.PlayerState;
import com.jeopardyProject.Game.Command.*;
import com.jeopardyProject.Game.GameController;

public class SelectQuestionStateTest {
    private PlayerState state;
    private GameController controller;

    @BeforeEach
    void setUp(){
        GameController.reset();
        controller = GameController.getInstance();
        state = new SelectQuestionState();
    }

    @Test
    void testSelectQuestionStateBlocksSelectCategoryAction(){
        Action action = new SelectCategoryAction(controller);
        assertFalse(state.isAllowed(action));
    }

    @Test
    void testSelectQuestionStateAllowsSelectQuestionAction(){
        Action action = new SelectQuestionAction(controller);
        assertTrue(state.isAllowed(action));
    }

    @Test
    void testSelectQuestionStateBlocksAnswerQuestionAction(){
        Action action = new AnswerQuestionAction(controller);
        assertFalse(state.isAllowed(action));
    }

    @Test
    void testOnlyAllowsCorrectAction(){
        assertFalse(state.isAllowed(new SelectCategoryAction(controller)));
        assertTrue(state.isAllowed(new SelectQuestionAction(controller)));
        assertFalse(state.isAllowed(new AnswerQuestionAction(controller)));
    }

    @Test
    void testConsistentBehaviorMultipleCalls(){
        Action action = new SelectQuestionAction(controller);
        assertTrue(state.isAllowed(action));
        assertTrue(state.isAllowed(action));
    }
}
