package fi.tuni.fullstack_quiz;

import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

/**
 * Provides user with a menu screen on startup.
 */
public class TitleScreenActivity extends AppCompatActivity {

    // Used for controlling sound effects
    private SoundPool soundPool;
    int buttonSound;

    // Holds copyright information
    PopupWindow aboutWindow;

    /**
     * Initiates the UI for the main menu and loads button sounds.
     *
     * @param savedInstanceState Not used.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title);

        soundPool = SoundPoolUtils.createSoundPool();
        buttonSound = soundPool.load(this, R.raw.button, 1);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
    }

    /**
     * Launches the game.
     *
     * @param v Pressed Button object.
     */
    public void startGame(View v) {
        soundPool.play(buttonSound,1, 1, 0, 0, 1f);

        startActivity(new Intent(this, GameActivity.class));
    }

    public void submitQuestion(View v) {
        soundPool.play(buttonSound,1, 1, 0, 0, 1f);

        startActivity(new Intent(this, SubmitActivity.class));
    }

    /**
     * Shows the About-popup, containing required information about the app.
     *
     * @param v Pressed Button object.
     */
    public void aboutApp(View v) {
        soundPool.play(buttonSound,1, 1, 0, 0, 1f);

        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

        assert inflater != null;
        View popupView = inflater.inflate(R.layout.about_popup, null);

        int width = ConstraintLayout.LayoutParams.MATCH_PARENT;
        int height = ConstraintLayout.LayoutParams.MATCH_PARENT;
        boolean focusable = true;
        aboutWindow = new PopupWindow(popupView, width, height, focusable);

        aboutWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
    }

    /**
     * Closes the About-popup.
     *
     * @param v Pressed Button object.
     */
    public void closePopup(View v) {
        aboutWindow.dismiss();
    }

    /**
     * Releases the SoundPool before closing the activity.
     */
    @Override
    protected void onDestroy() {
        soundPool.release();
        soundPool = null;
        super.onDestroy();
    }
}
