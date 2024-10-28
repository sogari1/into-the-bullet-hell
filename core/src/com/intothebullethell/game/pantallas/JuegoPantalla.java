package com.intothebullethell.game.pantallas;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.intothebullethell.game.IntoTheBulletHell;
import com.intothebullethell.game.entidades.Enemigo;
import com.intothebullethell.game.entidades.Jugador;
import com.intothebullethell.game.globales.AssetRuta;
import com.intothebullethell.game.globales.RecursoRuta;
import com.intothebullethell.game.inputs.InputManager;
import com.intothebullethell.game.managers.ProyectilManager;
import com.intothebullethell.game.managers.RenderManager;
import com.intothebullethell.game.managers.TileColisionManager;
import com.intothebullethell.game.mecanicas.GenerarEnemigos;
import com.intothebullethell.game.mecanicas.Tiempo;
import com.intothebullethell.game.ui.Hud;
import com.intothebullethell.sonido.Musica;

public class JuegoPantalla implements Screen {

    private OrthographicCamera camara;
    private IntoTheBulletHell game;
    private Jugador jugador;
    private Stage stage;
    private Hud hud;
    private Tiempo tiempo;
    private GenerarEnemigos generadorEnemigos;
    private PausaPantalla pausaPantalla;
    private GameOverPantalla gameOverPantalla;
    private ProyectilManager proyectilManager;
    private ArrayList<Enemigo> enemigos;
    private InputManager inputManager;
    private TiledMapTileLayer collisionLayer;
    private TileColisionManager collisionManager;
    
    private boolean pausado;
    private int ronda;
    
    public JuegoPantalla(IntoTheBulletHell game) {
        this.game = game;
        this.pausado = false;
        this.enemigos = new ArrayList<>();
        this.ronda = 1;
        this.pausaPantalla = new PausaPantalla(game, this);
        this.gameOverPantalla = new GameOverPantalla(game);
    	this.camara = new OrthographicCamera();
    	this.inputManager = new InputManager();
    	Gdx.input.setInputProcessor(inputManager);
           
    	this.collisionLayer = (TiledMapTileLayer) RenderManager.mapa.getLayers().get(0);
        this.collisionManager = new TileColisionManager(collisionLayer);
            
        this.proyectilManager = new ProyectilManager(collisionManager);
        this.jugador = new Jugador(RecursoRuta.SPRITE_ABAJO, RecursoRuta.SPRITE_ARRIBA,  RecursoRuta.SPRITE_ABAJO, RecursoRuta.SPRITE_IZQUIERDA,  RecursoRuta.SPRITE_DERECHA, camara, inputManager, collisionLayer, collisionManager);
        this.jugador.setPosition(15 * collisionLayer.getTileWidth(), 15 * collisionLayer.getTileHeight());
        this.jugador.setProyectilManager(proyectilManager); 
        this.hud = new Hud(RenderManager.batch, jugador);
        this.jugador.setHud(hud);

        this.generadorEnemigos = new GenerarEnemigos(camara, RenderManager.mapa, enemigos, jugador, collisionLayer, collisionManager);
        this.generadorEnemigos.generarEnemigos();
        this.jugador.setEnemies(enemigos);
        this.tiempo = new Tiempo(jugador);
        tiempo.start();
        
        setCustomCursor(AssetRuta.CURSOR);
        
        Musica musica = game.getMusica();
        Musica.gameMusic = musica.getGameMusic();
        Musica.gameMusic.setLooping(true);
        Musica.gameMusic.play();
        
        this.stage = new Stage(new ScreenViewport());
    }

    @Override
    public void render(float delta) {
    	Gdx.gl.glClearColor(0, 0, 0, 1);
    	Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (inputManager.isPausaSolicitada()) {
        	pause();
        }
        if (!pausado) {
        	RenderManager.renderizar(camara);
        	actualizarObjetos(delta);          
            dibujarObjetos(RenderManager.batchRender);
        }

    }
    
    @Override
    public void show() {}
    private void actualizarObjetos(float delta) {
    	 jugador.update(delta);

         if (jugador.chequearMuerte()) {
             gameOver(); 
         }
         for (Enemigo enemigo : enemigos) {
         	enemigo.setProyectilManager(proyectilManager);
         	enemigo.update(delta);
         }

         if (enemigos.isEmpty()) {
             ronda++;
             hud.actualizarRonda(ronda);
             generadorEnemigos.generarEnemigos();
         }
//         System.out.println("Enemigos restantes: " + enemigos.size());
         hud.actualizarEnemigosRestantes(enemigos.size());
         hud.actualizarTemporizador(Tiempo.getTiempo());
         hud.render();
         proyectilManager.actualizarProyectiles(delta, enemigos, jugador);
    }
    private void dibujarObjetos(SpriteBatch batch) {
    	RenderManager.batchRender.begin();
    	jugador.draw(batch);
    	for (Enemigo enemigo : enemigos) {
    		enemigo.draw(batch);
    		}
    	proyectilManager.draw(batch);
        RenderManager.batchRender.end();
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
   }
    @Override
    public void resize(int width, int height) {
        camara.viewportWidth = width - 320;
        camara.viewportHeight = height - 300;
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
    	RenderManager.mapa.dispose();
    	RenderManager.render.dispose();
        stage.dispose();
        hud.dispose(); 
        Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow); 
    }

    @Override
    public void pause() {
        pausado = true;
        inputManager.setPausaSolicitada(false);
        tiempo.pausar();  
        Musica.playPauseMusic();
        game.setScreen(pausaPantalla);
    }

    public void resume() {
        pausado = false;
        tiempo.reanudar(); 
        Musica.stopAllMusic();
        Musica.gameMusic.play();
    }
    public void gameOver() {
    	Musica.gameMusic.pause();
    	game.setScreen(gameOverPantalla);
    }
    @Override
    public void hide() {}
    private void setCustomCursor(String cursorPath) {
        Pixmap pixmap = new Pixmap(Gdx.files.internal(cursorPath));
        Cursor cursor = Gdx.graphics.newCursor(pixmap, pixmap.getWidth() / 2, pixmap.getHeight() / 2);
        Gdx.graphics.setCursor(cursor);
        pixmap.dispose(); 
    }
}
