package com.intothebullethell.game;

import com.badlogic.gdx.Game;
import com.intothebullethell.game.screens.Play;

public class IntoTheBulletHell extends Game {
	 
	@Override
	public void create () {
		setScreen(new Play());
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
}
