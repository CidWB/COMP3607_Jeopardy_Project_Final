package com.jeopardyProject.Game;

import java.util.ArrayDeque;
import java.util.ArrayList;

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

    public ArrayList<Player> getAllPlayers(){
        return new ArrayList<>(this.players);
    }

    public void selectCategory(String input){   // works
        Player current = getCurrentPlayer();
        current.setState(new SelectCategoryState());
        current.setAction(new SelectCategoryAction());
        current.doAction(input);
    }

    public void selectQuestion(String input){
        Player current = getCurrentPlayer();
        System.out.println("Player: "+ current.getPlayerId());
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

    public void endTurn(int value, boolean correct){    // don't need separate Scorer class since code is very minimal
        int score = getCurrentPlayer().getScore();
        
        if(correct){
            getCurrentPlayer().setScore(score + value);
            System.out.println("Correct!");
        }
        else{
            getCurrentPlayer().setScore(score - value);
            System.out.println("Not quite.");
        }
            
        
        switchPlayer();
    }

    
}
