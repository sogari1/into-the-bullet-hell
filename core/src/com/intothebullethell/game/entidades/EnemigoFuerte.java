package com.intothebullethell.game.entidades;

import java.util.ArrayList;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.intothebullethell.game.globales.RecursoRuta;

public class EnemigoFuerte extends Enemigo {

	public EnemigoFuerte(Jugador jugador, ArrayList<Enemigo> enemigos, TiledMapTileLayer collisionLayer) {
		super(RecursoRuta.ENEMIGO, 8, 20, 3f, 1, 60, RecursoRuta.PROYECTIL_ESCOPETA, jugador, enemigos, collisionLayer);
	}
	@Override
    public void atacar() {
    	 Vector2 position = new Vector2(getX() + getWidth() / 2, getY() + getHeight() / 2);
         Vector2 target = new Vector2(jugador.getX() + jugador.getWidth() / 2, jugador.getY() + jugador.getHeight() / 2);
         Vector2 direction = target.cpy().sub(position).nor();
         
         for (int i = -2; i <= 2; i++) {
             Vector2 spreadDirection = new Vector2(direction).rotateDeg(i * 10);
             Vector2 spreadTarget = new Vector2(position).add(spreadDirection.scl(1000));
             Proyectil proyectil = new Proyectil(getProjectilTextura(), position, spreadTarget, projectilVelocidad, daño, false);
             proyectilManager.agregarProyectil(proyectil); 
         }
    }

}
