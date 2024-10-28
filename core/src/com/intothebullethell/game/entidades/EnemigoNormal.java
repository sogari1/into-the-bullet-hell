package com.intothebullethell.game.entidades;

import java.util.ArrayList;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.intothebullethell.game.globales.RecursoRuta;

public class EnemigoNormal extends Enemigo {
    public EnemigoNormal(Jugador jugador, ArrayList<Enemigo> enemigos, TiledMapTileLayer collisionLayer) {
        super(RecursoRuta.ENEMIGO, 10, 20, 12f, 1, 60, RecursoRuta.PROYECTIL_ESCOPETA, jugador, enemigos, collisionLayer);
    }
    @Override
    public void atacar() {
    	Vector2 position = new Vector2(getX() + getWidth() / 2, getY() + getHeight() / 2);
    	Vector2 target = new Vector2(jugador.getX() + jugador.getWidth() / 2, jugador.getY() + jugador.getHeight() / 2);
           
    	Proyectil proyectil = new Proyectil(getProjectilTextura(), position, target, projectilVelocidad, daño, false);
    	proyectilManager.agregarProyectil(proyectil); 
    }
}

