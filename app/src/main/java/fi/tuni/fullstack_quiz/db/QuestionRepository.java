package fi.tuni.fullstack_quiz.db;

import android.app.Application;

import java.util.List;

/**
 * Holds methods to easily manage the database.
 */
public class QuestionRepository {

    private QuestionDao mQuestionDao;
    private List<Question> mAllQuestions;

    /**
     * Needs to be done within an AsyncTask. Initiates a connection to the database.
     *
     * @param application Application in use.
     */
    QuestionRepository(Application application) {
        QuestionRoomDatabase db = QuestionRoomDatabase.getDatabase(application);
        mQuestionDao = db.questionDao();
        mAllQuestions = mQuestionDao.getQuestions();
    }

    /**
     * Returns the questions fetched from the database when this repository was created.
     *
     * @return A List of questions.
     */
    public List<Question> getAllQuestions() {
        return mAllQuestions;
    }

    /**
     * Inserts the given question to the database.
     *
     * @param question The Question to add to the database.
     */
    public void insert(Question question) {
        QuestionRoomDatabase.databaseWriteExecutor.execute(() -> mQuestionDao.insert(question));
    }
}
