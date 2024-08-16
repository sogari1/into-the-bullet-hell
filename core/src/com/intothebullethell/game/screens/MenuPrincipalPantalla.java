package com.intothebullethell.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.intothebullethell.game.IntoTheBulletHell;

public class MenuPrincipalPantalla implements Screen {

    private Stage stage;
    private Skin skin;
    private Music menuMusic;

    public MenuPrincipalPantalla(IntoTheBulletHell game) {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        // Cargar y reproducir la música del menú
        menuMusic = Gdx.audio.newMusic(Gdx.files.internal("sonidos/musica/DarkSouls.mp3"));
        menuMusic.setLooping(true);
        menuMusic.play();

        // Crear el Skin programáticamente
        skin = new Skin();
        BitmapFont font = new BitmapFont();
        skin.add("default-font", font);

        TextButtonStyle textButtonStyle = new TextButtonStyle();
        textButtonStyle.font = skin.getFont("default-font");
        skin.add("default", textButtonStyle);

        LabelStyle labelStyle = new LabelStyle();
        labelStyle.font = skin.getFont("default-font");
        skin.add("default", labelStyle);

        // Crear el Label para el título
        Label titleLabel = new Label("Into The Bullet Hell", skin, "default");
        titleLabel.setFontScale(2); // Ajusta el tamaño del texto si es necesario

        TextButton playButton = new TextButton("Jugar", skin);
        TextButton exitButton = new TextButton("Salir", skin);

        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new Jugar(game));
                menuMusic.stop(); // Detener la música del menú al cambiar de pantalla
            }
        });

        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        Table table = new Table();
        table.setFillParent(true);
        table.add(titleLabel).pad(10).center().expand(); // Añadir título
        table.row();
        table.add(playButton).pad(10).fillX().uniformX();
        table.row();
        table.add(exitButton).pad(10).fillX().uniformX();

        stage.addActor(table);
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
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
        skin.dispose();
        menuMusic.dispose(); // Liberar recursos de la música
    }
}
