package com.intothebullethell.game.objects;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.intothebullethell.game.entities.Projectile;

public abstract class Arma {
    protected float projectileSpeed;
    protected int damage;
    protected float fireRate;
    protected Texture projectileTexture;

    public Arma(float projectileSpeed, int damage, float fireRate, Texture projectileTexture) {
        this.projectileSpeed = projectileSpeed;
        this.damage = damage;
        this.fireRate = fireRate;
        this.projectileTexture = projectileTexture;
    }

    public abstract void shoot(Vector2 position, Vector2 target, ArrayList<Projectile> projectiles);

    public float getFireRate() {
        return fireRate;
    }
}
