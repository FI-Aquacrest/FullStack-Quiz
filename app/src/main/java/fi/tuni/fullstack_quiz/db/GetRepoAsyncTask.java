package fi.tuni.fullstack_quiz.db;

import android.app.Application;
import android.os.AsyncTask;

/**
 * Handles the creation of a QuestionRepository object in a separate thread.
 */
public class GetRepoAsyncTask extends AsyncTask<Application, String, QuestionRepository> {

    /**
     * Creates a QuestionRepository object, then returns it.
     *
     * @param applications The application in use.
     * @return Created QuestionRepository object.
     */
    @Override
    protected QuestionRepository doInBackground(Application... applications) {
        return new QuestionRepository(applications[0]);
    }
}