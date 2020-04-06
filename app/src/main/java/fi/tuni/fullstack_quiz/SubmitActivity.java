package fi.tuni.fullstack_quiz;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.ExecutionException;

import fi.tuni.fullstack_quiz.db.GetRepoAsyncTask;
import fi.tuni.fullstack_quiz.db.Question;
import fi.tuni.fullstack_quiz.db.QuestionRepository;

/**
 * This activity is used to add new questions to the database.
 */
public class SubmitActivity extends AppCompatActivity {

    RadioButton correct1;
    RadioButton correct2;
    RadioButton correct3;
    RadioButton correct4;

    // Make sure a radio button is pressed before submitting.
    boolean correctChoiceSelected = false;

    /**
     * Sets the UI and connects the RadioButtons to each other.
     *
     * @param savedInstanceState Not used.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit);

        correct1 = findViewById(R.id.correct_choice1);
        correct2 = findViewById(R.id.correct_choice2);
        correct3 = findViewById(R.id.correct_choice3);
        correct4 = findViewById(R.id.correct_choice4);

        correct1.setOnClickListener((View v) -> {
            correctChoiceSelected = true;
            correct1.setChecked(true);
            correct2.setChecked(false);
            correct3.setChecked(false);
            correct4.setChecked(false);
        });

        correct2.setOnClickListener((View v) -> {
            correctChoiceSelected = true;
            correct1.setChecked(false);
            correct2.setChecked(true);
            correct3.setChecked(false);
            correct4.setChecked(false);
        });

        correct3.setOnClickListener((View v) -> {
            correctChoiceSelected = true;
            correct1.setChecked(false);
            correct2.setChecked(false);
            correct3.setChecked(true);
            correct4.setChecked(false);
        });

        correct4.setOnClickListener((View v) -> {
            correctChoiceSelected = true;
            correct1.setChecked(false);
            correct2.setChecked(false);
            correct3.setChecked(false);
            correct4.setChecked(true);
        });
    }

    /**
     * Validates and saves the question to the database.
     *
     * @param view Pressed "Save" button
     */
    public void saveQuestion(View view) {
        EditText questionView = findViewById(R.id.edit_question_box);
        EditText answer1 = findViewById(R.id.edit_answer1);
        EditText answer2 = findViewById(R.id.edit_answer2);
        EditText answer3 = findViewById(R.id.edit_answer3);
        EditText answer4 = findViewById(R.id.edit_answer4);

        // Check that all fields have text on them and that a radiobutton has been clicked.
        if (questionView.getText().toString().equals("") || answer1.getText().toString().equals("") ||
        answer2.getText().toString().equals("") || answer3.getText().toString().equals("") ||
        answer4.getText().toString().equals("") || !correctChoiceSelected) {

            TextView centerText = findViewById(R.id.submit_center_line);
            centerText.setText("Please fill all of the fields and select a correct answer.");
            centerText.setTextColor(getResources().getColor(R.color.red));
        } else {
            GetRepoAsyncTask asyncTask = new GetRepoAsyncTask();
            QuestionRepository repo = null;

            try {
                repo = asyncTask.execute(getApplication()).get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            int correctIndex;
            if (correct1.isChecked()) {
                correctIndex = 0;
            } else if (correct2.isChecked()) {
                correctIndex = 1;
            } else if (correct3.isChecked()) {
                correctIndex = 2;
            } else {
                correctIndex = 3;
            }

            Question question = new Question(questionView.getText().toString(),
                    answer1.getText().toString(), answer2.getText().toString(),
                    answer3.getText().toString(), answer4.getText().toString(),
                    correctIndex);

            repo.insert(question);

            Toast.makeText(this, "Question saved successfully", Toast.LENGTH_LONG).show();
            finish();
        }
    }
}
