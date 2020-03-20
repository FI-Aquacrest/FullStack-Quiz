package fi.tuni.fullstack_quiz;

import android.content.Context;
import android.media.MediaPlayer;

public class MusicPlayer {
    private static MediaPlayer mediaPlayer;
    private static int pausedAt;

    public static void InitMediaPlayer(Context c, int source) {
        mediaPlayer = MediaPlayer.create(c, source);
    }

    public static void PauseMusic() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            pausedAt = mediaPlayer.getCurrentPosition();
            mediaPlayer.pause();
        }
    }

    public static void PlayMusic() {
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
        }
    }

    public static void ResumeMusic() {
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.seekTo(pausedAt);
            mediaPlayer.start();
        }
    }

    public static void StopMusic() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
    }

    public static void ReleaseMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
    }
}