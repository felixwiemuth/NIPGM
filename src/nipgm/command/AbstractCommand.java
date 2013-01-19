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

import nipgm.control.Game;
import nipgm.data.impl.GameStatus.State;

/**
 *
 * @author Felix Wiemuth
 */
public abstract class AbstractCommand {

    public CommandFeedback execute() throws CommandExecuteException {
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

    /**
     * Get the current state of the game.
     *
     * @return
     */
    protected State state() {
        return Game.getInstance().getStatus().getState();
    }
}
