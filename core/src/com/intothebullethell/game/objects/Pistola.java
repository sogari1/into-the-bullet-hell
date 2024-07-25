package com.intothebullethell.game.objects;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.intothebullethell.game.entities.Projectile;

public class Pistola extends Arma {
    public Pistola() {
        super(300, 10, 0.5f, new Texture("imagenes/pistola_projectil.png"));
    }

    @Override
    public void shoot(Vector2 position, Vector2 target, ArrayList<Projectile> projectiles) {
        projectiles.add(new Projectile(projectileTexture, position, target, projectileSpeed));
    }
}
