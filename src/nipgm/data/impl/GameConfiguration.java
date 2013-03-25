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

import nipgm.data.QuestionCategory;
import java.util.HashMap;
import java.util.Map;

/**
 * Includes everything configurable.
 *
 * @author Felix Wiemuth
 */
public class GameConfiguration {

    public enum AwardType {

        VOTED_CORRECT_ANSWER,
        GOT_PLAYERS_VOTE;
    }
    private Map<AwardType, Integer> baseCredits = new HashMap<>();
    private Map<QuestionCategory, Integer> questionTypeFactor = new HashMap<>();

    public int getCreditAmount(AwardType awardType, QuestionCategory questionType) {
        return baseCredits.get(awardType) * questionTypeFactor.get(questionType);
    }
}
