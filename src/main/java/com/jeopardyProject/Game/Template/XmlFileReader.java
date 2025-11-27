package com.jeopardyProject.Game.Template;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.jeopardyProject.Game.Question;
import com.jeopardyProject.Game.QuestionList;

/**
 * XML file parser implementing the Template Method pattern.
 * <p>
 * Parses question files in XML format using the Java DOM parser.
 * Expected XML structure:
 * <pre>
 * &lt;QuestionList&gt;
 *   &lt;QuestionItem&gt;
 *     &lt;Category&gt;Variables&lt;/Category&gt;
 *     &lt;Value&gt;100&lt;/Value&gt;
 *     &lt;QuestionText&gt;What is a variable?&lt;/QuestionText&gt;
 *     &lt;Options&gt;
 *       &lt;OptionA&gt;A memory location&lt;/OptionA&gt;
 *       &lt;OptionB&gt;A function&lt;/OptionB&gt;
 *       &lt;OptionC&gt;A class&lt;/OptionC&gt;
 *       &lt;OptionD&gt;A loop&lt;/OptionD&gt;
 *     &lt;/Options&gt;
 *     &lt;CorrectAnswer&gt;A&lt;/CorrectAnswer&gt;
 *   &lt;/QuestionItem&gt;
 * &lt;/QuestionList&gt;
 * </pre>
 * <p>
 * Error Handling:
 * <ul>
 *   <li>Logs parsing errors for individual questions</li>
 *   <li>Continues parsing remaining questions after errors</li>
 *   <li>Throws FileNotFoundException for malformed XML documents</li>
 * </ul>
 *
 * @see FileReaderTemplate
 * @author COMP3607 Jeopardy Project Team
 * @version 1.0
 */
public class XmlFileReader extends FileReaderTemplate{

    /**
     * Constructs an XmlFileReader for the specified file.
     *
     * @param filepath absolute path to the XML file
     */
    public XmlFileReader(String filepath){
        super(filepath);
    }

    /**
     * Parses questions from XML format using DOM parser.
     * <p>
     * Parses the XML document and extracts all &lt;QuestionItem&gt; elements.
     * Invalid questions are logged and skipped, allowing parsing to continue.
     * </p>
     *
     * @return QuestionList containing all successfully parsed questions
     * @throws FileNotFoundException if the XML file cannot be read or is malformed
     */
    @Override
    protected QuestionList parseQuestions() throws FileNotFoundException {
        QuestionList questions = new QuestionList();

        try{
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(new File(this.filepath));
            doc.getDocumentElement().normalize();

            NodeList questionItems = doc.getElementsByTagName("QuestionItem");

            for (int i = 0; i < questionItems.getLength(); i++){
                try{
                    Element questionElement = (Element) questionItems.item(i);
                    questions.addQuestion(parseQuestion(questionElement));
                } catch (Exception e){
                    System.err.println("Error parsing XML question: " + e.getMessage());
                }
            }
        } catch (FileNotFoundException e){
            throw e;
        } catch (Exception e){
            throw new FileNotFoundException("Failed to parse XML file: " + this.filepath);
        }
        return questions;
    }

    /**
     * Parses a single XML &lt;QuestionItem&gt; element into a Question object.
     * <p>
     * Extracts elements: Category, Value, QuestionText, Options (OptionA-D), CorrectAnswer
     * </p>
     *
     * @param questionElement XML Element representing a &lt;QuestionItem&gt;
     * @return Question object parsed from XML element
     * @throws NumberFormatException if Value element cannot be parsed as an integer
     * @throws NullPointerException if required elements are missing
     */
    private Question parseQuestion(Element questionElement) throws NumberFormatException {
        String category = questionElement.getElementsByTagName("Category").item(0).getTextContent();
        int value = Integer.parseInt(questionElement.getElementsByTagName("Value").item(0).getTextContent());
        String content = questionElement.getElementsByTagName("QuestionText").item(0).getTextContent();

        Element optionsElement = (Element) questionElement.getElementsByTagName("Options").item(0);
        HashMap<String, String> options = new HashMap<>();
        options.put("A", optionsElement.getElementsByTagName("OptionA").item(0).getTextContent());
        options.put("B", optionsElement.getElementsByTagName("OptionB").item(0).getTextContent());
        options.put("C", optionsElement.getElementsByTagName("OptionC").item(0).getTextContent());
        options.put("D", optionsElement.getElementsByTagName("OptionD").item(0).getTextContent());

        String answer = questionElement.getElementsByTagName("CorrectAnswer").item(0).getTextContent();
        return new Question(category, value, content, options, answer);
    }
}

