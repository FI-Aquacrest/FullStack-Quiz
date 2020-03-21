package fi.tuni.fullstack_quiz;

import android.content.Context;
import android.media.MediaPlayer;

class MusicPlayer {
    private static MediaPlayer mediaPlayer;
    private static int pausedAt;

    static void InitMediaPlayer(Context c, int source) {
        mediaPlayer = MediaPlayer.create(c, source);
        mediaPlayer.setVolume(0.5f, 0.5f);
    }

    static void PauseMusic() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            pausedAt = mediaPlayer.getCurrentPosition();
            mediaPlayer.pause();
        }
    }

    static void PlayMusic() {
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
        }
    }

    static void ResumeMusic() {
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.seekTo(pausedAt);
            mediaPlayer.start();
        }
    }

    static void StopMusic() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
    }

    static void ReleaseMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
    }
}