package com.jeopardyProject.Game;

import java.util.ArrayDeque;

import com.jeopardyProject.Game.State.*;
import com.jeopardyProject.Game.Command.*;

public class PlayerList {
    private ArrayDeque<Player> players;

    public PlayerList(int numPlayers){
        this.players = new ArrayDeque<>(numPlayers);
    }

    public void addPlayer(String playerId){
        this.players.add(new Player(playerId));
    }

    private void switchPlayer(){     // move player to back of queue
        Player player = this.players.pop();
        this.players.add(player);
        player.setState(new WaitingState());
    }

    public Player getCurrentPlayer(){
        return this.players.peek();
    }

    public void selectCategory(String input){
        Player current = getCurrentPlayer();
        current.setState(new SelectCategoryState());
        current.setAction(new SelectCategoryAction());
        current.doAction(input);
    }

    public void selectQuestion(String input){
        Player current = getCurrentPlayer();
        current.setState(new SelectQuestionState());
        current.setAction(new SelectQuestionAction());
        current.doAction(input);
    }

    public void answerQuestion(String input){
        Player current = getCurrentPlayer();
        current.setState(new AnswerQuestionState());
        current.setAction(new AnswerQuestionAction());
        current.doAction(input);
    }

    public void endTurn(int value, boolean correct){
        int score = getCurrentPlayer().getScore();
        
        if(correct)
            getCurrentPlayer().setScore(score + value);    // award points
        else
            getCurrentPlayer().setScore(score - value);    // deduct points
        
        switchPlayer();
    }

    
}
