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
import nipgm.data.Answer;
import nipgm.data.AnswerItem;
import nipgm.data.Question;
import nipgm.translations.Texts;

/**
 *
 * @author Felix Wiemuth
 */
public class Task {

    public enum Stage {

        PREPARING, VOTING, CREDTIS, FINISHED;
    }
    //private Texts texts = Game.getInstance().
    private Stage stage = Stage.PREPARING;
    private Question question;
    private List<AnswerItem> answers = new ArrayList<>();
    private int correctAnswerIndex;

    public Task(Question question, Texts texts) {
        this.question = question;
        answers.add(new AnswerItem(question.getAnswer()));
    }

    public Question getQuestion() {
        return question;
    }

    public void addAnswer(Answer answer) throws Exception {
        if (!(stage == Stage.PREPARING)) {
            throw new Exception(Game.getText("error_cannotAddAnswersAnymore"));
        }
        answers.add(new AnswerItem(answer));
    }

    public void mergeAnswers(int index1, int index2) {
        //TODO implement
    }

    public void closeTaskPrepartion() {
        if (stage == Stage.PREPARING) {
            stage = Stage.VOTING;
        }
    }

    //TODO voting + close voting
    public void distributeCredits() throws Exception {
        if (stage.compareTo(Stage.CREDTIS) < 0) {
            throw new Exception(Game.getText("error_cannotDistributeCreditsBeforeVoting"));
        }
        if (stage.compareTo(Stage.CREDTIS) > 0) {
            throw new Exception(Game.getText("error_creditsAlreadyDistributed"));
        }
        //TODO do distribution
    }
}
