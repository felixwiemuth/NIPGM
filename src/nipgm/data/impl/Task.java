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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nipgm.control.Game;
import nipgm.data.Answer;
import nipgm.data.AnswerItem;
import nipgm.data.Question;

/**
 * A 'Task' represents the data used for one round. This includes question,
 * players, answers etc.
 *
 * @author Felix Wiemuth
 */
public class Task {

    public enum Stage {

        PREPARING, VOTING, CREDTIS, FINISHED;
    }

    /**
     * Describes the status of a player regarding the current round.
     */
    private class PlayerStatus {

        private GamePlayer player;
        private boolean submittedAnswer = false;
        private boolean hasVoted = false;
        private int creditsEarned = 0; //credits earned until now in this round

        public PlayerStatus(GamePlayer player) {
            this.player = player;
        }

        public void submitAnswer(AnswerItem answerItem) throws Exception {
            if (submittedAnswer) {
                throw new Exception(Game.getText("ex_cannotAddAnswerAlreadySubmitted"));
            }
            answers.add(answerItem);
            submittedAnswer = true;
        }

        public void vote(int answerIndex) throws Exception {
            if (hasVoted) {
                throw new Exception(Game.getText("ex_alreadyVoted"));
            }
            try {
                answers.get(answerIndex).addVote(player);
                hasVoted = true;
            } catch (IndexOutOfBoundsException ex) {
                throw new Exception(Game.getText("ex_answerNumberNotExists"));
            }
        }

        public void addCredits(int credits) {
            creditsEarned += credits;
        }

        public int getCreditsEarned() {
            return creditsEarned;
        }

        public void addCreditsToAccount() {
            player.addCredits(creditsEarned);
        }
    }
    private Stage stage = Stage.PREPARING;
    private Question question;
    private List<AnswerItem> answers = new ArrayList<>();
    private int correctAnswerIndex;
    private Map<GamePlayer, PlayerStatus> playerStatus = new HashMap<>(); //TODO initialize

    public Task(Question question, List<GamePlayer> players) {
        this.question = question;
        answers.add(new AnswerItem(question.getAnswer()));
        for (GamePlayer player : players) {
            playerStatus.put(player, new PlayerStatus(player));
        }
    }

    public Question getQuestion() {
        return question;
    }

    public void addAnswer(Answer answer, GamePlayer player) throws Exception {
        if (!(stage == Stage.PREPARING)) {
            throw new Exception(Game.getText("ex_cannotAddAnswersAnymore"));
        }
        playerStatus.get(player).submitAnswer(new AnswerItem(answer, player));
    }

    public void addVote(int answerIndex, GamePlayer player) throws Exception {
        playerStatus.get(player).vote(answerIndex);
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
            throw new Exception(Game.getText("ex_cannotDistributeCreditsBeforeVoting"));
        }
        if (stage.compareTo(Stage.CREDTIS) > 0) {
            throw new Exception(Game.getText("ex_creditsAlreadyDistributed"));
        }
        calculateCredits();
        for (PlayerStatus p : playerStatus.values()) {
            p.addCreditsToAccount();
        }
    }

    private void calculateCredits() {
    }
}
