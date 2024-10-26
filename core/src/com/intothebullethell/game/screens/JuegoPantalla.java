package com.intothebullethell.game.screens;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.intothebullethell.game.IntoTheBulletHell;
import com.intothebullethell.game.entities.Enemigo;
import com.intothebullethell.game.entities.Jugador;
import com.intothebullethell.game.inputs.InputManager;
import com.intothebullethell.game.managers.AssetManagerJuego;
import com.intothebullethell.game.managers.ProyectilManager;
import com.intothebullethell.game.managers.RecursoManager;
import com.intothebullethell.game.managers.TileColisionManager;
import com.intothebullethell.game.mecanicas.GenerarEnemigos;
import com.intothebullethell.game.mecanicas.Tiempo;
import com.intothebullethell.game.ui.Hud;
import com.intothebullethell.sound.Musica;

public class JuegoPantalla implements Screen {

    private TiledMap map;
    private OrthogonalTiledMapRenderer renderizar;
    private OrthographicCamera camara;
    private IntoTheBulletHell game;
    private Jugador jugador;
    private Stage stage;
    private Skin skin;
    private boolean pausado;
    private Hud hud;
    private SpriteBatch batch;
    private ArrayList<Enemigo> enemigos;
    public Music gameMusic;
    private InputManager inputManager = new InputManager();
    private int ronda;
    private int tiempoNum;
    private Tiempo tiempo;
    private GenerarEnemigos generadorEnemigos;
    private PausaPantalla pausaPantalla;
    private ProyectilManager proyectilManager;

    public JuegoPantalla(IntoTheBulletHell game) {
        this.game = game;
        this.pausado = false;
        this.batch = new SpriteBatch();
        this.enemigos = new ArrayList<>();
        this.ronda = 1;
        this.pausaPantalla = new PausaPantalla(game, this);
        this.proyectilManager = new ProyectilManager();
        
        Musica musica = game.getMusica();
        gameMusic = musica.getGameMusic();
        gameMusic.setLooping(true);
        gameMusic.play();
        
        this.map = new TmxMapLoader().load(AssetManagerJuego.MAPA);
        this.renderizar = new OrthogonalTiledMapRenderer(map);
    	this.camara = new OrthographicCamera();
           
    	TiledMapTileLayer collisionLayer = (TiledMapTileLayer) map.getLayers().get(0);
        TileColisionManager collisionManager = new TileColisionManager(collisionLayer);
            
        TextureRegion upSprite = RecursoManager.SPRITE_ARRIBA;
        TextureRegion downSprite = RecursoManager.SPRITE_ABAJO;
        TextureRegion leftSprite = RecursoManager.SPRITE_IZQUIERDA;
        TextureRegion rightSprite = RecursoManager.SPRITE_DERECHA;

        this.jugador = new Jugador(downSprite, upSprite, downSprite, leftSprite, rightSprite, camara, inputManager, collisionLayer);
        this.jugador.setPosition(15 * collisionLayer.getTileWidth(), 15 * collisionLayer.getTileHeight());
        this.jugador.setProyectilManager(proyectilManager); 
        this.hud = new Hud(batch, jugador);
        this.jugador.setHud(hud);

        this.generadorEnemigos = new GenerarEnemigos(camara, map, enemigos, jugador, collisionLayer, collisionManager);
        this.generadorEnemigos.generarEnemigos(10);

        this.jugador.setEnemies(enemigos);
        this.tiempo = new Tiempo(jugador);
        tiempo.start();
        
        setCustomCursor(AssetManagerJuego.CURSOR);

        this.skin = createSkin();
        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(inputManager);
    }

    @Override
    public void render(float delta) {
        if (inputManager.isPausaSolicitada()) {
        	pause();
        }
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (!pausado) {

            renderizar.setView(camara);
            renderizar.render();
            
            SpriteBatch batch = (SpriteBatch) renderizar.getBatch();
            
            jugador.update(delta);

            for (Enemigo enemigo : enemigos) {
            	enemigo.setProyectilManager(proyectilManager);
            	enemigo.update(delta);
            }

            if (enemigos.isEmpty()) {
                ronda++;
                hud.actualizarRonda(ronda);
                generadorEnemigos.generarEnemigos(calcularNumeroDeEnemigos());
            }
            
            batch.begin();
            dibujarObjetos(batch);
            batch.end();
            
            proyectilManager.actualizarProyectiles(delta, enemigos, jugador);
            stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
            stage.draw();
            
            hud.render();
            tiempoNum = Tiempo.getTiempo();
            hud.actualizarTemporizador(tiempoNum);
        }

    }
    
    @Override
    public void show() {}

    @Override
    public void resize(int width, int height) {
        camara.viewportWidth = width - 320;
        camara.viewportHeight = height - 300;
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        map.dispose();
        renderizar.dispose();
        stage.dispose();
        skin.dispose();
        hud.dispose(); 
        Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow); 
    }

    @Override
    public void pause() {
        pausado = true;
        inputManager.setPausaSolicitada(false);
        tiempo.pausar();  
        gameMusic.pause();
        game.setScreen(pausaPantalla);
    }

    public void resume() {
        pausado = false;
        tiempo.reanudar(); 
        gameMusic.play();
    }
    @Override
    public void hide() {}
    
    private Skin createSkin() {
        Skin skin = new Skin();
        BitmapFont font = new BitmapFont();
        skin.add("default-font", font);

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = skin.getFont("default-font");
        skin.add("default", textButtonStyle);

        return skin;
    }
    private void setCustomCursor(String cursorPath) {
        Pixmap pixmap = new Pixmap(Gdx.files.internal(cursorPath));
        Cursor cursor = Gdx.graphics.newCursor(pixmap, pixmap.getWidth() / 2, pixmap.getHeight() / 2);
        Gdx.graphics.setCursor(cursor);
        pixmap.dispose(); 
    }
    private void dibujarObjetos(SpriteBatch batch) {
        jugador.draw(batch);
        for (Enemigo enemigo : enemigos) {
            enemigo.draw(batch);
        }
        proyectilManager.draw(batch);
    }
    private int calcularNumeroDeEnemigos() {
        return (int) (5 * Math.pow(1.2, ronda));
    }
}
