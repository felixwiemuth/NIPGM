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
package nipgm.database;

import nipgm.data.db.DBGame;
import org.apache.ibatis.annotations.Param;

/**
 * The interface to the SQL code defined in XML files. This includes all tasks
 * that cannot be directly associated with one class (so that a seperate mapper
 * could be used).
 *
 * @author Felix Wiemuth
 */
public interface DBMapper {

    public void insertGame(DBGame game);

    public void insertProperty(@Param("key") String key, @Param("value") String value);
}
