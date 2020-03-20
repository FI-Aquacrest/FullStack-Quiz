package fi.tuni.fullstack_quiz;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;

public class GameActivity extends Activity {
    Question[] questions;
    Question currentQuestion;
    ArrayList<Question> askedQuestions = new ArrayList<>();
    int answeredQuestions = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        generateQuestions();
    }

    public void generateQuestions() {
        questions = new Question[]{
                new Question("Which British boxer is nicknamed ‘King Khan’?",
                        new String[]{"Amir Khan", "John Doe", "Mary Jones", "Jacl Sparrow"}, 0),
                new Question("Complete the title of a famous hit for The Clovers “Love Potion No….”?",
                        new String[]{"15", "22", "1", "9"}, 3)
        };

        askQuestion();
    }

    public void askQuestion() {
        do {
            int random = (int) (Math.random() * questions.length);
            currentQuestion = questions[random];
        } while (askedQuestions.contains(currentQuestion) && answeredQuestions < questions.length);

        if (answeredQuestions == questions.length) {
            finish();
        }

        askedQuestions.add(currentQuestion);

        TextView questionView = findViewById(R.id.question_box);
        questionView.setText(currentQuestion.getQuestion());

        String[] answers = currentQuestion.getAnswers();

        Button answer1 = findViewById(R.id.answer1);
        answer1.setText(answers[0]);

        Button answer2 = findViewById(R.id.answer2);
        answer2.setText(answers[1]);

        Button answer3 = findViewById(R.id.answer3);
        answer3.setText(answers[2]);

        Button answer4 = findViewById(R.id.answer4);
        answer4.setText(answers[3]);
    }

    public void answerQuestion(View v) {
        answeredQuestions++;

        switch (v.getId()) {
            case R.id.answer1:
                if (currentQuestion.isCorrect(0)) {
                    correctAnswer(findViewById(R.id.answer1));
                } else {
                    wrongAnswer(findViewById(R.id.answer1));
                } break;

            case R.id.answer2:
                if (currentQuestion.isCorrect(1)) {
                    correctAnswer(findViewById(R.id.answer2));
                } else {
                    wrongAnswer(findViewById(R.id.answer2));
                } break;

            case R.id.answer3:
                if (currentQuestion.isCorrect(2)) {
                    correctAnswer(findViewById(R.id.answer3));
                } else {
                    wrongAnswer(findViewById(R.id.answer3));
                } break;

            case R.id.answer4:
                if (currentQuestion.isCorrect(3)) {
                    correctAnswer(findViewById(R.id.answer4));
                } else {
                    wrongAnswer(findViewById(R.id.answer4));
                } break;
        }
    }

    public void correctAnswer(View button) {
        button.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.MULTIPLY);

        TextView textView = findViewById(R.id.center_line);
        textView.setText("Correct!");
        textView.setTextColor(getResources().getColor(R.color.green));

        clearAnswer(button, textView);
    }

    public void wrongAnswer(View button) {
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
}
