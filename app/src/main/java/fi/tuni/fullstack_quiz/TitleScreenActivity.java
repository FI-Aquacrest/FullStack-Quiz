package fi.tuni.fullstack_quiz;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;

public class TitleScreenActivity extends Activity {
    private SoundPool soundPool;
    int buttonSound;

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

    @Override
    protected void onDestroy() {
        soundPool.release();
        soundPool = null;
        super.onDestroy();
    }
}
