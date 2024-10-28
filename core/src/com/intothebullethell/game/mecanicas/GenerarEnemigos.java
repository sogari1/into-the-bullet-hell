package com.intothebullethell.game.mecanicas;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.intothebullethell.game.entidades.Enemigo;
import com.intothebullethell.game.entidades.EnemigoFuerte;
import com.intothebullethell.game.entidades.EnemigoNormal;
import com.intothebullethell.game.entidades.EnemigoRapido;
import com.intothebullethell.game.entidades.Jugador;
import com.intothebullethell.game.managers.TileColisionManager;

public class GenerarEnemigos {
    private OrthographicCamera camara;
    private TiledMap map;
    private ArrayList<Enemigo> enemigos;
    private ArrayList<Enemigo> listaEnemigos;
    private Set<Vector2> occupiedPositions;
    private Jugador jugador; 
    private TiledMapTileLayer collisionLayer; 
    private TileColisionManager tileCollisionManager;
    private Random random;
    
    private int numeroDeEnemigos = 10;

    public GenerarEnemigos(OrthographicCamera camara, TiledMap map, ArrayList<Enemigo> enemigos, Jugador jugador, TiledMapTileLayer collisionLayer, TileColisionManager tileCollisionManager) {
        this.camara = camara;
        this.map = map;
        this.enemigos = enemigos;
        this.occupiedPositions = new HashSet<>();
        this.jugador = jugador;
        this.collisionLayer = collisionLayer;
        this.tileCollisionManager = tileCollisionManager;
        this.listaEnemigos = new ArrayList<>();
        this.random = new Random();
    }
    public void generarEnemigos() {
        enemigos.clear();
        occupiedPositions.clear();
        for (int i = 0; i < numeroDeEnemigos; i++) {
            Vector2 spawnPosition;
            do {
                spawnPosition = crearSpawnUnico();
            } while (isDentroCamaraJugador(spawnPosition));

            Enemigo enemigo = crearnEnemigoAleatorio(spawnPosition, jugador);
            enemigo.setPosition(spawnPosition.x, spawnPosition.y);
            enemigo.updateBoundingBox();
            enemigos.add(enemigo);
        }
//        System.out.println("Cantidad de enemigos spawneados: " + enemigos.size());
        sumarNumeroDeEnemigos();
    }

    private boolean isDentroCamaraJugador(Vector2 position) {
        float cameraLeft = camara.position.x - camara.viewportWidth / 2;
        float cameraRight = camara.position.x + camara.viewportWidth / 2;
        float cameraBottom = camara.position.y - camara.viewportHeight / 2;
        float cameraTop = camara.position.y + camara.viewportHeight / 2;

        return position.x > cameraLeft && position.x < cameraRight && position.y > cameraBottom && position.y < cameraTop;
    }

    private Vector2 crearSpawnUnico() {
        int mapWidth = 50;
        int mapHeight = 50; 
        int tileWidth = 32; 
        int tileHeight = 32; 

        Vector2 position;
        do {
            position = new Vector2(
                (float) (Math.random() * (mapWidth - 1) * tileWidth),
                (float) (Math.random() * (mapHeight - 1) * tileHeight)
            );
        } while (occupiedPositions.contains(position) || !isPosicionValida(position));

        occupiedPositions.add(position);
//        System.out.println(occupiedPositions);
        return position;
    }

    private boolean isPosicionValida(Vector2 position) {
        int tileSize = 32; 
        int x = (int) position.x / tileSize;
        int y = (int) position.y / tileSize;

        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(0);
        if (x >= 0 && x < layer.getWidth() && y >= 0 && y < layer.getHeight()) {
            Rectangle boundingBox = new Rectangle(position.x, position.y, tileSize, tileSize);
            return !tileCollisionManager.esColision(boundingBox);
        }
        return false;
    }

    private Enemigo crearnEnemigoAleatorio(Vector2 spawnPosition, Jugador jugador) {
    	inicializarListaEnemigos();
    	Enemigo enemigo;
    	enemigo = listaEnemigos.get(random.nextInt(listaEnemigos.size()));
        enemigo.setPosition(spawnPosition.x, spawnPosition.y);
        return enemigo;
    }
    private int sumarNumeroDeEnemigos() {
        numeroDeEnemigos += 2;
        return numeroDeEnemigos;
    }
    private void inicializarListaEnemigos() {
    	 listaEnemigos.add(new EnemigoNormal(jugador, enemigos, collisionLayer));
         listaEnemigos.add(new EnemigoRapido(jugador, enemigos, collisionLayer));
         listaEnemigos.add(new EnemigoFuerte(jugador, enemigos, collisionLayer));
    }
}
