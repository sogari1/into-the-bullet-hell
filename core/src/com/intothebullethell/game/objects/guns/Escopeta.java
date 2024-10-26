package com.intothebullethell.game.objects.guns;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;
import com.intothebullethell.game.entities.Proyectil;
import com.intothebullethell.game.managers.RecursoManager;
import com.intothebullethell.game.managers.SonidoManager;

public class Escopeta extends Arma {
    public Escopeta() {
        super("Escopeta", 150, 3, 1.5f, 8, false, 64, RecursoManager.PROYECTIL_ESCOPETA, RecursoManager.ARMA_ESCOPETA, SonidoManager.DISPARIO_ESCOPETA);
    }

    @Override
    public void disparar(Vector2 position, Vector2 target, ArrayList<Proyectil> proyectiles) {
        Vector2 direction = new Vector2(target).sub(position).nor();

        for (int i = -2; i <= 2; i++) {
            Vector2 spreadDirection = new Vector2(direction).rotateDeg(i * 5); // Ajusta el ángulo del spread 
            Vector2 spreadTarget = new Vector2(position).add(spreadDirection.scl(1000));
            
            proyectiles.add(new Proyectil(proyectilTextura, position, spreadTarget, proyectilVelocidad, daño, true));
        }
    }

}
