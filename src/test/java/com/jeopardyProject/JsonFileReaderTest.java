package com.jeopardyProject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.io.FileNotFoundException;

import com.jeopardyProject.Game.Question;
import com.jeopardyProject.Game.QuestionList;
import com.jeopardyProject.Game.Template.JsonFileReader;

public class JsonFileReaderTest {
    private JsonFileReader reader;
    private QuestionList questions;
    private String filepath = "src/resources/sample.json";

    @BeforeEach
    void setUp() {
        reader = new JsonFileReader();
    }

    @Test
    void testJsonFileReaderConstructor(){
        assertNotNull(reader);
    }

    @Test
    void testReadFileWithValidFilepath() throws FileNotFoundException{
        questions = reader.readFile(this.filepath);
        assertNotNull(questions);
    }

    @Test
    void testReadFileReturnsQuestions() throws FileNotFoundException{
        questions = reader.readFile(this.filepath);
        ArrayList<Question> allQuestions = questions.getQuestionArray();
        assertNotNull(allQuestions);
        assertTrue(allQuestions.size() > 0);
    }

    @Test
    void testReadFileCorrectNumberOfQuestions() throws FileNotFoundException{
        questions = reader.readFile(this.filepath);
        ArrayList<Question> allQuestions = questions.getQuestionArray();
        assertEquals(25, allQuestions.size());
    }

    @Test
    void testQuestionParsedCorrectly() throws FileNotFoundException{
        questions = reader.readFile(this.filepath);
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
        questions = reader.readFile(this.filepath);
        ArrayList<Question> allQuestions = questions.getQuestionArray();
        Question first = allQuestions.get(0);
        assertEquals("int num;", first.getValueGivenKey("A"));
        assertEquals("float num;", first.getValueGivenKey("B"));
        assertEquals("num int;", first.getValueGivenKey("C"));
        assertEquals("integer num;", first.getValueGivenKey("D"));
    }

    @Test
    void testCategoriesVaried() throws FileNotFoundException{
        questions = reader.readFile(this.filepath);
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
    void testValidQuestionValues() throws FileNotFoundException{
        questions = reader.readFile(this.filepath);
        ArrayList<Question> allQuestions = questions.getQuestionArray();
        for (Question q : allQuestions) {
            assertTrue(q.getValue() >= 100 && q.getValue() <= 500);
        }
    }

    @Test
    void testCorrectAnswersPresent() throws FileNotFoundException{
        questions = reader.readFile(this.filepath);
        ArrayList<Question> allQuestions = questions.getQuestionArray();
        for (Question q : allQuestions) {
            String correctAnswer = q.getRightAnswer();
            assertNotNull(correctAnswer);
            assertTrue(correctAnswer.matches("A") || correctAnswer.matches("B") || correctAnswer.matches("C") || correctAnswer.matches("D"));
        }
    }

    @Test
    void testQuestionNotNull() throws FileNotFoundException{
        questions = reader.readFile(this.filepath);
        ArrayList<Question> allQuestions = questions.getQuestionArray();
        for (Question q : allQuestions) {
            assertNotNull(q.getContent());
            assertTrue(q.getContent().length() > 0);
        }
    }

    @Test
    void testSecondQuestionData() throws FileNotFoundException{
        questions = reader.readFile(this.filepath);
        ArrayList<Question> allQuestions = questions.getQuestionArray();
        
        Question second = allQuestions.get(1);
        assertEquals("Variables & Data Types", second.getCategory());
        assertEquals(200, second.getValue());
        assertEquals("Which data type is used to store a single character?", second.getContent());
        assertEquals("B", second.getRightAnswer());
    }

    @Test
    void testCreateQuestionGridWorks() throws FileNotFoundException{
        questions = reader.readFile(this.filepath);
        questions.createQuestionGrid();
    }

    @Test
    void testHasQuestionsReturnsTrue() throws FileNotFoundException{
        questions = reader.readFile(this.filepath);
        assertTrue(questions.hasQuestions());
    }

    @Test
    void testFileNotFoundThrowsException(){
        JsonFileReader badReader = new JsonFileReader();
        assertThrows(FileNotFoundException.class, 
                    () -> badReader.readFile("nonexistent/path/file.json"));
    }

    @Test
    void testReadFileThrowsFileNotFoundForMissingFile(){
        assertThrows(FileNotFoundException.class,
                    () -> reader.readFile("src/resources/nonexistent.json"),
                    "readFile(String) should throw FileNotFoundException for missing file");
    }
}
