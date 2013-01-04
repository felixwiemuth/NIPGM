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
package nipgm.state;

import nipgm.data.Game;
import nipgm.gui.GUIFeedback;
import nipgm.gui.feedback.InfoDialog;

/**
 *
 * @author Felix Wiemuth
 */
public abstract class AbstractState {

    private static Game game;
    private AbstractState nextState;

    public AbstractState(AbstractState nextState) {
        this.nextState = nextState;
    }

//    private String title;
//    private String instructions;
    public void init() {
        game.getGUI().setView(this);
    }

//    public abstract void execute();
    public abstract void finish() throws Exception;

    public GUIFeedback goToNextState() {
        try {
            this.finish();
            game.getGUI().disableView();
            nextState.init();
            return null;
        } catch (Exception ex) {
            return new InfoDialog("Error", "Cannot continue: " + ex.getMessage());
        }
    }
}
