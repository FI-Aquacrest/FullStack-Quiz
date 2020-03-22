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
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

public class GameActivity extends Activity {
    LinkedList<Question> questions = new LinkedList<>();
    Question currentQuestion;
    ArrayList<Question> askedQuestions = new ArrayList<>();
    boolean gameEnd = false;

    private SoundPool soundPool;
    private int[] sounds = new int[2];

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

    public void generateQuestions() {
        questions.add(new Question("Which British boxer is nicknamed ‘King Khan’?",
                        new String[]{"Amir Khan", "John Adams", "Joseph McCarthy", "Ben Summers"}, 0));
        questions.add(new Question("Complete the title of a famous hit for The Clovers “Love Potion No….”?",
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

    public void askQuestion() {
        if (questions.isEmpty()) {
            endGame();
        } else {
            currentQuestion = questions.getFirst();
            questions.remove(currentQuestion);
        }

        askedQuestions.add(currentQuestion);

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

    public void answerQuestion(View v) {
        Button answer1 = findViewById(R.id.answer1);
        answer1.setClickable(false);

        Button answer2 = findViewById(R.id.answer2);
        answer2.setClickable(false);

        Button answer3 = findViewById(R.id.answer3);
        answer3.setClickable(false);

        Button answer4 = findViewById(R.id.answer4);
        answer4.setClickable(false);

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

    public void correctAnswer(View button) {
        soundPool.play(sounds[0],1, 1, 0, 0, 1f);
        button.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.MULTIPLY);

        TextView textView = findViewById(R.id.center_line);
        textView.setText("Correct!");
        textView.setTextColor(getResources().getColor(R.color.green));

        clearAnswer(button, textView);
    }

    public void wrongAnswer(View button) {
        soundPool.play(sounds[1],1, 1, 0, 0, 1f);
        button.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);

        TextView textView = findViewById(R.id.center_line);
        textView.setText("Wrong!");
        textView.setTextColor(getResources().getColor(R.color.red));

        clearAnswer(button, textView);
    }

    public void clearAnswer(View button, TextView textView) {
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            button.getBackground().clearColorFilter();
            textView.setText("");
            askQuestion();
        }, 1000);
    }

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

        Handler handler = new Handler();
        handler.postDelayed(this::finish, 2500);
    }

    @Override
    protected void onPause() {
        if (!gameEnd) {
            MusicPlayer.PauseMusic();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        MusicPlayer.ResumeMusic();
        super.onResume();
    }

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
