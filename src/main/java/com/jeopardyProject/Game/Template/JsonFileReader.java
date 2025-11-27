package com.jeopardyProject.Game.Template;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import com.jeopardyProject.Game.Question;
import com.jeopardyProject.Game.QuestionList;

/**
 * JSON file parser implementing the Template Method pattern.
 * <p>
 * Parses question files in JSON format using the Gson library.
 * Expected JSON structure:
 * <pre>
 * [
 *   {
 *     "Category": "Variables",
 *     "Value": 100,
 *     "Question": "What is a variable?",
 *     "Options": {
 *       "A": "A memory location",
 *       "B": "A function",
 *       "C": "A class",
 *       "D": "A loop"
 *     },
 *     "CorrectAnswer": "A"
 *   }
 * ]
 * </pre>
 * <p>
 * Error Handling:
 * <ul>
 *   <li>Logs parsing errors for individual questions</li>
 *   <li>Continues parsing remaining questions after errors</li>
 * </ul>
 *
 * @see FileReaderTemplate
 * @author COMP3607 Jeopardy Project Team
 * @version 1.0
 */
public class JsonFileReader extends FileReaderTemplate{

    /**
     * Constructs a JsonFileReader for the specified file.
     *
     * @param filepath absolute path to the JSON file
     */
    public JsonFileReader(String filepath){
        super(filepath);
    }

    /**
     * Parses questions from JSON format.
     * <p>
     * Parses the JSON array and converts each element to a Question object.
     * Invalid questions are logged and skipped, allowing parsing to continue.
     * </p>
     *
     * @return QuestionList containing all successfully parsed questions
     * @throws FileNotFoundException if the JSON file cannot be read
     */
    @Override
    protected QuestionList parseQuestions() throws FileNotFoundException {
        QuestionList questions = new QuestionList();

        try(FileReader reader = new FileReader(this.filepath)){
            JsonElement jsonElement = JsonParser.parseReader(reader);
            JsonArray jsonArray = jsonElement.getAsJsonArray();

            for (JsonElement item : jsonArray){
                try{
                    JsonObject jsonObject = item.getAsJsonObject();
                    questions.addQuestion(parseQuestion(jsonObject));
                } catch (Exception e){
                    System.err.println("Error parsing JSON question: " + e.getMessage());
                }
            }
        } catch (FileNotFoundException e){
            throw e;
        } catch (Exception e){
            System.err.println("Error reading JSON file: " + e.getMessage());
        }
        return questions;
    }

    /**
     * Parses a single JSON object into a Question.
     * <p>
     * Extracts fields: Category, Value, Question, Options{A,B,C,D}, CorrectAnswer
     * </p>
     *
     * @param jsonObject JSON object representing a single question
     * @return Question object parsed from JSON
     * @throws com.google.gson.JsonSyntaxException if required fields are missing
     */
    private Question parseQuestion(JsonObject jsonObject){
        String category = jsonObject.get("Category").getAsString();
        int value = jsonObject.get("Value").getAsInt();
        String content = jsonObject.get("Question").getAsString();

        JsonObject optionsObj = jsonObject.get("Options").getAsJsonObject();
        HashMap<String, String> options = new HashMap<>();
        options.put("A", optionsObj.get("A").getAsString());
        options.put("B", optionsObj.get("B").getAsString());
        options.put("C", optionsObj.get("C").getAsString());
        options.put("D", optionsObj.get("D").getAsString());

        String answer = jsonObject.get("CorrectAnswer").getAsString();
        return new Question(category, value, content, options, answer);
    }
}

