package com.jeopardyProject.Game;

import java.util.*;

public class QuestionList {
    private ArrayList<Question> questions = new ArrayList<>();
    LinkedHashSet<String> categorySet;
    List<String> categories;
    TreeSet<Integer> valueSet;
    Map<String, Map<Integer, Question>> grid;

    public void addQuestion(Question question){
        this.questions.add(question);
    }

    public ArrayList<Question> getQuestionArray(){
        return this.questions;
    }

    public ArrayList<Question> getQuestionsByCategory(String category){
        ArrayList<Question> newQuestions = new ArrayList<>();
        for(Question question : this.questions){
            if (question == null)   // just in case
                continue;

            if (question.getCategory().equalsIgnoreCase(category))
                newQuestions.add(question);
        }
        return newQuestions;
    }

    public boolean hasQuestions(){
        for (Question q : questions){
            if(q.getIsAnswered() == false)
                return true;
        }
        return false;       // if all questions have been answered
    }

    public void createQuestionGrid(){       // creates nested map that can be used for user selection
        if (questions == null || questions.isEmpty()) {
            System.out.println("No questions available.");
        }

        this.categorySet = new LinkedHashSet<>();
        this.valueSet = new TreeSet<>(Collections.reverseOrder());     // descending order
        this.grid = new HashMap<>();     // nested map for (category : (value : question))

        for (Question q : questions) {
            if (q == null) continue;
            String category = q.getCategory();
            int value = q.getValue();
            this.categorySet.add(category);
            this.valueSet.add(value);
            
            Map<Integer, Question> inner = grid.get(category);
            if (inner == null) {
                inner = new HashMap<>();
                grid.put(category, inner);
            }
            inner.put(value, q);
        }
    }

    private int[] formatGrid(){
        this.categories = new ArrayList<>(categorySet);
        
        int[] categoryWidths = new int[this.categories.size()];
        for (int i = 0; i < this.categories.size(); i++) {
            int width = this.categories.get(i).length();         // num of chars in category string
            Map<Integer, Question> map = this.grid.get(this.categories.get(i));
            if (map != null) {
                for (Integer value : valueSet) {
                    Question q = map.get(value);
                    String cell = "";
                    if (q != null && !q.getIsAnswered()){        // answered questions will have a smaller width than category
                            cell = "" + q.getValue();
                    }
                    if (cell.length() > width)
                        width = cell.length();   // format to fit contents
                }
            }
            categoryWidths[i] = Math.max(width, 3); // minimum width is 3 for formatting
        }
        return categoryWidths;
    }

    public void display(){
        int[] categoryWidths = formatGrid();    // must be called each time since table has to be updated

        StringBuilder sep = new StringBuilder();
        for (int width : categoryWidths) {
            sep.append("+");
            for (int i = 0; i < width + 2; i++) sep.append("-");
        }
        sep.append("+");
        System.out.println(sep.toString());

        StringBuilder fmt = new StringBuilder();
        for (int width : categoryWidths) {
            fmt.append("| %-").append(width).append("s ");
        }
        fmt.append("|\n");
        String format = fmt.toString();

        System.out.printf(format, categories.toArray());

        StringBuilder sep1 = new StringBuilder();
        for (int width : categoryWidths) {
            sep1.append("+");
            for (int i = 0; i < width + 2; i++) sep1.append("-");
        }
        sep1.append("+");
        System.out.println(sep1.toString());

        for (Integer v : valueSet) {
            Object[] row = new Object[categories.size()];
            for (int i = 0; i < categories.size(); i++) {
                Map<Integer, Question> map = grid.get(categories.get(i));
                String cell = "";
                if (map != null && map.containsKey(v)) {
                    Question q = map.get(v);
                    if(q.getIsAnswered())
                        cell = "X";
                    else
                        cell = String.valueOf(q.getValue());
                }
                row[i] = cell;
            }
            System.out.printf(format, row);
        }

        StringBuilder sep2 = new StringBuilder();
        for (int width : categoryWidths) {
            sep2.append("+");
            for (int i = 0; i < width + 2; i++) sep2.append("-");
        }
        sep2.append("+");
        System.out.println(sep2.toString());
    }
}
