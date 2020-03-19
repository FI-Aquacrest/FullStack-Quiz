package fi.tuni.fullstack_quiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class TitleScreenActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title);
    }

    public void startGame(View v) {
        startActivity(new Intent(this, GameActivity.class));
    }

    public void submitQuestion(View v) {

    }
}
