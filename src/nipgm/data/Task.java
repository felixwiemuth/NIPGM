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

import java.util.List;

/**
 *
 * @author Felix Wiemuth
 */
public class Task {

    private boolean isPreparing = true;
    private Question question;
    private List<AnswerItem> answers;
    private int correctAnswerIndex;

    public Task(Question question) {
        this.question = question;
    }

    public void addAnswer(Answer answer) throws Exception {
        if (!isPreparing) {
            throw new Exception("This task is closed. New answers cannot be added anymore.");
        }
        answers.add(new AnswerItem(answer));
    }

    public void closeTaskPrepartion() {
        isPreparing = false;
    }

    public void distributeCredits() throws Exception {
        if (isPreparing) {
            throw new Exception("Credits cannot be distributed before the task is closed!");
        }
    }
}
