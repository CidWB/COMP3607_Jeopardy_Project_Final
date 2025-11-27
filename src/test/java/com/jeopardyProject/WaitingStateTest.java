package com.jeopardyProject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.jeopardyProject.Game.State.WaitingState;
import com.jeopardyProject.Game.State.PlayerState;
import com.jeopardyProject.Game.Command.*;
import com.jeopardyProject.Game.GameController;

public class WaitingStateTest {
    private PlayerState state;
    private GameController controller;

    @BeforeEach
    void setUp(){
        GameController.reset();
        controller = GameController.getInstance();
        state = new WaitingState();
    }

    @Test
    void testWaitingStateBlocksSelectCategoryAction(){
        Action action = new SelectCategoryAction(controller);
        assertFalse(state.isAllowed(action));
    }

    @Test
    void testWaitingStateBlocksSelectQuestionAction(){
        Action action = new SelectQuestionAction(controller);
        assertFalse(state.isAllowed(action));
    }

    @Test
    void testWaitingStateBlocksAnswerQuestionAction(){
        Action action = new AnswerQuestionAction(controller);
        assertFalse(state.isAllowed(action));
    }

    @Test
    void testWaitingStateBlocksAllActions(){
        assertFalse(state.isAllowed(new SelectCategoryAction(controller)));
        assertFalse(state.isAllowed(new SelectQuestionAction(controller)));
        assertFalse(state.isAllowed(new AnswerQuestionAction(controller)));
    }

    @Test
    void testWaitingStateConsistentBehavior(){
        Action action = new SelectCategoryAction(controller);
        assertFalse(state.isAllowed(action));
        assertFalse(state.isAllowed(action));
    }
}
