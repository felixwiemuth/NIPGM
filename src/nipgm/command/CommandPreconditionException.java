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

/**
 * Indicates that a command could not be executed because one or more
 * preconditions (see <code>CommandPrecondition</code>) for the command were not
 * met.
 *
 * @author Felix Wiemuth
 */
public class CommandPreconditionException extends CommandExecuteException {

    /**
     * Creates a new instance of <code>CommandPreconditionException</code>
     * without detail message.
     */
    public CommandPreconditionException() {
    }

    /**
     * Constructs an instance of <code>CommandPreconditionException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public CommandPreconditionException(String msg) {
        super(msg);
    }
}
