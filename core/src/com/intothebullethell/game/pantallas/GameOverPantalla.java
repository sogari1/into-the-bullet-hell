package com.intothebullethell.game.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.intothebullethell.game.IntoTheBulletHell;
import com.intothebullethell.game.managers.RenderManager;
import com.intothebullethell.game.ui.Boton;
import com.intothebullethell.game.ui.Texto;

public class GameOverPantalla implements Screen {
	
	private Stage stage;
    private IntoTheBulletHell game;
    private Texto textoPerdiste;
    private Boton botonMenu;
    
    public GameOverPantalla(IntoTheBulletHell game) {
        this.game = game;
        this.stage = new Stage(new ScreenViewport());
        
        textoPerdiste = new Texto("¡Perdiste!", 48, Color.RED, 0, Gdx.graphics.getHeight());
        textoPerdiste.centerXY();

        botonMenu = new Boton(new Texto("Volver al Menú", 24, Color.WHITE, 0, 200));
        botonMenu.centrarX();
    }
    
    @Override
    public void show() {
    }
    
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        RenderManager.begin();
        textoPerdiste.draw(RenderManager.batch);
        botonMenu.draw(RenderManager.batch);
        RenderManager.end();
        
        if (botonMenu.isClicked()) {
            game.setScreen(new MenuPantalla(game)); // Cambia a MenuPantalla
            dispose();
        }
    }
    
    @Override
    public void resize(int width, int height) {
    	 stage.getViewport().update(width, height, true);
    }
    
    @Override
    public void pause() {
    }
    
    @Override
    public void resume() {
    }
    
    @Override
    public void hide() {
    }
    
    @Override
    public void dispose() {
        textoPerdiste.dispose();
        botonMenu.dispose();
    }
}
