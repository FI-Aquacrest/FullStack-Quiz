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

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Question question);

    @Query("SELECT * FROM questions")
    List<Question> getQuestions();

    @Query("DELETE FROM questions")
    void deleteAll();
}
