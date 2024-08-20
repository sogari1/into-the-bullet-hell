package com.intothebullethell.sound;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class Musica {
    private Music mainMenuMusic;
    private Music gameMusic;
    private Music pauseMusic;

    public Musica() {
        mainMenuMusic = Gdx.audio.newMusic(Gdx.files.internal("sonidos/musica/DarkSouls.mp3"));
        gameMusic = Gdx.audio.newMusic(Gdx.files.internal("sonidos/musica/Adrenaline.mp3"));
        pauseMusic = Gdx.audio.newMusic(Gdx.files.internal("sonidos/musica/DarkSouls.mp3"));
    }

    public void playMainMenuMusic() {
        stopAllMusic();
        mainMenuMusic.setLooping(true);
        mainMenuMusic.play();
    }

    public void playGameMusic() {
        stopAllMusic();
        gameMusic.setLooping(true);
        gameMusic.play();
    }

    public void playPauseMusic() {
        stopAllMusic();
        pauseMusic.setLooping(true);
        pauseMusic.play();
    }

    public void stopAllMusic() {
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
