package fi.tuni.fullstack_quiz;

import android.content.Context;
import android.media.MediaPlayer;

/**
 * Plays music during the game.
 */
class MusicPlayer {
    private static MediaPlayer mediaPlayer;
    private static int pausedAt;

    /**
     * Initializes the MediaPlayer with the given music file.
     *
     * @param c The context in which the music is played.
     * @param source The source ID of the music file to be played.
     */
    static void InitMediaPlayer(Context c, int source) {
        mediaPlayer = MediaPlayer.create(c, source);
        mediaPlayer.setVolume(0.5f, 0.5f);
    }

    /**
     * Pauses the music and saves the current position.
     */
    static void PauseMusic() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            pausedAt = mediaPlayer.getCurrentPosition();
            mediaPlayer.pause();
        }
    }

    /**
     * Starts playing the music on a loop.
     */
    static void PlayMusic() {
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
        }
    }

    /**
     * Resumes the music from the saved position.
     */
    static void ResumeMusic() {
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.seekTo(pausedAt);
            mediaPlayer.start();
        }
    }

    /**
     * Stops the music.
     */
    static void StopMusic() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
    }

    /**
     * Releases the MediaPlayer.
     */
    static void ReleaseMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
    }
}