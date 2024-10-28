package com.intothebullethell.sonido;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class Musica {
	public static Music mainMenuMusic;
	public static Music gameMusic;
	public static Music pauseMusic;

    public Musica() {
        mainMenuMusic = Gdx.audio.newMusic(Gdx.files.internal("sonidos/musica/DarkSouls.mp3"));
        gameMusic = Gdx.audio.newMusic(Gdx.files.internal("sonidos/musica/Adrenaline.mp3"));
        pauseMusic = Gdx.audio.newMusic(Gdx.files.internal("sonidos/musica/DarkSouls.mp3"));
    }

    public static void playMainMenuMusic() {
        stopAllMusic();
        mainMenuMusic.setLooping(true);
        mainMenuMusic.play();
    }

    public static void playGameMusic() {
        stopAllMusic();
        gameMusic.setLooping(true);
        gameMusic.play();
    }

    public static void playPauseMusic() {
        stopAllMusic();
        pauseMusic.setLooping(true);
        pauseMusic.play();
    }

    public static void stopAllMusic() {
        if (mainMenuMusic.isPlaying()) mainMenuMusic.stop();
        if (gameMusic.isPlaying()) gameMusic.stop();
        if (pauseMusic.isPlaying()) pauseMusic.stop();
    }

    public void dispose() {
        mainMenuMusic.dispose();
        gameMusic.dispose();
        pauseMusic.dispose();
    }
    public Music getMainMenuMusic() {
        return mainMenuMusic;
    }

    public Music getGameMusic() {
        return gameMusic;
    }

    public Music getPauseMusic() {
        return pauseMusic;
    }
}
