package com.intothebullethell.game.entities;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.intothebullethell.game.managers.TileColisionManager;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public abstract class Entidad extends Sprite {
	private TileColisionManager tileCollisionManager;
    protected int vida;
    protected float velocidad;
    protected Texture projectilTextura;
    protected Rectangle boundingBox;
    protected Vector2 velocity; 

    public Entidad(Texture texture, int vida, int velocidad, Texture projectilTextura, TiledMapTileLayer collisionLayer) {
        super(texture);
        this.vida = vida;
        this.velocidad = velocidad;
        this.projectilTextura = projectilTextura;
        this.velocity = new Vector2();
        this.tileCollisionManager = new TileColisionManager(collisionLayer);
        this.boundingBox = new Rectangle(getX(), getY(), getWidth(), getHeight());
    }

    public void mover(float delta, Vector2 velocity) {
        float oldX = getX();
        float oldY = getY();

        float newX = getX() + velocity.x * delta;
        float newY = getY() + velocity.y * delta;

        tileCollisionManager.setPositionAndCheckCollision(this, newX, newY, oldX, oldY);
    }


    public void updateBoundingBox() {
        boundingBox.setPosition(getX(), getY());
        boundingBox.setSize(getWidth(), getHeight());
    }

    public Rectangle getBoundingBox() {
        return boundingBox;
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

    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

	public void atacar() {}
}

