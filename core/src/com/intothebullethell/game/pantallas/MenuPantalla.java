package com.intothebullethell.game.pantallas;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.intothebullethell.game.IntoTheBulletHell;
import com.intothebullethell.game.managers.RenderManager;
import com.intothebullethell.game.ui.Boton;
import com.intothebullethell.game.ui.Texto;

public class MenuPantalla implements Screen {

    private Stage stage;
    private Music menuMusic;
    private Texto tituloJuego;
    private Boton playSingleplayerButton, playMultiplayerButton,exitButton;
    private IntoTheBulletHell game;

    public MenuPantalla(IntoTheBulletHell game) {
    	this.game = game;
        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        menuMusic = Gdx.audio.newMusic(Gdx.files.internal("sonidos/musica/DarkSouls.mp3"));
        menuMusic.setLooping(true);
        menuMusic.play();

        tituloJuego = new Texto("Into The Bullet Hell", 48, Color.WHITE, 0, Gdx.graphics.getHeight() - 400);
        tituloJuego.setShadow(6, 6, Color.GRAY);
        tituloJuego.centerX();

        playSingleplayerButton = new Boton(new Texto("Singleplayer", 24, Color.WHITE, 0, 200));
        playSingleplayerButton.centrarX();
        
        playMultiplayerButton = new Boton(new Texto("Multiplayer", 24, Color.WHITE, 0, 150));
        playMultiplayerButton.centrarX();
        
        exitButton = new Boton(new Texto("Salir", 24, Color.WHITE, 0, 100));
        exitButton.centrarX();
        
        stage.addActor(playSingleplayerButton);
        stage.addActor(playMultiplayerButton);
        stage.addActor(exitButton);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
    	 Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
         stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
         
         RenderManager.begin();
         tituloJuego.draw(RenderManager.batch); 
         playSingleplayerButton.draw(RenderManager.batch);  
         playMultiplayerButton.draw(RenderManager.batch);  
         exitButton.draw(RenderManager.batch);  
         RenderManager.end();
         
         stage.draw();
         
         if (playSingleplayerButton.isClicked()) {
             ((Game) Gdx.app.getApplicationListener()).setScreen(new JuegoPantalla(this.game));
             menuMusic.stop();
         }
         if (exitButton.isClicked()) {
             Gdx.app.exit(); 
         }
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        stage.dispose();
        menuMusic.dispose();  
        tituloJuego.dispose(); 
        playSingleplayerButton.dispose();  
        playMultiplayerButton.dispose();
        exitButton.dispose();  
    }
}
