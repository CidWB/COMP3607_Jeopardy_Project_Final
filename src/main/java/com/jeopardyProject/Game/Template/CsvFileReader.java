package com.jeopardyProject.Game.Template;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;

import com.jeopardyProject.Game.Question;
import com.jeopardyProject.Game.QuestionList;
import com.opencsv.*;

/**
 * CSV file parser implementing the Template Method pattern.
 * <p>
 * Parses question files in CSV (Comma-Separated Values) format using the OpenCSV library.
 * Expected CSV format:
 * <pre>
 * Category, Value, Question, OptionA, OptionB, OptionC, OptionD, CorrectAnswer
 * Variables,100,"What is a variable?","A memory location","A function","A class","A loop","A"
 * </pre>
 * <p>
 * Error Handling:
 * <ul>
 *   <li>Skips rows with insufficient columns</li>
 *   <li>Logs number format errors for invalid value fields</li>
 *   <li>Continues parsing remaining rows after errors</li>
 * </ul>
 *
 * @see FileReaderTemplate
 * @author COMP3607 Jeopardy Project Team
 * @version 1.0
 */
public class CsvFileReader extends FileReaderTemplate{
    /** Required number of columns in each CSV row. */
    private static final int REQUIRED_CSV_COLUMNS = 8;

    /**
     * Constructs a CsvFileReader for the specified file.
     *
     * @param filepath absolute path to the CSV file
     */
    public CsvFileReader(String filepath){
        super(filepath);
    }

    /**
     * Parses questions from CSV format.
     * <p>
     * Skips the header row and parses each subsequent row as a question.
     * Invalid rows are logged and skipped, allowing parsing to continue.
     * </p>
     *
     * @return QuestionList containing all successfully parsed questions
     * @throws FileNotFoundException if the CSV file cannot be read
     */
    @Override
    protected QuestionList parseQuestions() throws FileNotFoundException {
        QuestionList questions = new QuestionList();

        try(CSVReader csvReader = new CSVReader(new FileReader(this.filepath))){
            csvReader.readNext();

            String[] nextRecord;
            while ((nextRecord = csvReader.readNext()) != null){
                if(nextRecord.length < REQUIRED_CSV_COLUMNS){
                    System.err.println("Skipping invalid CSV row with insufficient columns");
                    continue;
                }
                try{
                    questions.addQuestion(parseQuestion(nextRecord));
                } catch (NumberFormatException e){
                    System.err.println("Invalid number format in CSV: " + e.getMessage());
                }
            }
        } catch (FileNotFoundException e){
            throw e;
        } catch (Exception e){
            System.err.println("Error reading CSV file: " + e.getMessage());
        }
        return questions;
    }

    /**
     * Parses a single CSV row into a Question object.
     * <p>
     * Expected row format: [Category, Value, Question, OptionA, OptionB, OptionC, OptionD, CorrectAnswer]
     * </p>
     *
     * @param record string array containing CSV row values
     * @return Question object parsed from the row
     * @throws NumberFormatException if the value field cannot be parsed as an integer
     */
    private Question parseQuestion(String[] record) throws NumberFormatException {
        String category = record[0];
        int value = Integer.parseInt(record[1]);
        String content = record[2];

        HashMap<String, String> options = new HashMap<>();
        options.put("A", record[3]);
        options.put("B", record[4]);
        options.put("C", record[5]);
        options.put("D", record[6]);

        String answer = record[7];
        return new Question(category, value, content, options, answer);
    }
}
