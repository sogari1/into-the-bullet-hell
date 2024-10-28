package com.intothebullethell.game.ui;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.intothebullethell.game.entidades.Jugador;
import com.intothebullethell.game.globales.RecursoRuta;
import com.intothebullethell.game.managers.RenderManager;

public class Hud {
    private Stage stage;
    private Jugador jugador;
    private Texture armaSprite;
    private Texto textoRonda, textoTiempo, textoMunicion, textoEnemigosRestantes;
    private int ronda;

    public Hud(SpriteBatch spriteBatch, Jugador jugador) {
        this.jugador = jugador;
        this.stage = new Stage(new ScreenViewport(), spriteBatch);

        textoRonda = new Texto("Ronda: 1", 20, Color.WHITE, 10, Gdx.graphics.getHeight() - 20);
        textoRonda.setShadow(4, 4, Color.BLACK);
        
        textoTiempo = new Texto("Aleatorizando en: 30", 20, Color.WHITE, 10, Gdx.graphics.getHeight() - 20);
        textoTiempo.setShadow(4, 4, Color.BLACK);
        textoTiempo.centerX();
        
        textoMunicion = new Texto("", 20, Color.WHITE, 10, Gdx.graphics.getHeight() - 850);
        textoMunicion.setShadow(4, 4, Color.BLACK);
        
        textoEnemigosRestantes = new Texto("Enemigos restantes: ", 20, Color.WHITE, 10, Gdx.graphics.getHeight() - 60);
        textoEnemigosRestantes.setShadow(4, 4, Color.BLACK);
        updateWeaponSprite();
    }

    public void render() {
        RenderManager.begin();
        
        int x = Gdx.graphics.getWidth() - (jugador.getVidaMaxima() / 2 * RecursoRuta.CORAZON_LLENO.getWidth());
        int y = Gdx.graphics.getHeight() - RecursoRuta.CORAZON_LLENO.getHeight();
        
        HudUtiles.drawHearts(RenderManager.batch, RecursoRuta.CORAZON_LLENO, RecursoRuta.CORAZON_MITAD, RecursoRuta.CORAZON_VACIO, jugador.getVidaMaxima(), jugador.getVidaActual(), x, y);
        HudUtiles.drawWeaponInfo(RenderManager.batch, jugador.getArmaEquipada(), textoMunicion);
        RenderManager.batch.draw(armaSprite, Gdx.graphics.getWidth() - armaSprite.getWidth() * 3 - 20, 0, armaSprite.getWidth() * 3 - 10, armaSprite.getHeight() * 3);
        textoRonda.draw(RenderManager.batch);
        textoTiempo.draw(RenderManager.batch);
        textoEnemigosRestantes.draw(RenderManager.batch);
        RenderManager.end();

        stage.draw();
    }

    public void actualizarRonda(int nuevaRonda) {
        ronda = nuevaRonda;
        textoRonda.setText("Ronda: " + ronda);
    }

    public void actualizarTemporizador(int tiempo) {
        textoTiempo.setText("Aleatorizando en: " + tiempo + "s");
    }
    public void actualizarEnemigosRestantes(int cantidad) {
    	textoEnemigosRestantes.setText("Enemigos restantes: " + cantidad);
    }
    public void updateWeaponSprite() {
    	armaSprite = jugador.getArmaTextura();
    }

    public void dispose() {
        stage.dispose();
        textoRonda.dispose();
        textoTiempo.dispose();
        textoMunicion.dispose();
        textoEnemigosRestantes.dispose();
    }
}
