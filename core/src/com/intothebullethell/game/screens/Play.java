package com.intothebullethell.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.intothebullethell.game.IntoTheBulletHell;
import com.intothebullethell.game.entities.Player;

public class Play implements Screen {

    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;
    private IntoTheBulletHell game;
    private Player player;
    private Stage stage;
    private Skin skin;
    private boolean paused;

    public Play(IntoTheBulletHell game) {
        this.game = game;
        this.paused = false;
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
            
            renderer.getBatch().begin();
            player.draw(renderer.getBatch());
            renderer.getBatch().end();
        }
        
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width - 200;
        camera.viewportHeight = height - 200;
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void show() {
        if (map == null) {
            map = new TmxMapLoader().load("mapas/mapa.tmx");
            renderer = new OrthogonalTiledMapRenderer(map);
            camera = new OrthographicCamera();

            // Carga las texturas para las diferentes direcciones
            TextureRegion upSprite = new TextureRegion(new Texture("imagenes/upSprite.png"));
            TextureRegion downSprite = new TextureRegion(new Texture("imagenes/downSprite.png"));
            TextureRegion leftSprite = new TextureRegion(new Texture("imagenes/leftSprite.png"));
            TextureRegion rightSprite = new TextureRegion(new Texture("imagenes/rightSprite.png"));

            // Crea el jugador con las texturas para las diferentes direcciones
            player = new Player(new Sprite(downSprite), (TiledMapTileLayer) map.getLayers().get(0), new TextureRegion(upSprite), new TextureRegion(downSprite), new TextureRegion(leftSprite), new TextureRegion(rightSprite), camera);
            player.setPosition(15 * player.getCollisionLayer().getTileWidth(), 15 * player.getCollisionLayer().getHeight());
        }

        Gdx.input.setInputProcessor(player);

        // Configura el cursor personalizado
        Pixmap crosshairPixmap = new Pixmap(Gdx.files.internal("imagenes/crosshair.png"));
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

            // Crear el botón de pausa
            TextButton pauseButton = new TextButton("Pause", skin);

            // Manejar el evento del botón de pausa
            pauseButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    game.setScreen(new PauseMenuScreen(game, Play.this));
                    paused = true;
                }
            });

        }
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
        // No dispose resources to keep the game state
    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        player.getTexture().dispose();
        stage.dispose();
        skin.dispose();
        Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow); // Restablece el cursor al sistema predeterminado
    }

    public void resumeGame() {
        paused = false;
        Gdx.input.setInputProcessor(player);
    }
}

