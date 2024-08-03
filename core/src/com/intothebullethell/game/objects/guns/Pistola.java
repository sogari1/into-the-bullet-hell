package com.intothebullethell.game.objects.guns;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.intothebullethell.game.entities.Projectile;

public class Pistola extends Arma {
    public Pistola() {
        super("Pistola", 200, 1, 0.4f, 10, false, 300, new Texture("imagenes/objetos/armaProyectil/bala.png"), new Texture("imagenes/objetos/armas/pistola.png"));
    }

    @Override
    public void shoot(Vector2 position, Vector2 target, ArrayList<Projectile> projectiles) {
        // Pistola dispara un solo proyectil hacia el objetivo
        projectiles.add(new Projectile(projectileTexture, position, target, projectileSpeed, damage));
    }
}
