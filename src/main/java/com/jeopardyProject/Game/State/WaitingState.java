package com.jeopardyProject.Game.State;

import com.jeopardyProject.Game.Command.*;

public class WaitingState implements PlayerState{
    public boolean isAllowed(Action action){    // no actions allowed in this state
            return false;
    }
}
