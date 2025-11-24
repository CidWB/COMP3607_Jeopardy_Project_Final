public class GameConfig {
    private fileFormatStrategy strategy;
    // either use QuestionsList class or ArrayList<Question>
    // either use PlayerList, ArrayList<Player> or State design pattern

    public void setStrategy(){
        ;
    }

    public void loadQuestions(){
        // call strategy method for parsing logic
        // set returned list of questions as attribute
    }
}
