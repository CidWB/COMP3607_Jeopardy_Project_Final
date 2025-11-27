package com.jeopardyProject.Game.State;

import com.jeopardyProject.Game.Command.*;

public class AnswerQuestionState implements PlayerState{
    public boolean isAllowed(Action action){
        if(action instanceof AnswerQuestionAction)
            return true;
        else
            return false;
    }
}
