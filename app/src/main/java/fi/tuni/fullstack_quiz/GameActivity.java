package fi.tuni.fullstack_quiz;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameActivity extends Activity {
    Question[] questions;
    Question currentQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        questions = new Question[]{
                new Question("Which British boxer is nicknamed ‘King Khan’?",
                        new String[]{"Amir Khan", "John Doe", "Mary Jones", "Jacl Sparrow"}, 0),
                new Question("Complete the title of a famous hit for The Clovers “Love Potion No….”?",
                        new String[]{"15", "22", "1", "9"}, 3)
        };

        askQuestion();
    }

    public void askQuestion() {
        int random = (int) (Math.random() * questions.length);
        currentQuestion = questions[random];

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
        boolean correct = false;

        switch (v.getId()) {
            case R.id.answer1:
                if (currentQuestion.isCorrect(0)) {
                    correctAnswer();
                    correct = true;
                } break;

            case R.id.answer2:
                if (currentQuestion.isCorrect(1)) {
                    correctAnswer();
                    correct = true;
                } break;

            case R.id.answer3:
                if (currentQuestion.isCorrect(2)) {
                    correctAnswer();
                    correct = true;
                } break;

            case R.id.answer4:
                if (currentQuestion.isCorrect(3)) {
                    correctAnswer();
                    correct = true;
                } break;
        }

        if (!correct) {
            wrongAnswer();
        }
    }

    public void correctAnswer() {
        Log.d("QuestionAnswered", "Correct answer");
    }

    public void wrongAnswer() {
        Log.d("QuestionAnswered", "Wrong answer");
    }
}
