package com.intothebullethell.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.intothebullethell.game.screens.Play;

public class IntoTheBulletHell extends Game {
	private Texture customCursor;
	 
	@Override
	public void create () {
		setScreen(new Play());
		customCursor = new Texture(Gdx.files.internal("imagenes/crosshair.png"));
        Pixmap pixmap = new Pixmap(Gdx.files.internal("imagenes/crosshair.png"));
        Gdx.graphics.setCursor(Gdx.graphics.newCursor(pixmap, 0, 0));
        pixmap.dispose();
	}

	@Override
	public void render () {
		super.render();

	}
	
	@Override
	public void dispose () {
		super.dispose();
		customCursor.dispose();
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
}
