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
package nipgm.data.db;

/**
 * The DB representation correspondig to nipgm.data.Question.
 *
 * @author Felix Wiemuth
 */
public class DBQuestion {

    private int id;
    private String question;
    private String answer;
    private int catID;

    public DBQuestion() {
    }

    public DBQuestion(String question, String answer, int catID) {
        this.question = question;
        this.answer = answer;
        this.catID = catID;
    }

    public int getID() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public int getCatID() {
        return catID;
    }
}
