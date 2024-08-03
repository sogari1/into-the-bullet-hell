package com.intothebullethell.game.map;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;

public class MapHelper {
    private TiledMap map;

    public MapHelper(TiledMap map) {
        this.map = map;
    }

    public boolean isTileCollidable(float x, float y) {
        // Convierte las coordenadas del mundo a coordenadas del tile
        int tileX = (int) (x / 32);
        int tileY = (int) (y / 32);

        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get("Capa de patrones 1");
        TiledMapTileLayer.Cell cell = layer.getCell(tileX, tileY);

        if (cell != null) {
            // Comprobar si la tile tiene la propiedad "bloque"
            MapProperties properties = cell.getTile().getProperties();
            return properties.containsKey("bloque");
        }

        return false;
    }

    public boolean isPositionValid(Vector2 position) {
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(0);

        // Obtén las coordenadas en términos de celdas del mapa
        int tileX = (int) (position.x / layer.getTileWidth());
        int tileY = (int) (position.y / layer.getTileHeight());

        // Verifica si las coordenadas están dentro de los límites del mapa
        if (tileX < 0 || tileX >= layer.getWidth() || tileY < 0 || tileY >= layer.getHeight()) {
            return false;
        }

        // Verifica si la celda está bloqueada (puedes personalizar esto según cómo manejas las celdas bloqueadas)
        TiledMapTileLayer.Cell cell = layer.getCell(tileX, tileY);
        if (cell != null && cell.getTile() != null) {
            // Puedes agregar lógica adicional aquí para verificar si la celda está bloqueada
            return false;
        }

        return true;
    }
}
