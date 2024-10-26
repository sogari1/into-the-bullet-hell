package com.intothebullethell.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Boton extends Actor {
    private Texto texto;
    private Rectangle bounds;

    public Boton(Texto texto) {
        this.texto = texto;
        this.bounds = new Rectangle(texto.getX(), texto.getY() - texto.getHeight(), texto.getWidth(), texto.getHeight());
    }

    public void draw(SpriteBatch batch) {
        texto.draw(batch); 
    }

    public boolean isMouseOver() {
        float mouseX = Gdx.input.getX();
        float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();
        return bounds.contains(mouseX, mouseY); 
    }

    public boolean isClicked() {
        return isMouseOver() && Gdx.input.isButtonJustPressed(com.badlogic.gdx.Input.Buttons.LEFT); 
    }

    public void setPosition(float x, float y) {
        texto.setPosition(x, y); // Actualiza la posición del texto
        bounds.setPosition(x, y - texto.getHeight()); // Actualiza los límites del botón
    }
    public void centrarX() {
        float centerX = (Gdx.graphics.getWidth() - texto.getWidth()) / 2;
        setPosition(centerX, texto.getY()); // Ajusta la posición del texto
    }
    public void centrarY() {
        float centerY = (Gdx.graphics.getHeight() - texto.getHeight()) / 2;
        setPosition(texto.getX(), centerY); // Ajusta la posición del texto
    }
    public void setColor(Color color) {
        texto.setColor(color); // Cambia el color del texto
    }

    public void dispose() {
        texto.dispose(); // Libera los recursos del texto
    }
}