package com.jeopardyProject.Game.Template;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;

import com.jeopardyProject.Game.Question;
import com.jeopardyProject.Game.QuestionList;
import com.opencsv.*;

public class CsvFileReader extends FileReaderTemplate{
    private CSVReader csvReader;
    private String filepath;

    @Override
    public QuestionList readFile(String filepath) throws FileNotFoundException{
        this.filepath = filepath;
        initFileReader();
        return buildQuestionList();
    }

    @Override
    public void initFileReader() throws FileNotFoundException{
        this.csvReader = new CSVReader(new FileReader(this.filepath)); 
    }

    @Override
    public QuestionList buildQuestionList(){
        QuestionList questions = new QuestionList();
        String[] nextRecord;
        HashMap<String, String> options;
        
        try{
            nextRecord = this.csvReader.readNext();     // skips first row with headers

            while ((nextRecord = this.csvReader.readNext()) != null) {
                String category = nextRecord[0];
                int value = Integer.parseInt(nextRecord[1]);
                String content = nextRecord[2];
                options = new HashMap<>();
                options.put("A", nextRecord[3]);
                options.put("B", nextRecord[4]);
                options.put("C", nextRecord[5]);
                options.put("D", nextRecord[6]);
                String answer = nextRecord[7];
                
                Question question = new Question(category, value, content, options, answer);
                questions.addQuestion(question);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return questions;
    }
}
