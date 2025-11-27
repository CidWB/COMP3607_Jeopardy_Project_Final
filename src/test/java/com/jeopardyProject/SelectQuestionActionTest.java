package com.jeopardyProject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.HashMap;

import com.jeopardyProject.Game.Command.SelectQuestionAction;
import com.jeopardyProject.Game.GameController;
import com.jeopardyProject.Game.Question;

public class SelectQuestionActionTest {
    private GameController controller;
    private SelectQuestionAction action;
    private Question q1;

    @BeforeEach
    void setUp() throws Exception{
        controller = GameController.getInstance();
        action = new SelectQuestionAction();

        HashMap<String,String> options = new HashMap<>();
        options.put("A","a");
        options.put("B","b"); 
        options.put("C","c"); 
        options.put("D","d");
        q1 = new Question("Functions", 100, "Q1", options, "A");       
        controller.setQuestion(q1);
    }

    @Test
    void testSelectQuestionByValue(){
        controller.setQuestion(q1);
        action.execute("100");
        Question selected = controller.getQuestion();
        assertNotNull(selected);
        assertEquals(100, selected.getValue());
    }

    @Test
    void testSelectNullQuestion(){
        action.execute("500");
        assertNull(controller.getQuestion());
    }
}
