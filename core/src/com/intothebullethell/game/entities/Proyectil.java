package com.intothebullethell.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Proyectil extends Sprite {
    private Vector2 position;
    private Vector2 direction;
    private float velocidad;
    private float daño;
    private Rectangle boundingBox;

    public Proyectil(Texture texture, Vector2 position, Vector2 target, float velocidad, float daño) {
        super(texture);
        this.position = new Vector2(position);
        this.direction = new Vector2(target).sub(position).nor();
        this.velocidad = velocidad;
        this.daño = daño;
        // Ajusta el origen del sprite al centro
        setOrigin(getWidth() / 2, getHeight() / 2);
        // Establece la posición inicial en el centro del sprite
        setPosition(position.x - getWidth() / 2, position.y - getHeight() / 2);
        // Inicializa el bounding box
        boundingBox = new Rectangle(getX(), getY(), getWidth(), getHeight());
    }

    public void update(float delta) {
        Vector2 velocity = new Vector2(direction).scl(velocidad * delta);
        position.add(velocity);
        // Actualiza la posición teniendo en cuenta el tamaño del sprite
        setPosition(position.x - getWidth() / 2, position.y - getHeight() / 2);
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
