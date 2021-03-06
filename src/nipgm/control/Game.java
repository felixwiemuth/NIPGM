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

import java.io.File;
import nipgm.data.impl.GameConfiguration;
import nipgm.data.impl.GameStatus;
import nipgm.database.DatabaseService;
import nipgm.translations.Translator;

/**
 *
 * @author Felix Wiemuth
 */
public class Game {

    public static final String version = "0.x.x Alpha";
    private static Game instance = new Game();
    private static File jarDirectory = new File(Game.class.getProtectionDomain().getCodeSource().getLocation().getPath());
    private Translator translator = new Translator(new File(jarDirectory, "translations"));
    private GameConfiguration config = new GameConfiguration();
    private GameStatus status = new GameStatus();
    //private DatabaseService dbservice = new DatabaseService();

    private Game() {
        //TODO load translation from prereferences
        //translator.loadTranslation(new File("en")); //load standard translation
        translator.loadTranslation(translator.getAvailableTranslations()[0]);
    }

    public static Game getInstance() {
        return instance;
    }

    public void run() {
    }

    public static String getText(String key) {
        return instance.translator.get(key);
    }

    public static String getFormattedText(String key, Object... args) {
        return String.format(instance.translator.get(key), args);
    }

    public static GameConfiguration getConfig() {
        return instance.config;
    }

    public static GameStatus getStatus() {
        return instance.status;
    }
}
