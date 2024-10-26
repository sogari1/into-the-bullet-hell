package com.intothebullethell.game.managers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public abstract class RenderManager {

	public static SpriteBatch batch = new SpriteBatch();
    public static ShapeRenderer shapeRenderer = new ShapeRenderer();

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
