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
 *
 * @author Felix Wiemuth
 */
public class CommandFeedback {

    private String title;
    private String message;

    public CommandFeedback(String title, String message) {
        this.title = title;
        this.message = message;
    }

    public CommandFeedback(CommandExecuteException ex) {
        title = ex.getClass().getName();
        message = ex.getMessage();
    }
}