package com.intothebullethell.game.mecanicas;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.intothebullethell.game.entities.Enemigo;
import com.intothebullethell.game.entities.EnemigoNormal;
import com.intothebullethell.game.entities.EnemigoRapido;
import com.intothebullethell.game.entities.Jugador;
import com.intothebullethell.game.managers.TileColisionManager;

public class GenerarEnemigos {
    private OrthographicCamera camara;
    private TiledMap map;
    private ArrayList<Enemigo> enemigos;
    private Set<Vector2> occupiedPositions;
    private Jugador jugador; 
    private TiledMapTileLayer collisionLayer; 
    private TileColisionManager tileCollisionManager;

    public GenerarEnemigos(OrthographicCamera camara, TiledMap map, ArrayList<Enemigo> enemigos, Jugador jugador, TiledMapTileLayer collisionLayer, TileColisionManager tileCollisionManager) {
        this.camara = camara;
        this.map = map;
        this.enemigos = enemigos;
        this.occupiedPositions = new HashSet<>();
        this.jugador = jugador;
        this.collisionLayer = collisionLayer;
        this.tileCollisionManager = tileCollisionManager;
    }
    public void generarEnemigos(int cantidad) {
        enemigos.clear();
        occupiedPositions.clear();

        for (int i = 0; i < cantidad; i++) {
            Vector2 spawnPosition;
            do {
                spawnPosition = getUniqueSpawnPosition();
            } while (isInsideCameraView(spawnPosition));

            Enemigo enemy = createRandomEnemy(spawnPosition, jugador);
            enemy.setPosition(spawnPosition.x, spawnPosition.y);
            enemy.updateBoundingBox();
            enemigos.add(enemy);
        }
    }
    public void initializeEnemies(int cantidad, Jugador jugador) {
        enemigos.clear();
        occupiedPositions.clear();

        for (int i = 0; i < cantidad; i++) {
            Vector2 spawnPosition;
            do {
                spawnPosition = getUniqueSpawnPosition();
            } while (isInsideCameraView(spawnPosition));

            Enemigo enemy = createRandomEnemy(spawnPosition, jugador);
            enemy.setPosition(spawnPosition.x, spawnPosition.y);
            enemy.updateBoundingBox();
            enemigos.add(enemy);
        }
    }

    private boolean isInsideCameraView(Vector2 position) {
        float cameraLeft = camara.position.x - camara.viewportWidth / 2;
        float cameraRight = camara.position.x + camara.viewportWidth / 2;
        float cameraBottom = camara.position.y - camara.viewportHeight / 2;
        float cameraTop = camara.position.y + camara.viewportHeight / 2;

        return position.x > cameraLeft && position.x < cameraRight &&
               position.y > cameraBottom && position.y < cameraTop;
    }

    private Vector2 getUniqueSpawnPosition() {
        int mapWidth = 50; // Ajusta según el tamaño de tu mapa
        int mapHeight = 50; // Ajusta según el tamaño de tu mapa
        int tileWidth = 32; // Tamaño del tile
        int tileHeight = 32; // Tamaño del tile

        Vector2 position;
        do {
            position = new Vector2(
                (float) (Math.random() * (mapWidth - 1) * tileWidth),
                (float) (Math.random() * (mapHeight - 1) * tileHeight)
            );
        } while (occupiedPositions.contains(position) || !isPositionValid(position));

        occupiedPositions.add(position);
        return position;
    }

    private boolean isPositionValid(Vector2 position) {
        int tileSize = 32; // Tamaño del tile
        int x = (int) position.x / tileSize;
        int y = (int) position.y / tileSize;

        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(0);
        if (x >= 0 && x < layer.getWidth() && y >= 0 && y < layer.getHeight()) {
            Rectangle boundingBox = new Rectangle(position.x, position.y, tileSize, tileSize);
            return !tileCollisionManager.isCollision(boundingBox);
        }
        return false;
    }

    private Enemigo createRandomEnemy(Vector2 spawnPosition, Jugador jugador) {
        Enemigo enemy;
        double randomValue = Math.random();

        if (randomValue < 0.5) {
            enemy = new EnemigoRapido(jugador, enemigos, collisionLayer);
        } else {
            enemy = new EnemigoNormal(jugador, enemigos, collisionLayer); 
        }

        enemy.setPosition(spawnPosition.x, spawnPosition.y);
        return enemy;
    }
}
