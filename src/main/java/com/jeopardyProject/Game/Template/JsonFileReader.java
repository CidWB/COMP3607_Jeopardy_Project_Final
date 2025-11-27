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

public class JsonFileReader extends FileReaderTemplate{
    private JsonElement jsonElement;
    private String filepath;

    @Override
    public QuestionList readFile(String filepath) throws FileNotFoundException{
        this.filepath = filepath;
        initFileReader();
        return buildQuestionList();
    }

    @Override
    public void initFileReader() throws FileNotFoundException{
        this.jsonElement = JsonParser.parseReader(new FileReader(this.filepath));
    }

    @Override
    protected QuestionList buildQuestionList(){
        QuestionList questions = new QuestionList();
        JsonArray jsonArray = jsonElement.getAsJsonArray();
        HashMap<String, String> options;
        
        try{            
            for (JsonElement item : jsonArray) {
                JsonObject jsonObject = item.getAsJsonObject();
                
                String category = jsonObject.get("Category").getAsString();
                int value = jsonObject.get("Value").getAsInt();
                String content = jsonObject.get("Question").getAsString();
                
                JsonObject optionsObj = jsonObject.get("Options").getAsJsonObject();
                options = new HashMap<>();
                options.put("A", optionsObj.get("A").getAsString());
                options.put("B", optionsObj.get("B").getAsString());
                options.put("C", optionsObj.get("C").getAsString());
                options.put("D", optionsObj.get("D").getAsString());
                
                String answer = jsonObject.get("CorrectAnswer").getAsString();
                
                Question question = new Question(category, value, content, options, answer);
                questions.addQuestion(question);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return questions;
    }
}

