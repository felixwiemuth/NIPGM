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
package nipgm.control;

import nipgm.data.impl.GameStatus;
import nipgm.translations.Translator;

/**
 *
 * @author Felix Wiemuth
 */
public class Game {

    private static Game instance = new Game();

    private Game() {
    }

    public static Game getInstance() {
        return instance;
    }
    private Translator translator;
    private GameStatus status;

    public void run() {
    }

    public String getText(String key) {
        return translator.get(key);
    }

    public GameStatus getStatus() {
        return status;
    }
}
