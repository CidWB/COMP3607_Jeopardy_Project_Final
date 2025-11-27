package com.jeopardyProject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import com.jeopardyProject.Game.Question;
import com.jeopardyProject.Game.QuestionList;
import com.jeopardyProject.Game.Template.XmlFileReader;

public class XmlFileReaderTest {
    private XmlFileReader reader;
    private QuestionList questions;
    private String filepath = "src/resources/sample.xml";

    @BeforeEach
    void setUp() throws Exception {
        this.reader = new XmlFileReader(this.filepath);
    }

    @Test
    void testXmlFileReaderConstructor(){
        assertNotNull(reader);
    }

    @Test
    void testReadFile() throws FileNotFoundException{
        questions = reader.readFile();
        assertNotNull(questions);
    }

    @Test
    void testReadFileReturnsQuestions() throws FileNotFoundException{
        questions = reader.readFile();
        ArrayList<Question> allQuestions = questions.getQuestionArray();
        assertNotNull(allQuestions);
        assertTrue(allQuestions.size() > 0);
    }

    @Test
    void testReadFileCorrectNumberOfQuestions() throws FileNotFoundException{
        questions = reader.readFile();
        ArrayList<Question> allQuestions = questions.getQuestionArray();
        assertEquals(25, allQuestions.size());
    }

    @Test
    void testFirstQuestionParsedCorrectly() throws FileNotFoundException{
        questions = reader.readFile();
        ArrayList<Question> allQuestions = questions.getQuestionArray();
        Question first = allQuestions.get(0);
        assertEquals("Variables & Data Types", first.getCategory());
        assertEquals(100, first.getValue());
        assertEquals("Which of the following declares an integer variable in C++?", first.getContent());
        assertEquals("A", first.getRightAnswer());
        assertFalse(first.getIsAnswered());
    }

    @Test
    void testOptionsParsedCorrectly() throws FileNotFoundException{
        questions = reader.readFile();
        ArrayList<Question> allQuestions = questions.getQuestionArray();
        Question first = allQuestions.get(0);
        assertEquals("int num;", first.getValueGivenKey("A"));
        assertEquals("float num;", first.getValueGivenKey("B"));
        assertEquals("num int;", first.getValueGivenKey("C"));
        assertEquals("integer num;", first.getValueGivenKey("D"));
    }

    @Test
    void testCategoriesVaried() throws FileNotFoundException{
        questions = reader.readFile();
        ArrayList<Question> varDataTypes = questions.getQuestionsByCategory("Variables & Data Types");
        ArrayList<Question> controlStructures = questions.getQuestionsByCategory("Control Structures");
        ArrayList<Question> functions = questions.getQuestionsByCategory("Functions");
        ArrayList<Question> arrays = questions.getQuestionsByCategory("Arrays");
        ArrayList<Question> fileHandling = questions.getQuestionsByCategory("File Handling");
        
        assertTrue(varDataTypes.size() > 0);
        assertTrue(controlStructures.size() > 0);
        assertTrue(functions.size() > 0);
        assertTrue(arrays.size() > 0);
        assertTrue(fileHandling.size() > 0);
    }

    @Test
    void testValuesAreCorrect() throws FileNotFoundException{
        questions = reader.readFile();
        ArrayList<Question> allQuestions = questions.getQuestionArray();
        for (Question q : allQuestions) {
            assertTrue(q.getValue() >= 100 && q.getValue() <= 500);
        }
    }

    @Test
    void testCorrectAnswersPresent() throws FileNotFoundException{
        questions = reader.readFile();
        ArrayList<Question> allQuestions = questions.getQuestionArray();
        for (Question q : allQuestions) {
            String correctAnswer = q.getRightAnswer();
            assertNotNull(correctAnswer, "Correct answer should not be null");
            assertTrue(correctAnswer.matches("A") || correctAnswer.matches("B") || correctAnswer.matches("C") || correctAnswer.matches("D"));
        }
    }

    @Test
    void testSecondQuestionData() throws FileNotFoundException{
        questions = reader.readFile();
        ArrayList<Question> allQuestions = questions.getQuestionArray();
        Question second = allQuestions.get(1);
        assertEquals("Variables & Data Types", second.getCategory());
        assertEquals(200, second.getValue());
        assertEquals("Which data type is used to store a single character?", second.getContent());
        assertEquals("B", second.getRightAnswer());
    }

    @Test
    void testCreateQuestionGridWorks() throws FileNotFoundException{
        questions = reader.readFile();
        questions.createQuestionGrid();
    }

    @Test
    void testHasQuestionsReturnsTrue() throws FileNotFoundException{
        questions = reader.readFile();
        assertTrue(questions.hasQuestions());
    }

    @Test
    void testCategoryCount() throws FileNotFoundException{
        questions = reader.readFile();
        ArrayList<Question> vars = questions.getQuestionsByCategory("Variables & Data Types");
        ArrayList<Question> control = questions.getQuestionsByCategory("Control Structures");
        ArrayList<Question> funcs = questions.getQuestionsByCategory("Functions");
        ArrayList<Question> arr = questions.getQuestionsByCategory("Arrays");
        ArrayList<Question> files = questions.getQuestionsByCategory("File Handling");
        
        assertEquals(5, vars.size());
        assertEquals(5, control.size());
        assertEquals(5, funcs.size());
        assertEquals(5, arr.size());
        assertEquals(5, files.size());
    }
}
