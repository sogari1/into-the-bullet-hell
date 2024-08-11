package com.intothebullethell.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Proyectil extends Sprite {
    private Vector2 posicion;
    private Vector2 direccion;
    private float velocidad;
    private float daño;
    private Rectangle boundingBox;

    public Proyectil(Texture texture, Vector2 posicion, Vector2 target, float velocidad, float daño) {
        super(texture);
        this.posicion = new Vector2(posicion);
        this.direccion = new Vector2(target).sub(posicion).nor();
        this.velocidad = velocidad;
        this.daño = daño;
        // Ajusta el origen del sprite al centro
        setOrigin(getWidth() / 2, getHeight() / 2);
        // Establece la posición inicial en el centro del sprite
        setPosition(posicion.x - getWidth() / 2, posicion.y - getHeight() / 2);
        // Inicializa el bounding box
        boundingBox = new Rectangle(getX(), getY(), getWidth(), getHeight());
    }

    public void update(float delta) {
        Vector2 velocity = new Vector2(direccion).scl(velocidad * delta);
        posicion.add(velocity);
        // Actualiza la posición teniendo en cuenta el tamaño del sprite
        setPosition(posicion.x - getWidth() / 2, posicion.y - getHeight() / 2);
        boundingBox.setPosition(getX(), getY());
    }

    public boolean collidesWith(Entidad Entidad) {
        return boundingBox.overlaps(Entidad.getBoundingRectangle());
    }

    public float getDaño() {
        return daño;
    }

    public boolean isOutOfScreen() {
        return getX() + getWidth() < 0 || getX() > Gdx.graphics.getWidth() || getY() + getHeight() < 0 || getY() > Gdx.graphics.getHeight();
    }

    @Override
    public void draw(Batch batch) {
        super.draw(batch);
    }
}
