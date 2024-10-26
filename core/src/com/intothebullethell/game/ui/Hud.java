package com.intothebullethell.game.ui;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.intothebullethell.game.entities.Jugador;
import com.intothebullethell.game.managers.RecursoManager;

public class Hud {
    private SpriteBatch batch;
    private Stage stage;
    private Jugador jugador;
    private Texture armaSprite;
    private BitmapFont font;
    private Label textoRonda, textoTiempo;
    private int ronda;

    public Hud(SpriteBatch spriteBatch, Jugador jugador) {
        this.batch = spriteBatch;
        this.jugador = jugador;

        // Inicializa el stage con un viewport
        this.stage = new Stage(new ScreenViewport(), spriteBatch);

        // Cargar la fuente y estilos de texto
        font = new BitmapFont(); 
        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.WHITE);

        textoRonda = new Label("Ronda: 1", labelStyle);
        textoRonda.setPosition(10, Gdx.graphics.getHeight() - 30);
        stage.addActor(textoRonda);

        textoTiempo = new Label("Tiempo: 30", labelStyle);
        textoTiempo.setPosition(10, Gdx.graphics.getHeight() - 60);
        stage.addActor(textoTiempo);

        updateWeaponSprite();
    }

    public void render() {
        batch.begin();
        
        int x = Gdx.graphics.getWidth() - (jugador.getVida() / 2 * RecursoManager.CORAZON_LLENO.getWidth()) - 10;
        int y = Gdx.graphics.getHeight() - RecursoManager.CORAZON_LLENO.getHeight() - 10;
        HudUtiles.drawHearts(batch, RecursoManager.CORAZON_LLENO, RecursoManager.CORAZON_MITAD, RecursoManager.CORAZON_VACIO, jugador.getVida(), jugador.getVida(), x, y);

        if (armaSprite != null) {
            batch.draw(armaSprite, Gdx.graphics.getWidth() - armaSprite.getWidth(), 0);
        }

        // Dibuja la información de munición usando HudUtils
        HudUtiles.drawWeaponInfo(batch, jugador.getArmaEquipada(), font);

        batch.end();

        stage.draw();
    }

    public void actualizarRonda(int nuevaRonda) {
        ronda = nuevaRonda;
        textoRonda.setText("Ronda: " + ronda);
    }

    public void actualizarTemporizador(int tiempo) {
        textoTiempo.setText("Tiempo: " + tiempo);
    }

    public void updateWeaponSprite() {
        if (jugador != null) {
            armaSprite = jugador.getArmaTextura();
        }
    }

    public void dispose() {
        stage.dispose();
        font.dispose();
    }
}
