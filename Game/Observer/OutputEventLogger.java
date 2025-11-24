package Observer;

public class OutputEventLogger implements GameObserver {
    private GameEventLog log;

    @Override
    public void update(GameEventLog log){
        this.log = log;
    }

    public void displayLog(){
        this.log.displayLog();
    }
}
