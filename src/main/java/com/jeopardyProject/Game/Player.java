package com.jeopardyProject.Game;

import com.jeopardyProject.Game.Command.*;
import com.jeopardyProject.Game.State.*;

public class Player {
    private String playerId;
    private int score;
    private PlayerState state;
    private Action action;
    

    public Player(String playerId){
        this.playerId = playerId;
        this.setState(new WaitingState());
    }

    public String getPlayerId(){
        return this.playerId;
    }

    public void setState(PlayerState state){
        this.state = state;
    }

    public void setAction(Action action){            // sets action as whatever is permissible in current state
        if(state.isAllowed(action))
            this.action = action;
        else
            System.out.println("Invalid action attempted for " + playerId);
    }

    public void doAction(String input){
        this.action.execute(input);
    }

    public int getScore(){
        return this.score;
    }

    public void setScore(int score){
        this.score = score;
    }
}
