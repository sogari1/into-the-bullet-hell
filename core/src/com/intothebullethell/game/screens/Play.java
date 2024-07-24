package com.intothebullethell.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.intothebullethell.game.entities.Player;

public class Play implements Screen {

	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	private OrthographicCamera camera;
	
	private Player player;

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		camera.position.set(player.getX() + player.getWidth() / 2, player.getY() + player.getHeight() / 2, 0);
		camera.update();
		
		renderer.setView(camera);
		renderer.render();
		
		renderer.getBatch().begin();
		player.draw(renderer.getBatch());
		renderer.getBatch().end();
	}

	@Override
	public void resize(int width, int height) {
		camera.viewportWidth = width - 200;
		camera.viewportHeight = height - 200;
	}

	@Override
	public void show() {
		map = new TmxMapLoader().load("mapas/mapa.tmx");
		renderer = new OrthogonalTiledMapRenderer(map);
		camera = new OrthographicCamera();
		
		// Carga las texturas para las diferentes direcciones
		TextureRegion upSprite = new TextureRegion(new Texture("imagenes/upSprite.png"));
		TextureRegion downSprite = new TextureRegion(new Texture("imagenes/downSprite.png"));
		TextureRegion leftSprite = new TextureRegion(new Texture("imagenes/leftSprite.png"));
		TextureRegion rightSprite = new TextureRegion(new Texture("imagenes/rightSprite.png"));
		
		// Crea el jugador con las texturas para las diferentes direcciones
		player = new Player(new Sprite(downSprite), (TiledMapTileLayer) map.getLayers().get(0),new TextureRegion(upSprite), new TextureRegion(downSprite), new TextureRegion(leftSprite), new TextureRegion(rightSprite), camera);
				
		player.setPosition(15 * player.getCollisionLayer().getTileWidth(), 15 * player.getCollisionLayer().getHeight());
				
		Gdx.input.setInputProcessor(player);	
	}
	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void hide() {
		dispose();
	}

	@Override
	public void dispose() {
		map.dispose();
		renderer.dispose();
		player.getTexture().dispose();
	}

}
