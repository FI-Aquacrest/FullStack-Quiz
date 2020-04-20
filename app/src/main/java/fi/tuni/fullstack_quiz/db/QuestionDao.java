package fi.tuni.fullstack_quiz.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

/**
 * Holds methods that handle the SQL queries.
 */
@Dao
public interface QuestionDao {

    /**
     * Adds a new Question -object to the database.
     *
     * @param question
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Question question);

    /**
     * Returns all Questions from the database in a List -object.
     *
     * @return List containing all of the Questions from the database.
     */
    @Query("SELECT * FROM questions")
    List<Question> getQuestions();

    /**
     * Deletes all rows from the database. Not used.
     */
    @Query("DELETE FROM questions")
    void deleteAll();
}
