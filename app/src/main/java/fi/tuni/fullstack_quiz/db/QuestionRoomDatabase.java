package fi.tuni.fullstack_quiz.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * RoomDatabase class for the questions.
 */
@Database(entities = {Question.class}, version = 2, exportSchema = false)
public abstract class QuestionRoomDatabase extends RoomDatabase {

    public abstract QuestionDao questionDao();

    private static volatile QuestionRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    // Returns the database, creates it from the given asset file if does not exist.
    static QuestionRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (QuestionRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            QuestionRoomDatabase.class, "question_database")
                            .createFromAsset("database/questions.db")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
