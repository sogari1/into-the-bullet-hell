package com.intothebullethell.game.screens;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
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
import com.intothebullethell.game.entities.Enemy;
import com.intothebullethell.game.entities.Player;
import com.intothebullethell.game.entities.Projectile;

public class Play implements Screen {

    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;
    private IntoTheBulletHell game;
    private Player player;
    private Stage stage;
    private Skin skin;
    private boolean paused;
    private HUD hud;
    private SpriteBatch batch;
    private ArrayList<Enemy> enemies;
    private Set<Vector2> occupiedPositions = new HashSet<>();


    public Play(IntoTheBulletHell game) {
        this.game = game;
        this.paused = false;
        this.batch = new SpriteBatch();
        this.enemies = new ArrayList<>();
    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.setScreen(new PauseMenuScreen(game, this));
            paused = true;
        }

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (!paused) {
            camera.position.set(player.getX() + player.getWidth() / 2, player.getY() + player.getHeight() / 2, 0);
            camera.update();

            renderer.setView(camera);
            renderer.render();
            
            // Usa renderer.getBatch() para todo el dibujo
            SpriteBatch batch = (SpriteBatch) renderer.getBatch();
            
            player.update(delta);

            for (Enemy enemy : enemies) {
                enemy.update(delta);
            }
            
            batch.begin();
            player.draw(batch);
            for (Enemy enemy : enemies) {
                enemy.draw(batch);
                for (Projectile projectile : enemy.getProjectiles()) {
                    projectile.draw(batch);
                }
            }
            batch.end();

        }

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
        
        hud.setPlayerHealth(player.getHealth());
        hud.render();
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width - 400;
        camera.viewportHeight = height - 400;
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void show() {
        if (map == null) {
            map = new TmxMapLoader().load("mapas/mapa.tmx");
            renderer = new OrthogonalTiledMapRenderer(map);
            camera = new OrthographicCamera();

            TiledMapTileLayer collisionLayer = (TiledMapTileLayer) map.getLayers().get(0);

            // Carga las texturas para las diferentes direcciones
            TextureRegion upSprite = new TextureRegion(new Texture("imagenes/personaje/kurumiUp.png"));
            TextureRegion downSprite = new TextureRegion(new Texture("imagenes/personaje/kurumiDown.png"));
            TextureRegion leftSprite = new TextureRegion(new Texture("imagenes/personaje/kurumiLeft.png"));
            TextureRegion rightSprite = new TextureRegion(new Texture("imagenes/personaje/kurumiRight.png"));

            // Inicializa `Player` aquí
            player = new Player(downSprite, (TiledMapTileLayer) map.getLayers().get(0), upSprite, downSprite, leftSprite, rightSprite, camera);
            player.setPosition(15 * collisionLayer.getTileWidth(), 15 * collisionLayer.getHeight());
            player.setCollisionLayer(collisionLayer); 
            
            hud = new HUD(batch, player);
            player.setHUD(hud);

            // Inicializa y coloca los enemigos
            initializeEnemies(5);

            player.setEnemies(enemies);
        }

        Gdx.input.setInputProcessor(player);

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
        renderer.dispose();
        stage.dispose();
        skin.dispose();
        hud.dispose(); 
        Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow); // Restablece el cursor al sistema predeterminado
    }

    public void resumeGame() {
        paused = false;
        Gdx.input.setInputProcessor(player);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}
    
    private void initializeEnemies(int count) {
        enemies = new ArrayList<>();
        occupiedPositions.clear(); // Limpia las posiciones ocupadas

        for (int i = 0; i < count; i++) {
            Vector2 spawnPosition = getUniqueSpawnPosition();
            Enemy enemy = new Enemy(player, enemies);
            enemy.setPosition(spawnPosition.x, spawnPosition.y);
            enemies.add(enemy);
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


