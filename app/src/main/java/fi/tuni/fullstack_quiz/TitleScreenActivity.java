package fi.tuni.fullstack_quiz;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import androidx.constraintlayout.widget.ConstraintLayout;

public class TitleScreenActivity extends Activity {
    private SoundPool soundPool;
    int buttonSound;

    PopupWindow aboutWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title);

        soundPool = SoundPoolUtils.createSoundPool();
        buttonSound = soundPool.load(this, R.raw.button, 1);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
    }

    public void startGame(View v) {
        soundPool.play(buttonSound,1, 1, 0, 0, 1f);

        startActivity(new Intent(this, GameActivity.class));
    }

    public void submitQuestion(View v) {
        soundPool.play(buttonSound,1, 1, 0, 0, 1f);
    }

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

    public void closePopup(View v) {
        aboutWindow.dismiss();
    }

    @Override
    protected void onDestroy() {
        soundPool.release();
        soundPool = null;
        super.onDestroy();
    }
}
