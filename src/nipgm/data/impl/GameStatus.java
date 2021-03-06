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
import nipgm.data.Task;

/**
 *
 * @author Felix Wiemuth
 */
public class GameStatus implements Status {

    public enum State {

        IDLE,
        SHOW_QUESTION,
        ENTER_ANSWERS,
        SHOW_ANSWERS,
        ENTER_VOTES,
        SHOW_RESULTS;
    }
    private State state = State.IDLE;
    private List<GamePlayer> players = new ArrayList<>(); //all registered players (not necessary those playing the current task)
    private List<GameTask> tasks = new ArrayList<>(); //list of all played tasked - the last entry is the current task

    @Override
    public GameTask getCurrentTask() {
        return tasks.get(tasks.size());
    }

    @Override
    public List<? extends Task> getTasks() {
        //TODO implement
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
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
