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
package nipgm.data;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Felix Wiemuth
 */
public class AnswerItem {

    private Answer answer;
    private List<Player> authors = new ArrayList<>(); //TODO correct answer: always empty author list?? (-> merge answers!)
    private boolean isCorrect;
    List<Player> votes;

    /**
     * Create a correct answer.
     *
     * @param answer
     */
    public AnswerItem(Answer answer) {
        this.answer = answer;
        isCorrect = true;
    }

    /**
     * Create a players answer.
     *
     * @param answer
     * @param author
     */
    public AnswerItem(Answer answer, Player author) {
        this.answer = answer;
        isCorrect = false;
        authors.add(author);
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public Answer getAnswer() {
        return answer;
    }
}
