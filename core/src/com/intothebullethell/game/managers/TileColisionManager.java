package com.intothebullethell.game.managers;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Rectangle;
import com.intothebullethell.game.entidades.Entidad;
import com.intothebullethell.game.globales.ConfiguracionJuego;

public class TileColisionManager {
    private TiledMapTileLayer collisionLayer;

    public TileColisionManager(TiledMapTileLayer collisionLayer) {
        this.collisionLayer = collisionLayer;
    }

    public boolean isCollision(Rectangle boundingBox) {
        if (collisionLayer != null) {
            int tileWidth = collisionLayer.getTileWidth();
            int tileHeight = collisionLayer.getTileHeight();
            int startX = (int) (boundingBox.x / tileWidth);
            int startY = (int) (boundingBox.y / tileHeight);
            int endX = (int) ((boundingBox.x + boundingBox.width) / tileWidth);
            int endY = (int) ((boundingBox.y + boundingBox.height) / tileHeight);

//            System.out.println("Checking collision for boundingBox: " + boundingBox);

            for (int x = startX; x <= endX; x++) {
                for (int y = startY; y <= endY; y++) {
                    Cell cell = collisionLayer.getCell(x, y);
                    if (cell != null && cell.getTile() != null && cell.getTile().getProperties().containsKey(ConfiguracionJuego.TILE_COLISION)) {
//                        System.out.println("Collision detected at: (" + x + ", " + y + ")");
                        return true;
                    }
                }
            }
        }
        return false;
    }


    public void setPositionAndCheckCollision(Entidad entidad, float newX, float newY, float oldX, float oldY) {
//    	System.out.println("Entitdad moviendose: " + entidad.getClass().getSimpleName());
        entidad.setX(newX);
        entidad.setY(newY);
        entidad.updateBoundingBox();

        if (isCollision(entidad.getBoundingBox())) {
            entidad.setX(oldX);
            entidad.setY(oldY);
            entidad.updateBoundingBox();
//        	System.out.println(entidad.getClass().getSimpleName() + " ha colisionado");
        }
    }
}