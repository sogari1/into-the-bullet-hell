package com.intothebullethell.game.objects.guns;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.intothebullethell.game.entities.Projectile;

public class Escopeta extends Arma {
    public Escopeta() {
        super("Escopeta", 150, 15, 1.0f, 8, false, 64, new Texture("imagenes/objetos/armaProyectil/pistola_projectil.png"), new Texture("imagenes/objetos/armas/escopeta.png"));
    }

    @Override
    public void shoot(Vector2 position, Vector2 target, ArrayList<Projectile> projectiles) {
        // Escopeta dispara múltiples proyectiles en un abanico
        for (int i = -2; i <= 2; i++) {
            // Puedes ajustar los ángulos o el rango según el diseño del abanico
        	Vector2 spreadTarget = new Vector2(target).add(new Vector2(i * 30, 0)); // Ajusta el spread aquí
            projectiles.add(new Projectile(projectileTexture, position, spreadTarget, projectileSpeed, damage));
        }
        
    }
}
