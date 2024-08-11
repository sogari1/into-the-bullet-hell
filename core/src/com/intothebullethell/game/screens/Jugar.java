package com.intothebullethell.game.screens;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.intothebullethell.game.IntoTheBulletHell;
import com.intothebullethell.game.entities.Enemigo;
import com.intothebullethell.game.entities.Jugador;
import com.intothebullethell.game.entities.Proyectil;
import com.intothebullethell.sound.Musica;

public class Jugar implements Screen {

    private TiledMap map;
    private OrthogonalTiledMapRenderer renderizar;
    private OrthographicCamera camara;
    private IntoTheBulletHell game;
    private Jugador jugador;
    private Stage stage;
    private Skin skin;
    private boolean pausado;
    private HUD hud;
    private SpriteBatch batch;
    private ArrayList<Enemigo> enemigos;
    private Set<Vector2> occupiedPositions = new HashSet<>();
    public Music gameMusic;


    public Jugar(IntoTheBulletHell game) {
        this.game = game;
        this.pausado = false;
        this.batch = new SpriteBatch();
        this.enemigos = new ArrayList<>();
        
        Musica musica = game.getMusica();
        gameMusic = musica.getGameMusic();
        gameMusic.setLooping(true);
        gameMusic.play();
    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.setScreen(new MenuPausaPantalla(game, this));
            pausado = true;
            gameMusic.pause(); 
        }
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (!pausado) {
            camara.position.set(jugador.getX() + jugador.getWidth() / 2, jugador.getY() + jugador.getHeight() / 2, 0);
            camara.update();

            renderizar.setView(camara);
            renderizar.render();
            
            // Usa renderer.getBatch() para todo el dibujo
            SpriteBatch batch = (SpriteBatch) renderizar.getBatch();
            
            jugador.update(delta);

            for (Enemigo enemy : enemigos) {
                enemy.update(delta);
            }
            
            batch.begin();
            jugador.draw(batch);
            for (Enemigo enemigo : enemigos) {
            	enemigo.draw(batch);
                for (Proyectil proyectil : enemigo.getProjectiles()) {
                    proyectil.draw(batch);
                }
            }
            batch.end();

        }

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
        
        hud.setJugadorVida(jugador.getVida());
        hud.render();
    }

    @Override
    public void resize(int width, int height) {
        camara.viewportWidth = width - 400;
        camara.viewportHeight = height - 400;
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void show() {
        if (map == null) {
            map = new TmxMapLoader().load("mapas/mapa.tmx");
            renderizar = new OrthogonalTiledMapRenderer(map);
            camara = new OrthographicCamera();

            TiledMapTileLayer collisionLayer = (TiledMapTileLayer) map.getLayers().get(0);

            // Carga las texturas para las diferentes direcciones
            TextureRegion upSprite = new TextureRegion(new Texture("imagenes/personaje/kurumiUp.png"));
            TextureRegion downSprite = new TextureRegion(new Texture("imagenes/personaje/kurumiDown.png"));
            TextureRegion leftSprite = new TextureRegion(new Texture("imagenes/personaje/kurumiLeft.png"));
            TextureRegion rightSprite = new TextureRegion(new Texture("imagenes/personaje/kurumiRight.png"));

            // Inicializa `Jugador` aquí
            jugador = new Jugador(downSprite, (TiledMapTileLayer) map.getLayers().get(0), upSprite, downSprite, leftSprite, rightSprite, camara);
            jugador.setPosition(15 * collisionLayer.getTileWidth(), 15 * collisionLayer.getHeight());
            jugador.setCollisionLayer(collisionLayer); 
            
            hud = new HUD(batch, jugador);
            jugador.setHUD(hud);

            // Inicializa y coloca los enemigos
            initializeEnemies(20);

            jugador.setEnemies(enemigos);
        }

        Gdx.input.setInputProcessor(jugador);

        // Configura el cursor personalizado
        Pixmap crosshairPixmap = new Pixmap(Gdx.files.internal("imagenes/otros/cursor/crosshair.png"));
        Cursor cursor = Gdx.graphics.newCursor(crosshairPixmap, crosshairPixmap.getWidth() / 2, crosshairPixmap.getHeight() / 2);
        Gdx.graphics.setCursor(cursor);

        // Libera el Pixmap después de usarlo
        crosshairPixmap.dispose();

        // Crear la escena para la UI
        if (stage == null) {
            stage = new Stage(new ScreenViewport());

            // Crear el Skin programáticamente
            skin = new Skin();
            // Crear la fuente
            BitmapFont font = new BitmapFont();
            skin.add("default-font", font);

            // Crear el estilo para los botones
            TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
            textButtonStyle.font = skin.getFont("default-font");
            skin.add("default", textButtonStyle);
        }
    }

    @Override
    public void dispose() {
        map.dispose();
        renderizar.dispose();
        stage.dispose();
        skin.dispose();
        hud.dispose(); 
        Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow); // Restablece el cursor al sistema predeterminado
    }

    public void resumeGame() {
        pausado = false;
        Gdx.input.setInputProcessor(jugador);
        // Reanudar la música
        gameMusic.play();
    }


    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}
    
    private void initializeEnemies(int cont) {
        enemigos = new ArrayList<>();
        occupiedPositions.clear(); // Limpia las posiciones ocupadas

        for (int i = 0; i < cont; i++) {
            Vector2 spawnPosition = getUniqueSpawnPosition();
            Enemigo enemy = new Enemigo(jugador, enemigos);
            enemy.setPosition(spawnPosition.x, spawnPosition.y);
            enemigos.add(enemy);
        }
    }

    private Vector2 getUniqueSpawnPosition() {
        int mapWidth = 50; // Cambia esto según el tamaño de tu mapa
        int mapHeight = 50; // Cambia esto según el tamaño de tu mapa
        int tileWidth = 32; // Tamaño del tile
        int tileHeight = 32; // Tamaño del tile

        Vector2 position;
        do {
            position = new Vector2(
                (float) (Math.random() * (mapWidth - 1) * tileWidth),
                (float) (Math.random() * (mapHeight - 1) * tileHeight)
            );
        } while (occupiedPositions.contains(position) || !isPositionValid(position));

        occupiedPositions.add(position);
        return position;
    }

    private boolean isPositionValid(Vector2 position) {
        // Ajusta esta lógica según las posiciones válidas de tu mapa
        int tileSize = 32; // Tamaño del tile
        int x = (int) position.x / tileSize;
        int y = (int) position.y / tileSize;

        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(0);
        return x >= 0 && x < layer.getWidth() && y >= 0 && y < layer.getHeight() && layer.getCell(x, y) != null;
    }
}


