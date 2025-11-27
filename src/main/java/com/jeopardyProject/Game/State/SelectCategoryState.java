package com.jeopardyProject.Game.State;

import com.jeopardyProject.Game.Command.*;

public class SelectCategoryState implements PlayerState{
    public boolean isAllowed(Action action){
        if(action instanceof SelectCategoryAction)
            return true;
        else
            return false;
    }
}
