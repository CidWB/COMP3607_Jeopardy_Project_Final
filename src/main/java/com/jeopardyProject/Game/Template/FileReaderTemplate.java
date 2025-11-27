package com.jeopardyProject.Game.Template;

import java.io.File;
import java.io.FileNotFoundException;

import com.jeopardyProject.Game.QuestionList;

/**
 * Abstract base class implementing the Template Method pattern for question file parsing.
 * <p>
 * This class defines the skeleton algorithm for reading question files:
 * <ol>
 *   <li>Validate file exists</li>
 *   <li>Parse questions using format-specific logic (delegated to subclasses)</li>
 * </ol>
 * Subclasses implement {@link #parseQuestions()} to handle format-specific parsing
 * for CSV, JSON, and XML files.
 * <p>
 * Concrete Implementations:
 * <ul>
 *   <li>{@link CsvFileReader} - Parses CSV files using OpenCSV library</li>
 *   <li>{@link JsonFileReader} - Parses JSON files using Gson library</li>
 *   <li>{@link XmlFileReader} - Parses XML files using Java DOM parser</li>
 * </ul>
 *
 * @see QuestionList
 * @author COMP3607 Jeopardy Project Team
 * @version 1.0
 */
public abstract class FileReaderTemplate {
    /** Absolute path to the question file. */
    protected String filepath;

    /**
     * Constructs a FileReaderTemplate with the specified file path.
     *
     * @param filepath absolute path to the question file
     */
    public FileReaderTemplate(String filepath){
        this.filepath = filepath;
    }

    /**
     * Template method defining the file reading algorithm.
     * <p>
     * This method is final to prevent overriding the algorithm structure.
     * It orchestrates the reading process by:
     * <ol>
     *   <li>Validating file existence</li>
     *   <li>Delegating format-specific parsing to {@link #parseQuestions()}</li>
     * </ol>
     *
     * @return QuestionList containing all parsed questions
     * @throws FileNotFoundException if file does not exist or is not accessible
     */
    public final QuestionList readFile() throws FileNotFoundException {
        validateFileExists();
        return parseQuestions();
    }

    /**
     * Validates that the specified file exists and is a regular file.
     *
     * @throws FileNotFoundException if file does not exist or is a directory
     */
    private void validateFileExists() throws FileNotFoundException {
        File file = new File(filepath);
        if(!file.exists() || !file.isFile()){
            throw new FileNotFoundException("File not found: " + filepath);
        }
    }

    /**
     * Abstract method for format-specific question parsing.
     * <p>
     * Subclasses must implement this method to parse questions from their
     * specific file format (CSV, JSON, or XML) into a unified QuestionList.
     * </p>
     *
     * @return QuestionList containing parsed questions
     * @throws FileNotFoundException if the file cannot be read
     */
    protected abstract QuestionList parseQuestions() throws FileNotFoundException;
}
