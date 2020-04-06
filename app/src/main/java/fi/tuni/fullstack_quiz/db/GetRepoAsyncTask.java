package fi.tuni.fullstack_quiz.db;

import android.app.Application;
import android.os.AsyncTask;

public class GetRepoAsyncTask extends AsyncTask<Application, String, QuestionRepository> {

    @Override
    protected QuestionRepository doInBackground(Application... applications) {
        return new QuestionRepository(applications[0]);
    }
}