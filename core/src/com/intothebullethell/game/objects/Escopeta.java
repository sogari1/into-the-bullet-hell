package com.intothebullethell.game.objects;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.intothebullethell.game.entities.Projectile;

public class Escopeta extends Arma {
    public Escopeta() {
        super(200, 15, 1.0f, new Texture("imagenes/pistola_projectil.png"));
    }

    @Override
    public void shoot(Vector2 position, Vector2 target, ArrayList<Projectile> projectiles) {
        // Escopeta dispara múltiples proyectiles en un abanico
        for (int i = -2; i <= 2; i++) {
            Vector2 newTarget = target.cpy().rotateDeg(i * 10);
            projectiles.add(new Projectile(projectileTexture, position, newTarget, projectileSpeed));
        }
    }
}
