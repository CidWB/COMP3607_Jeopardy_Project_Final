package Command;

public class AnswerQuestionAction implements Action{
    private Question question;
    
    @Override
    public void execute(Game gameInstance){
        // display question options
        // prompt player for selection
        // can use HashMap to get corresponding String for answer
        // update gameInstance with selected Question
        // if answer is correct can update question.isAnswered to true
        ;
    }

    @Override
    public String toString(){
        return "implement logic";
    }
}
