/*
 * Copyright (C) 2013 Felix Wiemuth
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package nipgm.command;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import nipgm.control.Game;
import nipgm.data.impl.GameStatus.State;

/**
 * Base class for commands from the user to the game. Subclasses define concrete
 * commands. Every subclass should define the command state permissions in a
 * static part using 'putStatePermissions(Class commandClass, StatePermissions
 * p)'.
 *
 * @author Felix Wiemuth
 */
public abstract class AbstractCommand {

    /**
     * Contains the states in which the command is allowed to be executed and
     * optional special exceptions to be thrown in special disallowed states.
     */
    protected static class StatePermissions {

        private final Set<State> allowedStates = new HashSet<>();
        private final Map<State, CommandExecuteException> disallowedStateExceptions = new HashMap<>();

        public StatePermissions(State... allowedStates) {
            this.allowedStates.addAll(Arrays.asList(allowedStates));
        }

        public void putDisallowedStateException(State state, CommandExecuteException ex) {
            disallowedStateExceptions.put(state, ex);
        }
    }
    protected final static Map<Class, StatePermissions> statePermissions = new HashMap<>();

    public CommandFeedback execute() throws CommandExecuteException {
        StatePermissions p = statePermissions.get(this.getClass());
        if (!p.allowedStates.contains(Game.getStatus().getState())) {
            CommandExecuteException ex = p.disallowedStateExceptions.get(Game.getStatus().getState());
            if (ex == null) {
                //TODO name current state, allowed states
                ex = new CommandExecuteException(Game.getText("ex_CommandNotAllowedInCurrentState"));
            }
            throw ex;
        }
        try {
            return exec();
        } catch (CommandExecuteException ex) {
            //TODO log exception
            throw ex;
        }
    }

    public CommandFeedback execute(ExceptionHandler exceptionHandler) {
        try {
            return execute();
        } catch (CommandExecuteException ex) {
            //TODO log exception
            CommandFeedback feedback = new CommandFeedback(ex);
            exceptionHandler.handleException(ex);
            return feedback;
        }
    }

    public CommandFeedback execute(ExceptionHandler exceptionHandler, FeedbackHandler resultHandler) {
        //TODO log exception
        CommandFeedback result = execute(exceptionHandler);
        resultHandler.handleFeedback(result);
        return result;
    }

    protected abstract CommandFeedback exec() throws CommandExecuteException;

    protected static void putStatePermissions(Class commandClass, StatePermissions p) {
        statePermissions.put(commandClass, p);
    }
}
