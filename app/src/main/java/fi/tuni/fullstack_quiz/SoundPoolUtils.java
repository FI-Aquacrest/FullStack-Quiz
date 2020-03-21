package fi.tuni.fullstack_quiz;

import android.annotation.TargetApi;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

class SoundPoolUtils {

    static SoundPool createSoundPool() {
        SoundPool sounds;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            sounds = createNewSoundPool();
        } else {
            sounds = createOldSoundPool();
        }

        return sounds;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private static SoundPool createNewSoundPool(){
        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        return new SoundPool.Builder()
                .setAudioAttributes(attributes)
                .build();
    }

    private static SoundPool createOldSoundPool(){
        return new SoundPool(5, AudioManager.STREAM_MUSIC,0);
    }
}
