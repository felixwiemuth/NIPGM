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

import nipgm.data.GameStatus.State;

/**
 *
 * @author Felix Wiemuth
 */
public abstract class AbstractCommand {

    private static State state;

    public abstract CommandFeedback execute() throws CommandExecuteException;

    public CommandFeedback execute(FeedbackHandler exceptionHandler) {
        try {
            return execute();
        } catch (CommandExecuteException ex) {
            CommandFeedback feedback = new CommandFeedback(ex);
            exceptionHandler.handleFeedback(feedback);
            return feedback;
        }
    }

    public CommandFeedback execute(FeedbackHandler exceptionHandler, FeedbackHandler resultHandler) {
        CommandFeedback result = execute(exceptionHandler);
        resultHandler.handleFeedback(result);
        return result;
    }

    /**
     * Get the current state of the game.
     *
     * @return
     */
    protected State state() {
        return state;
    }

    public void setState(State state) {
        AbstractCommand.state = state;
    }
}
