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

public class XmlFileReader extends FileReaderTemplate{
    private  DocumentBuilder dBuilder;
    private File xmlFile;
    private Document doc;

    @Override
    public QuestionList readFile(String filepath) throws FileNotFoundException{
        this.xmlFile = new File(filepath);
        initFileReader();
        return buildQuestionList();
    }

    @Override
    public void initFileReader() throws FileNotFoundException{
        try {
            this.dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            this.doc = dBuilder.parse(xmlFile);
        } catch (javax.xml.parsers.ParserConfigurationException | org.xml.sax.SAXException | java.io.IOException e) {
            throw new RuntimeException("Failed to initialize XML parser for file: " + this.xmlFile.getPath(), e);
        }
    }

    @Override
    protected QuestionList buildQuestionList(){
        QuestionList questions = new QuestionList();
        HashMap<String, String> options;
        
        try{
            this.doc.getDocumentElement().normalize();
            NodeList questionItems = this.doc.getElementsByTagName("QuestionItem");
            
            for (int i = 0; i < questionItems.getLength(); i++) {
                Element questionElement = (Element) questionItems.item(i);
                
                String category = questionElement.getElementsByTagName("Category").item(0).getTextContent();
                int value = Integer.parseInt(questionElement.getElementsByTagName("Value").item(0).getTextContent());
                String content = questionElement.getElementsByTagName("QuestionText").item(0).getTextContent();
                
                Element optionsElement = (Element) questionElement.getElementsByTagName("Options").item(0);
                options = new HashMap<>();
                options.put("A", optionsElement.getElementsByTagName("OptionA").item(0).getTextContent());
                options.put("B", optionsElement.getElementsByTagName("OptionB").item(0).getTextContent());
                options.put("C", optionsElement.getElementsByTagName("OptionC").item(0).getTextContent());
                options.put("D", optionsElement.getElementsByTagName("OptionD").item(0).getTextContent());
                
                String answer = questionElement.getElementsByTagName("CorrectAnswer").item(0).getTextContent();
                
                Question question = new Question(category, value, content, options, answer);
                questions.addQuestion(question);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return questions;
    }
}

