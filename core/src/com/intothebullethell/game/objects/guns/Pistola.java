package com.intothebullethell.game.objects.guns;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.intothebullethell.game.entities.Proyectil;
import com.intothebullethell.sound.EfectoSonidoArma;

public class Pistola extends Arma {
    public Pistola() {
        super("Pistola", 200, 1, 0.4f, 10, false, 300, new Texture("imagenes/objetos/armaProyectil/bala.png"), new Texture("imagenes/objetos/armas/pistola.png"), new EfectoSonidoArma("sonidos/armas/Pistola.wav"));
    }

    @Override
    public void disparar(Vector2 position, Vector2 target, ArrayList<Proyectil> proyectiles) {
        // Pistola dispara un solo proyectil hacia el objetivo
        proyectiles.add(new Proyectil(projectilTextura, position, target, projectilVelocidad, daño));
    }
}
