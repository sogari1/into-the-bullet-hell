package com.intothebullethell.game.entities;

import java.util.ArrayList;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.intothebullethell.game.managers.RecursoManager;

public class EnemigoNormal extends Enemigo {
    public EnemigoNormal(Jugador jugador, ArrayList<Enemigo> enemigos, TiledMapTileLayer collisionLayer) {
        super(RecursoManager.ENEMIGO, 10, 30, 3f, 1, 100, false, RecursoManager.PROYECTIL_ESCOPETA, jugador, enemigos, collisionLayer);
    }
}
