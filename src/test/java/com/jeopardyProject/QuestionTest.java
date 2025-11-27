package com.jeopardyProject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;

import com.jeopardyProject.Game.Question;

public class QuestionTest {
    private HashMap<String, String> options;
    private Question question;

    @BeforeEach
    void setUp(){
        options = new HashMap<>();
        options.put("A", "int num;");
        options.put("B", "float num;");
        options.put("C", "num int;");
        options.put("D", "integer num;");
        
        question = new Question("Variables", 200, "Which of the following declares an integer variable in C++?", options, "A");
    }

    @Test
    void testConstructor(){
        assertEquals("Variables", question.getCategory());
        assertEquals(200, question.getValue());
        assertEquals("Which of the following declares an integer variable in C++?", question.getContent());
        assertEquals("A", question.getRightAnswer());
        assertFalse(question.getIsAnswered());
    }

    @Test
    void testGetCategory(){
        assertEquals("Variables", question.getCategory());
    }

    @Test
    void testGetValue(){
        assertEquals(200, question.getValue());
    }

    @Test
    void testGetContent(){
        assertEquals("Which of the following declares an integer variable in C++?", question.getContent());
    }

    @Test
    void testGetOptions(){
        HashMap<String, String> retrievedOptions = question.getOptions();
        assertNotNull(retrievedOptions);
        assertEquals(4, retrievedOptions.size());
        assertEquals("int num;", retrievedOptions.get("A"));
    }

    @Test
    void testGetValueGivenKey(){
        assertEquals("int num;", question.getValueGivenKey("A"));
        assertEquals("float num;", question.getValueGivenKey("B"));
        assertEquals("num int;", question.getValueGivenKey("C"));
        assertEquals("integer num;", question.getValueGivenKey("D"));
    }

    @Test
    void testGetValueGivenKeyInvalid(){
        assertNull(question.getValueGivenKey("Z"));
    }

    @Test
    void testGetRightAnswer(){
        assertEquals("A", question.getRightAnswer());
    }

    @Test
    void testSetAndGetIsAnswered(){
        question.setIsAnswered(true);
        assertTrue(question.getIsAnswered()); 
        question.setIsAnswered(false);
        assertFalse(question.getIsAnswered());
    }

    @Test
    void testMultipleQuestions(){
        HashMap<String, String> options2 = new HashMap<>();
        options2.put("A", "return");
        options2.put("B", "void");
        options2.put("C", "int");
        options2.put("C", "empty");
        
        Question question2 = new Question("Functions", 300, "Which keyword is used to define a function returning no value?", options2, "B");
        
        assertNotEquals(question.getCategory(), question2.getCategory());
        assertNotEquals(question.getValue(), question2.getValue());
        assertEquals("Variables", question.getCategory());
        assertEquals("Functions", question2.getCategory());
    }

    @Test
    void testOptionsContainsAllKeys(){
        assertTrue(options.containsKey("A"));
        assertTrue(options.containsKey("B"));
        assertTrue(options.containsKey("C"));
        assertTrue(options.containsKey("D"));
    }

    @Test
    void testQuestionCorrectAnswerKey(){
        assertEquals("A", question.getRightAnswer());
        assertEquals("int num;", question.getValueGivenKey(question.getRightAnswer()));
    }
}
