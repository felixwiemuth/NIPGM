/*
 * Copyright (C) 2013 - 2014 Felix Wiemuth
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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import nipgm.control.Game;
import nipgm.data.Question;
import nipgm.data.QuestionCategory;
import nipgm.data.db.DBGame;
import nipgm.data.db.DBPlayer;
import nipgm.data.db.DBQuestion;
import nipgm.data.db.DBQuestionCategory;
import nipgm.data.impl.GamePlayer;
import nipgm.data.impl.GameQuestionCategory;
import nipgm.data.persistence.PlayerMapper;
import nipgm.data.persistence.QuestionCategoryMapper;
import nipgm.data.persistence.QuestionMapper;
import nipgm.util.DatabaseUtil;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/**
 * The database service provides an interface to the database. It allows to
 * obtain objects from and write objects to the database. It throws different
 * exceptions when errors occur:
 * <ul>
 * <li><code>IllegalArgumentException</code>
 * </ul>
 * The excpetion message contains information about the reason of the problem
 * and, where appropriate, which parameters are concerned.
 *
 * @author Felix Wiemuth
 */
public class DatabaseService {

    public static class ObjectNotFoundException extends Exception {

        public ObjectNotFoundException(String message) {
            super(message);
        }
    }

    public static class ItemNotFoundException extends Exception {

        public ItemNotFoundException(String message) {
            super(message);
        }
    }
    private static final String RESOURCE_SQL_INITDB = "/nipgm/resources/sql/initializeDB.sql";
    private static final String RESOURCE_MYBATIS_CONFIG = "nipgm/resources/mybatis-config.xml";
    private final File database;
    private SqlSessionFactory sqlSessionFactory;
    //Important to store categories here: always use the same objects for one category entry of the DB
    private HashMap<Integer, GameQuestionCategory> questionCategoriesMap; //used for
//    private QuestionCategoryTreeNode categoryTree;
    private GameQuestionCategory rootCategory;

    /**
     * Create a new database service. Note: The database must already be
     * initialized by calling static method 'initDB()'.
     *
     * @param database
     * @param username
     * @param password
     * @throws IOException
     */
    public DatabaseService(File database, String username, String password) throws IOException {
        this.database = database;

        Properties properties = new Properties();
        properties.setProperty("username", username);
        properties.setProperty("password", password);
        properties.setProperty("url", DatabaseUtil.protocol + database.getCanonicalPath());

        InputStream inputStream = Resources.getResourceAsStream(RESOURCE_MYBATIS_CONFIG);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream, properties);
        loadCategories();
    }

    public DatabaseService(File database) throws IOException {
        this(database, "", "");
    }

    public GamePlayer loadPlayer(int id) throws ItemNotFoundException {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            PlayerMapper mapper = session.getMapper(PlayerMapper.class);
            DBPlayer dbplayer = mapper.selectPlayer(id);
            if (dbplayer == null) {
                throw new ItemNotFoundException(Game.getFormattedText("ex_playerNotFound", id));
            }
            return new GamePlayer(dbplayer);
        }
    }

    /**
     * Get a list of all player objects in the database.
     *
     * @return
     */
    public List<DBPlayer> getAllPlayers() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            PlayerMapper mapper = session.getMapper(PlayerMapper.class);
            return mapper.selectAllPlayers();
        }
    }

    /**
     * Saves a new player with name 'name' to the database.
     *
     * @param name
     */
    public GamePlayer createPlayer(String name) {
        DBPlayer dbplayer = new DBPlayer(name);
        try (SqlSession session = sqlSessionFactory.openSession()) {
            PlayerMapper mapper = session.getMapper(PlayerMapper.class);
            mapper.insertPlayer(dbplayer);
            session.commit();
        }
        return new GamePlayer(dbplayer);
    }

    private void loadCategories() {
        Set<DBQuestionCategory> dbQuestionCategories;
        Set<GameQuestionCategory> questionCategories = new HashSet<>();
        Map<GameQuestionCategory, Integer> parentIDs = new HashMap<>();
        questionCategoriesMap = new HashMap<>();
        try (SqlSession session = sqlSessionFactory.openSession()) {
            QuestionCategoryMapper mapper = session.getMapper(QuestionCategoryMapper.class);
            dbQuestionCategories = new HashSet<>(mapper.selectAllCategories()); //TODO directly get as set from myBatis
        }

        Set<GameQuestionCategory> questionCategoriesUnattached = new HashSet<>(); //the nodes where to find a parent for (initialized with all categories but the root category)

        //convert to GameQuestionCategory
        for (DBQuestionCategory dbCategory : dbQuestionCategories) {
            GameQuestionCategory category = new GameQuestionCategory(dbCategory);
            questionCategories.add(category);
            questionCategoriesMap.put(category.getID(), category);
            if (dbCategory.getParentCat() == null) { //found root
                rootCategory = category;
            } else {
                questionCategoriesUnattached.add(category);
            }
            parentIDs.put(category, dbCategory.getParentCat());
        }

        //build tree
        int size;
        do { //while the size of unattached categories shrinks, try to attach
            size = questionCategoriesUnattached.size();
            for (Iterator<GameQuestionCategory> it = questionCategoriesUnattached.iterator(); it.hasNext();) {
                GameQuestionCategory node = it.next();
                Integer nodeParent = parentIDs.get(node);
                for (GameQuestionCategory parent : questionCategories) {
                    if (parent.getID() == nodeParent) {
                        parent.addChildCategory(node);
                        it.remove();
                        break; //continue with next node without parent
                    }
                }
            }
        } while (size > questionCategoriesUnattached.size());

        //now all non-root nodes should be attached
        if (size != 0) {
            throw new IllegalStateException("The category data in the database is corrupted or there is a bug in the software.");
        }
        if (rootCategory == null) {
            throw new IllegalStateException("Corrupted database: no root category was found.");
        }
    }

    /**
     * Create a new 'QuestionCategory'.
     *
     * @param name
     * @param description
     * @param parentCategory the parent category (required, there is only one
     * default root category)
     * @return
     */
    public QuestionCategory createQuestionCategory(String name, String description, QuestionCategory parentCategory) {
        checkNotNull(parentCategory, "parentCategory");
        try (SqlSession session = sqlSessionFactory.openSession()) {
            QuestionCategoryMapper mapper = session.getMapper(QuestionCategoryMapper.class);
            DBQuestionCategory dbcategory = new DBQuestionCategory(name, description, parentCategory.getID());
            mapper.insertCategory(dbcategory);
            session.commit();
            GameQuestionCategory category = new GameQuestionCategory(dbcategory);
            questionCategoriesMap.put(category.getID(), category);
            questionCategoriesMap.get(dbcategory.getParentCat()).addChildCategory(category); //insert into category tree
            return category;
        }
    }

    /**
     * Get the root category. This gives access to the whole category tree which
     * was loaded on construction. All changes to the category tree done over
     * this class' interface are also reflected in the tree obtained.
     *
     * @return
     */
    public QuestionCategory getRootCategory() {
        return rootCategory;
    }

    private List<DBQuestionCategory> getAllCategories() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            QuestionCategoryMapper mapper = session.getMapper(QuestionCategoryMapper.class);
            return mapper.selectAllCategories();
        }
    }

    public Question loadQuestion(int id) throws ObjectNotFoundException {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            QuestionMapper mapper = session.getMapper(QuestionMapper.class);
            DBQuestion dbquestion = mapper.selectQuestion(id);
            if (dbquestion == null) {
                throw new ObjectNotFoundException(Game.getFormattedText("ex_questionNotFound", id));
            }
            return new Question(dbquestion.getQuestion(),
                    questionCategoriesMap.get(dbquestion.getCatID()),
                    dbquestion.getAnswer());
        }
    }

    public List<DBQuestion> getAllDBQuestions() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            QuestionMapper mapper = session.getMapper(QuestionMapper.class);
            List<DBQuestion> questions = mapper.selectAllQuestions();
            return questions;
        }
    }

    public void addQuestion(Question question) {
        DBQuestion dbquestion = new DBQuestion(question.getText(), question.getAnswer().getText(), question.getCategory().getID());
        try (SqlSession session = sqlSessionFactory.openSession()) {
            QuestionMapper mapper = session.getMapper(QuestionMapper.class);
            int rows = mapper.insertQuestion(dbquestion);
            session.commit();
        }
    }

    /**
     *
     * @return the auto-generated ID of the game
     */
    public int createDBGame() { //TODO make private
        try (SqlSession session = sqlSessionFactory.openSession()) {
            DBMapper mapper = session.getMapper(DBMapper.class);
            DBGame dbgame = new DBGame();
            mapper.insertGame(dbgame);
            session.commit();
            return dbgame.getId();
        }
    }

    /**
     *
     * @param database
     * @throws nipgm.util.DatabaseUtil.ConnectException
     * @throws FileNotFoundException
     * @throws SQLException
     * @throws IOException
     */
    public static void initDB(File database) throws DatabaseUtil.ConnectException, FileNotFoundException, SQLException, IOException {
        try (Connection conn = DatabaseUtil.getConnection(database);
                Reader reader = new BufferedReader(new InputStreamReader(DatabaseService.class.getResourceAsStream(RESOURCE_SQL_INITDB)))) {
            ScriptRunner runner = new ScriptRunner(conn);
            runner.runScript(reader);
        }
        //TODO do without DatabaseService?
        DatabaseService dbservice = new DatabaseService(database);
        dbservice.insertProgramVersion();
        //TODO need to shut down DatabaseService?
    }

    private void insertProgramVersion() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            DBMapper mapper = session.getMapper(DBMapper.class);
            mapper.insertProperty("application_version", Game.version);
            session.commit();
        }
    }

    private void checkNotNull(Object o, String parameterName) throws IllegalArgumentException {
        if (o == null) {
            throw new IllegalArgumentException(String.format("Cannot perform database operation: parameter '%s' must not be null!", parameterName));
        }
    }
}
