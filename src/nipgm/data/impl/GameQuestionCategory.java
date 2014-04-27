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
import nipgm.data.QuestionCategory;
import nipgm.data.db.DBQuestionCategory;

/**
 * Implementation of <code>QuestionCategory</code>.
 *
 * @author Felix Wiemuth
 */
public class GameQuestionCategory implements QuestionCategory {

    private final DBQuestionCategory dbQuestionCategory;
    private final List<GameQuestionCategory> childCategories = new ArrayList<>();

    public GameQuestionCategory(DBQuestionCategory dbQuestionCategory) {
        this.dbQuestionCategory = dbQuestionCategory;
    }

    @Override
    public int getID() {
        return dbQuestionCategory.getID();
    }

    @Override
    public String getName() {
        return dbQuestionCategory.getName();
    }

    @Override
    public String getDescription() {
        return dbQuestionCategory.getDescription();
    }

    public boolean addChildCategory(GameQuestionCategory category) {
        return childCategories.add(category);
    }

    @Override
    public List<? extends QuestionCategory> getChildCategories() {
        return childCategories;
    }
}
