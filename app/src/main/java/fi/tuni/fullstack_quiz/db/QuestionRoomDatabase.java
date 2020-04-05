package fi.tuni.fullstack_quiz.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Question.class}, version = 1, exportSchema = false)
public abstract class QuestionRoomDatabase extends RoomDatabase {

    public abstract QuestionDao questionDao();

    private static volatile QuestionRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static QuestionRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (QuestionRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            QuestionRoomDatabase.class, "question_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);

            databaseWriteExecutor.execute(() -> {
                QuestionDao dao = INSTANCE.questionDao();
                dao.deleteAll();
                dao.insert(new Question("Which British boxer is nicknamed ‘King Khan’?",
                        "Amir Khan", "John Adams",
                        "Joseph McCarthy", "Ben Summers", 0));

                dao.insert(new Question("Complete the title of a famous hit for " +
                        "The Clovers \"Love Potion No….\"?", "15", "22",
                        "1", "9", 3));

                dao.insert(new Question("In which English city is Meadowhall " +
                        "Railway Station?", "Bristol", "Wells",
                        "Sheffield", "Oxford", 2));

                dao.insert(new Question("Who became quizmaster of the BBC’s " +
                        "‘University Challenge’ in 1994?", "Theresa Lemaire",
                        "Jeremy Paxman", "Louis Myers",
                        "Gregory Dykes", 1));

                dao.insert(new Question("David Lloyd George was British Prime Minister " +
                        "during the reign of which monarch?", "Henry VIII",
                        "Elizabeth I", "George V",
                        "James II", 2));

                dao.insert(new Question("In which 2000 film starring " +
                        "Jude Law does former boxer Ricky Grover make a cameo appearance?",
                        "Cast Away", "Love, Honour and Obey",
                        "What Lies Beneath", "The Big Tease", 1));

                dao.insert(new Question("The Belgian beer ‘Kriek’ is flavoured with " +
                        "which fruit?", "Citrus", "Banana", "Cherry",
                        "Pineapple", 2));

                dao.insert(new Question("In the game of ‘Connect Four’, how many " +
                        "counters must a player get in a row to win a game?",
                        "3", "8", "40", "4", 3));

                dao.insert(new Question("The 18th Century Convention of Kanagawa was " +
                        "a ‘Treaty of Amity and Friendship’ between Japan and which other country?",
                        "United States", "France", "Russia",
                        "United Kingdom", 0));

                dao.insert(new Question("In computing, how many bits are in one byte?",
                        "32", "8", "16", "64", 1));
            });
        }
    };
}
