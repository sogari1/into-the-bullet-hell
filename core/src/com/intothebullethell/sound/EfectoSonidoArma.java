package com.intothebullethell.sound;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class EfectoSonidoArma {
    private Sound sonidoDisparo;

    public EfectoSonidoArma(String rutaSonidoDisparo) {
        this.sonidoDisparo = Gdx.audio.newSound(Gdx.files.internal(rutaSonidoDisparo));
    }

    public void reproducirDisparo() {
        sonidoDisparo.play();
    }

    public void dispose() {
        sonidoDisparo.dispose();
    }
}
