package com.jeopardyProject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.HashMap;

import com.jeopardyProject.Game.Command.SelectCategoryAction;
import com.jeopardyProject.Game.GameController;
import com.jeopardyProject.Game.Question;
import com.jeopardyProject.Game.QuestionList;

public class SelectCategoryActionTest {
    private GameController controller;
    private SelectCategoryAction action;
    private QuestionList questions;

    @BeforeEach
    void setUp() throws Exception{
        controller = GameController.getInstance();
        action = new SelectCategoryAction();

        questions = new QuestionList();
        HashMap<String,String> options = new HashMap<>();
        options.put("A","a");
        options.put("B","b"); 
        options.put("C","c"); 
        options.put("D","d");
        Question q1 = new Question("Variables & Data Types", 100, "Q1", options, "A");
        Question q2 = new Question("Control Structures", 200, "Q2", options, "B");
        questions.addQuestion(q1);
        questions.addQuestion(q2);
    }

    @Test
    void testSelectCategoryMatchesStartOfWord(){
        controller.setCategory("Variables & Data Types");
        action.execute("Var");
        assertEquals("Variables & Data Types", controller.getCategory());
    }

    @Test
    void testSelectCategoryNoMatch(){
        controller.setCategory("Variables & Data Types");
        action.execute("NonExisting");
        assertNull(controller.getCategory());
    }
}
