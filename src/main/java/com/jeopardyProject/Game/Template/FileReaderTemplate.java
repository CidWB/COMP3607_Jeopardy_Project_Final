package com.jeopardyProject.Game.Template;

import java.io.FileNotFoundException;

import com.jeopardyProject.Game.QuestionList;

public abstract class FileReaderTemplate {
    
    public QuestionList readFile(String filepath) throws FileNotFoundException {
        initFileReader();
        return buildQuestionList();
    }

    protected abstract void initFileReader() throws FileNotFoundException;
    protected abstract QuestionList buildQuestionList();
}
