package fi.tuni.fullstack_quiz;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Collections;
import java.util.LinkedList;
import java.util.concurrent.ExecutionException;

import fi.tuni.fullstack_quiz.db.Question;
import fi.tuni.fullstack_quiz.db.QuestionRepository;
import fi.tuni.fullstack_quiz.db.GetRepoAsyncTask;

/**
 * Provides core game functionality.
 */
public class GameActivity extends AppCompatActivity {

    // Holds all of the questions fetched or generated on startup.
    LinkedList<Question> questions;

    // Current question being asked.
    Question currentQuestion;

    // Prevents the app from attempting to release the SoundPool multiple times.
    boolean gameEnd = false;

    // Holds the required sound effects.
    private SoundPool soundPool;

    // sounds[0] = correct -sound
    // sounds[1] = incorrect -sound
    private int[] sounds = new int[2];

    int correctAnswers = 0;

    /**
     * Creates UI, starts music and loads sound effects.
     *
     * @param savedInstanceState Not used.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        MusicPlayer.InitMediaPlayer(this, R.raw.music);
        MusicPlayer.PlayMusic();

        soundPool = SoundPoolUtils.createSoundPool();
        sounds[0] = soundPool.load(this, R.raw.correct, 1);
        sounds[1] = soundPool.load(this, R.raw.incorrect, 1);

        getQuestions();
    }

    /**
     * Fills the questions -list with questions and shuffles the list.
     */
    public void getQuestions() {
        questions = new LinkedList<>();

        GetRepoAsyncTask asyncTask = new GetRepoAsyncTask();

        try {
            QuestionRepository repo = asyncTask.execute(getApplication()).get();

            questions.addAll(repo.getAllQuestions());

            // Remove random questions until there are only 10 left.
            while (questions.size() > 10) {
                questions.remove((int) (Math.random() * questions.size()));
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Log.d("QuestionAmount", Integer.toString(questions.size()));

        Collections.shuffle(questions);
        askQuestion();
    }

    /**
     * Displays a question by altering text within the UI objects.
     *
     * Whenever a question is asked, it is removed from the list to prevent
     * it from being asked again. When the list is empty, the game ends.
     */
    public void askQuestion() {
        if (questions.isEmpty()) {
            endGame();
        } else {
            currentQuestion = questions.getFirst();
            questions.remove(currentQuestion);

            TextView questionView = findViewById(R.id.question_box);
            questionView.setText(currentQuestion.getQuestion());

            Button answer1 = findViewById(R.id.answer1);
            answer1.setText(currentQuestion.getAnswer1());
            answer1.setClickable(true);

            Button answer2 = findViewById(R.id.answer2);
            answer2.setText(currentQuestion.getAnswer2());
            answer2.setClickable(true);

            Button answer3 = findViewById(R.id.answer3);
            answer3.setText(currentQuestion.getAnswer3());
            answer3.setClickable(true);

            Button answer4 = findViewById(R.id.answer4);
            answer4.setText(currentQuestion.getAnswer4());
            answer4.setClickable(true);
        }
    }

    /**
     * Checks if the given answer is correct.
     *
     * @param v Pressed answer-button.
     */
    public void answerQuestion(View v) {

        // Prevents the buttons from being clicked before the next question is asked.
        Button answer1 = findViewById(R.id.answer1);
        answer1.setClickable(false);

        Button answer2 = findViewById(R.id.answer2);
        answer2.setClickable(false);

        Button answer3 = findViewById(R.id.answer3);
        answer3.setClickable(false);

        Button answer4 = findViewById(R.id.answer4);
        answer4.setClickable(false);

        // Checks which button was pressed and determines if answer was correct.
        switch (v.getId()) {
            case R.id.answer1:
                if (currentQuestion.getCorrectIndex() == 0) {
                    correctAnswer(answer1);
                } else {
                    wrongAnswer(answer1);
                } break;

            case R.id.answer2:
                if (currentQuestion.getCorrectIndex() == 1) {
                    correctAnswer(answer2);
                } else {
                    wrongAnswer(answer2);
                } break;

            case R.id.answer3:
                if (currentQuestion.getCorrectIndex() == 2) {
                    correctAnswer(answer3);
                } else {
                    wrongAnswer(answer3);
                } break;

            case R.id.answer4:
                if (currentQuestion.getCorrectIndex() == 3) {
                    correctAnswer(answer4);
                } else {
                    wrongAnswer(answer4);
                } break;
        }
    }

    /**
     * Plays a sound, shows a text and turns the pressed button green to indicate a correct answer.
     *
     * @param button Button to be colored.
     */
    public void correctAnswer(View button) {
        soundPool.play(sounds[0],1, 1, 0, 0, 1f);
        button.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.MULTIPLY);

        correctAnswers++;

        TextView textView = findViewById(R.id.center_line);
        textView.setText("Correct!");
        textView.setTextColor(getResources().getColor(R.color.green));

        clearAnswer(button, textView);
    }

    /**
     * Plays a sound, shows a text and turns the pressed button red to indicate an incorrect answer.
     *
     * @param button Button to be colored.
     */
    public void wrongAnswer(View button) {
        soundPool.play(sounds[1],1, 1, 0, 0, 1f);
        button.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);

        TextView textView = findViewById(R.id.center_line);
        textView.setText("Wrong!");
        textView.setTextColor(getResources().getColor(R.color.red));

        clearAnswer(button, textView);
    }

    /**
     * Clears the pressed button's color and hides the middle text after a delay.
     *
     * @param button Pressed button that has been colored.
     * @param textView Middle text that displays "Correct" or "Incorrect"
     */
    public void clearAnswer(View button, TextView textView) {
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            button.getBackground().clearColorFilter();
            textView.setText("");
            askQuestion();
        }, 1000);
    }

    /**
     * Stops the music and hides the UI components to display a game-over screen.
     */
    public void endGame() {
        MusicPlayer.StopMusic();
        MusicPlayer.ReleaseMusic();
        gameEnd = true;

        TextView questionView = findViewById(R.id.question_box);
        TextView centerLine = findViewById(R.id.center_line);
        Button answer1 = findViewById(R.id.answer1);
        Button answer2 = findViewById(R.id.answer2);
        Button answer3 = findViewById(R.id.answer3);
        Button answer4 = findViewById(R.id.answer4);

        questionView.setVisibility(View.GONE);
        answer1.setVisibility(View.GONE);
        answer2.setVisibility(View.GONE);
        answer3.setVisibility(View.GONE);
        answer4.setVisibility(View.GONE);

        centerLine.setTextColor(getResources().getColor(R.color.gray));
        centerLine.setText("Game Over" + "\n" + "Final Score: " + correctAnswers + "/10");
        centerLine.setTextSize(40);

        // Exits the Activity after a 2.5 second game-over screen.
        Handler handler = new Handler();
        handler.postDelayed(this::finish, 2500);
    }

    /**
     * Pauses the music so it can be continued when the app is refocused.
     */
    @Override
    protected void onPause() {
        if (!gameEnd) {
            MusicPlayer.PauseMusic();
        }
        super.onPause();
    }

    /**
     * Continues the music,
     */
    @Override
    protected void onResume() {
        MusicPlayer.ResumeMusic();
        super.onResume();
    }

    /**
     * Stops the music and releases the MusicPlayer and the SoundPool.
     */
    @Override
    protected void onDestroy() {
        if (!gameEnd) {
            MusicPlayer.StopMusic();
            MusicPlayer.ReleaseMusic();
        }

        soundPool.release();
        soundPool = null;

        super.onDestroy();
    }
}
