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
package nipgm.data.impl;

import java.util.ArrayList;
import java.util.List;
import nipgm.control.Game;
import nipgm.data.Status;

/**
 *
 * @author Felix Wiemuth
 */
public class GameStatus implements Status {

    public enum State {

        IDLE;
    }
    private State state = State.IDLE;
    private List<GamePlayer> players = new ArrayList<>(); //all registered players (not necessary those playing the current task)
    private List<Task> tasks = new ArrayList<>(); //list of all played tasked - the last entry is the current task

    public Task getCurrentTask() {
        return tasks.get(tasks.size());
    }

    public State getState() {
        return state;
    }

    public String getStateName(State state) {
        return Game.getText("stateName_" + state.toString());
    }

    public String getStateName() {
        return getStateName(state);
    }

    public void addPlayer(GamePlayer player) {
        players.add(player);
    }
}
