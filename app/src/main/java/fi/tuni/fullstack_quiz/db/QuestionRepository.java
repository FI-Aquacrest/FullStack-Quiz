package fi.tuni.fullstack_quiz.db;

import android.app.Application;

import java.util.List;

public class QuestionRepository {

    private QuestionDao mQuestionDao;
    private List<Question> mAllQuestions;

    QuestionRepository(Application application) {
        QuestionRoomDatabase db = QuestionRoomDatabase.getDatabase(application);
        mQuestionDao = db.questionDao();
        mAllQuestions = mQuestionDao.getQuestions();
    }

    public List<Question> getAllQuestions() {
        return mAllQuestions;
    }

    public void insert(Question question) {
        QuestionRoomDatabase.databaseWriteExecutor.execute(() -> mQuestionDao.insert(question));
    }
}
