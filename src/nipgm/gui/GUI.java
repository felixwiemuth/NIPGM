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
package nipgm.gui;

import nipgm.state.AbstractState;
import nipgm.state.ShowQuestion;

/**
 *
 * @author Felix Wiemuth
 */
public class GUI implements AbstractGUI {

    @Override
    public void setView(AbstractState state) {
        if (state instanceof ShowQuestion) {
            //TODO set view
        } else {
            //TODO error
        }
    }

    @Override
    public void disableView() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
