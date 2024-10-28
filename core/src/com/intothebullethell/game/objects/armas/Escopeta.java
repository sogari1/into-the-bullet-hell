package com.intothebullethell.game.objects.armas;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;
import com.intothebullethell.game.entidades.Proyectil;
import com.intothebullethell.game.globales.RecursoRuta;
import com.intothebullethell.game.globales.SonidoRuta;

public class Escopeta extends Arma {
    public Escopeta() {
        super("Escopeta", 150, 3, 1.5f, 8, false, 64, RecursoRuta.PROYECTIL_ESCOPETA, RecursoRuta.ARMA_ESCOPETA, SonidoRuta.DISPARIO_ESCOPETA);
    }

    @Override
    public void disparar(Vector2 position, Vector2 target, ArrayList<Proyectil> proyectiles) {
        Vector2 direction = new Vector2(target).sub(position).nor();

        for (int i = -2; i <= 2; i++) {
            Vector2 spreadDirection = new Vector2(direction).rotateDeg(i * 5); 
            Vector2 spreadTarget = new Vector2(position).add(spreadDirection.scl(1000));
            
            proyectiles.add(new Proyectil(proyectilTextura, position, spreadTarget, proyectilVelocidad, daño, true));
        }
    }

}
