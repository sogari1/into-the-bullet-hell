package com.intothebullethell.game.entidades;

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
    protected TiledMapTileLayer collisionLayer;
    protected ProyectilManager proyectilManager;

    public Enemigo(Texture texture, int vida, int velocidad, float intervaloAtaque, int daño, float projectilVelocidad, Texture projectilTextura, Jugador jugador, ArrayList<Enemigo> enemigos, TiledMapTileLayer collisionLayer) {
        super(texture, vida, velocidad, projectilTextura, collisionLayer);
        this.jugador = jugador;
        this.enemigos = enemigos;
        this.intervaloAtaque = intervaloAtaque;
        this.tiempoAtaque = intervaloAtaque;
        this.daño = daño;
        this.projectilVelocidad = projectilVelocidad;
        this.collisionLayer = collisionLayer;
    }

    @Override
    public void update(float delta) {
        moverHaciaJugador(delta);
 
        tiempoAtaque -= delta;
//        System.out.println("Tiempo antes del ataque: " + tiempoAtaque );
        if (tiempoAtaque <= 0) {
            atacar();
            tiempoAtaque = intervaloAtaque;
        }
    }

    private void moverHaciaJugador(float delta) {

        Vector2 position = new Vector2(getX(), getY());
        Vector2 target = new Vector2(jugador.getX(), jugador.getY());
        Vector2 direction = target.sub(position).nor();

        velocity.set(direction).scl(velocidad);

        mover(delta, velocity);
    }


    @Override
    protected void remove() {
        enemigos.remove(this);
    }

    @Override
    public void atacar() {
    }



    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        boundingBox.setPosition(x, y); 
    }

    @Override
    public void recibirDaño(int daño) {
        vidaMaxima -= daño;
        if (vidaMaxima <= 0) {
            remove();
        }
    }

	public void setProyectilManager(ProyectilManager proyectilManager) {
	    this.proyectilManager = proyectilManager;
	}
}
