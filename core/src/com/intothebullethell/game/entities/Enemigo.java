package com.intothebullethell.game.entities;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.intothebullethell.game.managers.ProyectilManager;
public abstract class Enemigo extends Entidad {
    protected float intervaloAtaque;
    protected float tiempoAtaque;
    protected Jugador jugador;
    protected ArrayList<Enemigo> enemigos;
    protected float projectilVelocidad;
    protected int daño;
    protected boolean dispersion;
    protected TiledMapTileLayer collisionLayer;
    protected ProyectilManager proyectilManager;

    public Enemigo(Texture texture, int vida, int velocidad, float intervaloAtaque, int daño, float projectilVelocidad, boolean dispersion, Texture projectilTextura, Jugador jugador, ArrayList<Enemigo> enemigos, TiledMapTileLayer collisionLayer) {
        super(texture, vida, velocidad, projectilTextura, collisionLayer);
        this.jugador = jugador;
        this.enemigos = enemigos;
        this.intervaloAtaque = intervaloAtaque;
        this.tiempoAtaque = intervaloAtaque;
        this.daño = daño;
        this.projectilVelocidad = projectilVelocidad;
        this.dispersion = dispersion;
        this.collisionLayer = collisionLayer;
    }

    @Override
    public void update(float delta) {
        moverHaciaJugador(delta);
 
        tiempoAtaque -= delta;
        if (tiempoAtaque <= 0) {
            atacar();
            tiempoAtaque = intervaloAtaque;
        }
    }

    private void moverHaciaJugador(float delta) {

        Vector2 position = new Vector2(getX(), getY());
        Vector2 target = new Vector2(jugador.getX(), jugador.getY());
        Vector2 direction = target.sub(position).nor();

        velocity.set(direction).scl(velocidad * 2);

        mover(delta, velocity);
    }


    @Override
    protected void remove() {
        enemigos.remove(this);
    }

    @Override
    public void atacar() {
        Vector2 position = new Vector2(getX() + getWidth() / 2, getY() + getHeight() / 2);
        Vector2 target = new Vector2(jugador.getX() + jugador.getWidth() / 2, jugador.getY() + jugador.getHeight() / 2);
        Vector2 direction = target.cpy().sub(position).nor();

        if (dispersion) {
            for (int i = -2; i <= 2; i++) {
                Vector2 spreadDirection = new Vector2(direction).rotateDeg(i * 10);
                Vector2 spreadTarget = new Vector2(position).add(spreadDirection.scl(1000));
                Proyectil proyectil = new Proyectil(getProjectilTextura(), position, spreadTarget, projectilVelocidad, daño, false);
                proyectilManager.agregarProyectil(proyectil); 
            }
        } else {
            Proyectil proyectil = new Proyectil(getProjectilTextura(), position, target, projectilVelocidad, daño, false);
            proyectilManager.agregarProyectil(proyectil); 
        }
    }



    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        boundingBox.setPosition(x, y); 
    }

    @Override
    public void recibirDaño(float daño) {
        vida -= daño;
        if (vida <= 0) {
            remove();
        }
    }

	public void setProyectilManager(ProyectilManager proyectilManager) {
	    this.proyectilManager = proyectilManager;
	}
}
