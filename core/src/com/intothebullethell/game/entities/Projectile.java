package com.intothebullethell.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

public class Projectile extends Sprite {
    private Vector2 direction;
    private float speed;

    public Projectile(Texture texture, Vector2 position, Vector2 target, float speed) {
        super(texture);
        // Ajusta el origen del sprite al centro
        setOrigin(getWidth() / 2, getHeight() / 2);

        // Establece la posición inicial en el centro del sprite
        setPosition(position.x - getWidth() / 2, position.y - getHeight() / 2);

        this.direction = target.sub(position).nor();
        this.speed = speed;
    }

    public void update(float delta) {
        // Actualiza la posición teniendo en cuenta el tamaño del sprite
        setPosition(getX() + direction.x * speed * delta, getY() + direction.y * speed * delta);
    }

    public boolean isOutOfScreen() {
        return getX() + getWidth() < 0 || getX() > Gdx.graphics.getWidth() || getY() + getHeight() < 0 || getY() > Gdx.graphics.getHeight();
    }

    @Override
    public void draw(Batch batch) {
        super.draw(batch);
    }
}
