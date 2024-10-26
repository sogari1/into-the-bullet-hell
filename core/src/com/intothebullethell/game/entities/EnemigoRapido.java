package com.intothebullethell.game.entities;

import java.util.ArrayList;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.intothebullethell.game.managers.RecursoManager;

public class EnemigoRapido extends Enemigo {
    public EnemigoRapido(Jugador jugador, ArrayList<Enemigo> enemigos, TiledMapTileLayer collisionLayer) {
        super(RecursoManager.ENEMIGO, 4, 20, 4f, 2, 100, true, RecursoManager.PROYECTIL_ESCOPETA, jugador, enemigos, collisionLayer);
    }
}
