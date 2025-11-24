package Observer;
import java.util.ArrayList;
import Command.Action;

public class GameEventLogPublisher {
    private GameController controller = GameController.getInstance();
    private ArrayList<GameObserver> observers = new ArrayList<>();
    private GameEventLog log;

    public void addObserver(GameObserver observer){
        this.observers.add(observer);
    }

    public void removeObserver(GameObserver observer){
        this.observers.remove(observer);
    }

    public void notifyObservers(){
        for (GameObserver gameObserver : observers) {
            gameObserver.update(this.log);
        }
    }

    public void createLog(Action action, String playerId){
        int caseId = controller.getCaseID();
        String timestamp; // add timestamp
        this.log = new GameEventLog(caseId, playerId, timestamp, action);
    }
}
