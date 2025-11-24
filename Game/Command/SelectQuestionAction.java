package Command;

public class SelectQuestionAction implements Action{
    private String category;
    private int value;
    
    @Override
    public void execute(Game gameInstance){
        // display table/list of question values
        // prompt player for selection (should only display remaining values)
        // search gameInstance.QuestionList for value matches
        // update gameInstance with selected Question
        ;
    }

    @Override
    public String toString(){
        return "implement logic";
    }
}
