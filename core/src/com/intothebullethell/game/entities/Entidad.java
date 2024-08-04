package com.intothebullethell.game.entities;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;

public abstract class Entidad extends Sprite {
    private TiledMapTileLayer collisionLayer;
    protected int vida;
    protected float velocidad;
    protected Texture projectilTextura;
    private Rectangle boundingBox;

    public Entidad(Texture texture, int vida, int velocidad, Texture projectilTextura) {
        super(texture);
        this.vida = vida;
        this.velocidad = velocidad;
        this.projectilTextura = projectilTextura;
        boundingBox = new Rectangle(getX(), getY(), getWidth(), getHeight());
    }

    public void move(float delta, Vector2 velocity) {
        float oldX = getX();
        float oldY = getY();
        setPositionAndCheckCollision(getX() + velocity.x * delta, getY(), oldX, oldY);
        setPositionAndCheckCollision(getX(), getY() + velocity.y * delta, oldX, oldY);
    }

    private void setPositionAndCheckCollision(float newX, float newY, float oldX, float oldY) {
        setX(newX);
        setY(newY);
        boundingBox.setPosition(getX(), getY());
        if (isCollision()) {
            setX(oldX);
            setY(oldY);
            boundingBox.setPosition(getX(), getY());
        }
    }

    private boolean isCollision() {
        if (collisionLayer != null) {
            int tileWidth = collisionLayer.getTileWidth();
            int tileHeight = collisionLayer.getTileHeight();
            int startX = (int) (boundingBox.x / tileWidth);
            int startY = (int) (boundingBox.y / tileHeight);
            int endX = (int) ((boundingBox.x + boundingBox.width) / tileWidth);
            int endY = (int) ((boundingBox.y + boundingBox.height) / tileHeight);

            for (int x = startX; x <= endX; x++) {
                for (int y = startY; y <= endY; y++) {
                    Cell cell = collisionLayer.getCell(x, y);
                    if (cell != null && cell.getTile() != null && cell.getTile().getProperties().containsKey("bloque")) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void setCollisionLayer(TiledMapTileLayer collisionLayer) {
        this.collisionLayer = collisionLayer;
    }

    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

    public float getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(float velocidad) {
        this.velocidad = velocidad;
    }

    public Texture getProjectilTextura() {
        return projectilTextura;
    }

    public void setProjectilTextura(Texture projectilTextura) {
        this.projectilTextura = projectilTextura;
    }


    public void recibirDaño(float daño) {
        vida -= daño;
        if (vida <= 0) {
            remove();
        }
    }

    protected abstract void remove();
    public abstract void update(float delta);
    public abstract void atacar(ArrayList<Proyectil> projectil);
}

