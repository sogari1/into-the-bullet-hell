package com.intothebullethell.game.objects.guns;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;
import com.intothebullethell.game.entities.Proyectil;
import com.intothebullethell.game.managers.RecursoManager;
import com.intothebullethell.game.managers.SonidoManager;

public class Pistola extends Arma {
    public Pistola() {
        super("Pistola", 200, 1, 0.4f, 10, false, 300, RecursoManager.PROYECTIL_PISTOLA, RecursoManager.ARMA_PISTOLA, SonidoManager.DISPARIO_PISTOLA);
    }

    @Override
    public void disparar(Vector2 position, Vector2 target, ArrayList<Proyectil> proyectiles) {
        proyectiles.add(new Proyectil(proyectilTextura, position, target, proyectilVelocidad, daño, true));
    }
}
