package Command;

public class SelectCategoryAction implements Action{
    private String category;
    
    @Override
    public void execute(Game gameInstance){
        // display table of question catagories
        // prompt player for selection (should not be able to select categories that have no more questions)
        // search QuestionList for category matches
        // update gameInstance with ArrayList of matches
        ;
    }

    @Override
    public String toString(){
        return "implement logic";
    }
}
