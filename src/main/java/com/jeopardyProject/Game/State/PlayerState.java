package com.jeopardyProject.Game.State;

import com.jeopardyProject.Game.Command.*;

public interface PlayerState {
    public boolean isAllowed(Action action);
}
