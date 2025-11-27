package com.jeopardyProject.Game.State;

import com.jeopardyProject.Game.Command.*;

public class SelectQuestionState implements PlayerState{
    public boolean isAllowed(Action action){
        if(action instanceof SelectQuestionAction)
            return true;
        else
            return false;
    }
}
