package fi.tuni.fullstack_quiz;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.Collections;
import java.util.LinkedList;

/**
 * Provides core game functionality.
 */
public class GameActivity extends Activity {

    // Holds all of the questions fetched or generated on startup.
    LinkedList<Question> questions = new LinkedList<>();

    // Current question being asked.
    Question currentQuestion;

    // Prevents the app from attempting to release the SoundPool multiple times.
    boolean gameEnd = false;

    // Holds the required sound effects.
    private SoundPool soundPool;

    // sounds[0] = correct -sound
    // sounds[1] = incorrect -sound
    private int[] sounds = new int[2];

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

        generateQuestions();
    }

    /**
     * Fills the questions -list with questions and shuffles the list.
     */
    public void generateQuestions() {
        questions.add(new Question("Which British boxer is nicknamed ‘King Khan’?",
                        new String[]{"Amir Khan", "John Adams", "Joseph McCarthy", "Ben Summers"}, 0));
        questions.add(new Question("Complete the title of a famous hit for The Clovers \"Love Potion No….\"?",
                        new String[]{"15", "22", "1", "9"}, 3));
        questions.add(new Question("In which English city is Meadowhall Railway Station?",
                new String[]{"Bristol", "Wells", "Sheffield", "Oxford"}, 2));
        questions.add(new Question("Who became quizmaster of the BBC’s ‘University Challenge’ in 1994?",
                new String[]{"Theresa Lemaire", "Jeremy Paxman", "Louis Myers", "Gregory Dykes"}, 1));
        questions.add(new Question("David Lloyd George was British Prime Minister during the reign of which monarch?",
                new String[]{"Henry VIII", "Elizabeth I", "George V", "James II"}, 2));
        questions.add(new Question("In which 2000 film starring Jude Law does former boxer Ricky Grover make a cameo appearance?",
                new String[]{"Cast Away", "Love, Honour and Obey", "What Lies Beneath", "The Big Tease"}, 1));
        questions.add(new Question("The Belgian beer ‘Kriek’ is flavoured with which fruit?",
                new String[]{"Citrus", "Banana", "Cherry", "Pineapple"}, 2));
        questions.add(new Question("In the game of ‘Connect Four’, how many counters must a player get in a row to win a game?",
                new String[]{"3", "8", "40", "4"}, 3));
        questions.add(new Question("The 18th Century Convention of Kanagawa was a ‘Treaty of Amity and Friendship’ between Japan and which other country?",
                new String[]{"United States", "France", "Russia", "United Kingdom"}, 0));
        questions.add(new Question("In computing, how many bits are in one byte?",
                new String[]{"32", "8", "16", "64"}, 1));

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
        }

        TextView questionView = findViewById(R.id.question_box);
        questionView.setText(currentQuestion.getQuestion());

        String[] answers = currentQuestion.getAnswers();

        Button answer1 = findViewById(R.id.answer1);
        answer1.setText(answers[0]);
        answer1.setClickable(true);

        Button answer2 = findViewById(R.id.answer2);
        answer2.setText(answers[1]);
        answer2.setClickable(true);

        Button answer3 = findViewById(R.id.answer3);
        answer3.setText(answers[2]);
        answer3.setClickable(true);

        Button answer4 = findViewById(R.id.answer4);
        answer4.setText(answers[3]);
        answer4.setClickable(true);
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
                if (currentQuestion.isCorrect(0)) {
                    correctAnswer(answer1);
                } else {
                    wrongAnswer(answer1);
                } break;

            case R.id.answer2:
                if (currentQuestion.isCorrect(1)) {
                    correctAnswer(answer2);
                } else {
                    wrongAnswer(answer2);
                } break;

            case R.id.answer3:
                if (currentQuestion.isCorrect(2)) {
                    correctAnswer(answer3);
                } else {
                    wrongAnswer(answer3);
                } break;

            case R.id.answer4:
                if (currentQuestion.isCorrect(3)) {
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
        centerLine.setText("Game Over");
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
