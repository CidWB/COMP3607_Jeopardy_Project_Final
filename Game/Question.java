import java.util.ArrayList;
import java.util.List;

public class Question {

    private String category;
    private int value;
    private String content;
    private List<String> options; // change to HashMap
    private String correctAnswer;
    private List<String> answers;
    private boolean isAnswered;


    public String getCategory() {
        return category;
    }   
    public int getValue() {
        return value;
    }
    public void setValue(int value) {
        this.value = value;
    }
    public String getContent() {
        return content;
    }
    public List<String> getOptions() {
        return options;
    }
    public String getRightAnswer() {
        return correctAnswer;
    }
    public List<String> getAnswers() {
        return answers;
    }
    public boolean isAnswered() {
        return isAnswered;
    }
    public void setAnswered(boolean answered) {
        isAnswered = answered;
    }

    public Question(String category, int value, String content, List<String> options, String rightAnswer) {
        this.category = category;
        this.value = value;
        this.content = content;
        this.options = options;
        this.correctAnswer = rightAnswer;
        this.answers = new ArrayList<>();
        this.isAnswered = false;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Category: ").append(category).append("\n");
        sb.append("Value: ").append(value).append("\n");
        sb.append("Question: ").append(content).append("\n");
        if(options != null && !options.isEmpty())
            sb.append("Options: \n");
            for(int i = 0; i < options.size(); i++ ) {
                sb.append((i + 1)).append(". ").append(options.get(i)).append("\n");    
            }   

        return sb.toString();
    }   




    
}
