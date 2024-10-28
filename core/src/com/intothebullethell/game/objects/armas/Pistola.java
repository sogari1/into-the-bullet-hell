package com.intothebullethell.game.objects.armas;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;
import com.intothebullethell.game.entidades.Proyectil;
import com.intothebullethell.game.globales.RecursoRuta;
import com.intothebullethell.game.globales.SonidoRuta;

public class Pistola extends Arma {
    public Pistola() {
        super("Pistola", 200, 1, 0.4f, 10, false, 300, RecursoRuta.PROYECTIL_PISTOLA, RecursoRuta.ARMA_PISTOLA, SonidoRuta.DISPARIO_PISTOLA);
    }

    @Override
    public void disparar(Vector2 position, Vector2 target, ArrayList<Proyectil> proyectiles) {
        proyectiles.add(new Proyectil(proyectilTextura, position, target, proyectilVelocidad, daño, true));
    }
}
