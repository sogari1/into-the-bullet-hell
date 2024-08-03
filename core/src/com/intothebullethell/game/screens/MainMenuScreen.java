package com.intothebullethell.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.intothebullethell.game.IntoTheBulletHell;
import com.badlogic.gdx.scenes.scene2d.InputEvent;

public class MainMenuScreen implements Screen {

    private Stage stage;
    private Skin skin;

    public MainMenuScreen(IntoTheBulletHell game) {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        // Crear el Skin programáticamente
        skin = new Skin();
        // Crear la fuente
        BitmapFont font = new BitmapFont();
        skin.add("default-font", font);

        // Crear el estilo para los botones
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = skin.getFont("default-font");
        skin.add("default", textButtonStyle);

        // Crear los botones
        TextButton playButton = new TextButton("Jugar", skin);
        TextButton exitButton = new TextButton("Salir", skin);

        // Manejar los eventos de los botones
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new Play(game));
            }
        });

        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        // Añadir los botones a una tabla para layout
        Table table = new Table();
        table.setFillParent(true);
        table.add(playButton).pad(10).fillX().uniformX();
        table.row();
        table.add(exitButton).pad(10).fillX().uniformX();

        stage.addActor(table);
    }

    @Override
    public void show() {
    }

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
        skin.dispose();
    }
}

