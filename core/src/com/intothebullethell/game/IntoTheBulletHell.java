package com.intothebullethell.game;

import com.badlogic.gdx.Game;
import com.intothebullethell.game.screens.MenuPrincipalPantalla;
import com.intothebullethell.sound.Musica;

public class IntoTheBulletHell extends Game {
	
	private Musica musica;
	@Override
	public void create () {
		musica = new Musica();
		setScreen(new MenuPrincipalPantalla(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		super.dispose();
	}
	
	public void resize(int width, int height) {
		super.resize(width, height);
	}
	
	public void pause() {
		super.pause();
	}
	
	public void resume() {
		super.resume();
	}
	public Musica getMusica() {
        return musica;
    }
}
