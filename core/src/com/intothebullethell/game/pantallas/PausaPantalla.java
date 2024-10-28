package com.intothebullethell.game.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.intothebullethell.game.IntoTheBulletHell;
import com.intothebullethell.game.managers.RenderManager;
import com.intothebullethell.game.ui.Boton;
import com.intothebullethell.game.ui.Texto;

public class PausaPantalla implements Screen {

	private Stage stage;
    private SpriteBatch batch;
    private Boton botonReanudar;
    private Boton botonMenuPrincipal;
    private IntoTheBulletHell game;
    private JuegoPantalla jugarPantalla;

    public PausaPantalla(IntoTheBulletHell game, JuegoPantalla jugarPantalla) {
    	this.game = game;
    	this.jugarPantalla = jugarPantalla;
        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        botonReanudar = new Boton(new Texto("Reanudar", 24, Color.WHITE, 0, 200));
        botonReanudar.centrarX();
        botonMenuPrincipal = new Boton(new Texto("Menú Principal", 24, Color.WHITE, 0, 150));
        botonMenuPrincipal.centrarX();
        
        stage.addActor(botonReanudar);
        stage.addActor(botonMenuPrincipal);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        
        RenderManager.begin();
        botonReanudar.draw(RenderManager.batch);
        botonMenuPrincipal.draw(RenderManager.batch);
        RenderManager.end();
        
        stage.draw();
        if (botonReanudar.isClicked()) {
            game.setScreen(jugarPantalla);  
            jugarPantalla.resume();  
        }

        if (botonMenuPrincipal.isClicked()) {
        	game.setScreen(new MenuPantalla(game));
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
    	 stage.dispose();
         batch.dispose();
         botonReanudar.dispose();
         botonMenuPrincipal.dispose();
    }
}
