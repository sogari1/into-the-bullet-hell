package com.intothebullethell.game.managers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public abstract class RenderManager {

	public static SpriteBatch batch = new SpriteBatch();
	public static TiledMap mapa = new TmxMapLoader().load(AssetManagerJuego.MAPA);;
	public static OrthogonalTiledMapRenderer renderizar = new OrthogonalTiledMapRenderer(mapa);;
	public static SpriteBatch batchRender = (SpriteBatch) RenderManager.renderizar.getBatch();
	
    public static void begin(){
        batch.begin();
    }

    public static void end(){
        batch.end();
    }

    public static void dispose() {
        batch.dispose();
    }

}
