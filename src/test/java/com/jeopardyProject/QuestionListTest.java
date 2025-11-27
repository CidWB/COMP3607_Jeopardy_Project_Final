package com.jeopardyProject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;

import com.jeopardyProject.Game.Question;
import com.jeopardyProject.Game.QuestionList;

public class QuestionListTest {
    private QuestionList questionList;
    private Question q1, q2, q3;

    @BeforeEach
    void setUp(){
        questionList = new QuestionList();
        
        HashMap<String, String> options1 = new HashMap<>();
        options1.put("A", "Answer A");
        q1 = new Question("Functions", 100, "Question 1", options1, "A");
        
        HashMap<String, String> options2 = new HashMap<>();
        options2.put("B", "Answer B");
        q2 = new Question("Functions", 200, "Question 2", options2, "B");
        
        HashMap<String, String> options3 = new HashMap<>();
        options3.put("C", "Answer C");
        q3 = new Question("Arrays", 300, "Question 3", options3, "C");
    }

    @Test
    void testAddQuestion(){
        questionList.addQuestion(q1);
        ArrayList<Question> questions = questionList.getQuestionArray();
        assertEquals(1, questions.size());
        assertTrue(questions.contains(q1));
    }

    @Test
    void testAddMultipleQuestions(){
        questionList.addQuestion(q1);
        questionList.addQuestion(q2);
        questionList.addQuestion(q3);
        ArrayList<Question> questions = questionList.getQuestionArray();
        assertEquals(3, questions.size());
    }

    @Test
    void testGetQuestionArray(){
        questionList.addQuestion(q1);
        questionList.addQuestion(q2);
        ArrayList<Question> questions = questionList.getQuestionArray();
        assertNotNull(questions);
        assertEquals(2, questions.size());
    }

    @Test
    void testGetQuestionsByCategory(){
        questionList.addQuestion(q1);
        questionList.addQuestion(q2);
        questionList.addQuestion(q3);
        
        ArrayList<Question> scienceQuestions = questionList.getQuestionsByCategory("Functions");
        assertEquals(2, scienceQuestions.size());
        assertTrue(scienceQuestions.contains(q1));
        assertTrue(scienceQuestions.contains(q2));
        assertFalse(scienceQuestions.contains(q3));
    }

    @Test
    void testGetQuestionsByCategoryEmpty(){
        questionList.addQuestion(q1);
        questionList.addQuestion(q2);
        ArrayList<Question> mathQuestions = questionList.getQuestionsByCategory("File Handling");
        assertEquals(0, mathQuestions.size());
    }

    @Test
    void testHasQuestionsEmpty(){
        assertFalse(questionList.hasQuestions());
    }

    @Test
    void testHasQuestionsWithUnansweredQuestions(){
        questionList.addQuestion(q1);
        questionList.addQuestion(q2);
        assertTrue(questionList.hasQuestions());
    }

    @Test
    void testHasQuestionsAllAnswered(){
        questionList.addQuestion(q1);
        questionList.addQuestion(q2);
        q1.setIsAnswered(true);
        q2.setIsAnswered(true);
        assertFalse(questionList.hasQuestions());
    }

    @Test
    void testCreateQuestionGrid(){      // no execption thrown
        questionList.addQuestion(q1);
        questionList.addQuestion(q2);
        questionList.addQuestion(q3);
        questionList.createQuestionGrid();
    }

    @Test
    void testGetQuestionArrayNotNull(){
        questionList.addQuestion(q1);
        ArrayList<Question> questions = questionList.getQuestionArray();
        assertNotNull(questions);
    }
}
