package com.intothebullethell.game.managers;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.intothebullethell.game.entidades.Enemigo;
import com.intothebullethell.game.entidades.Jugador;
import com.intothebullethell.game.entidades.Proyectil;
import com.intothebullethell.game.objects.armas.Arma;

import java.util.ArrayList;

public class ProyectilManager {
    public ArrayList<Proyectil> proyectiles;
    private TileColisionManager tileColisionManager;

    public ProyectilManager(TileColisionManager tileColisionManager) {
        this.proyectiles = new ArrayList<>();
        this.tileColisionManager = tileColisionManager;
    }

    public void agregarProyectil(Proyectil proyectil) {
        proyectiles.add(proyectil);
    }

    public void actualizarProyectiles(float delta, ArrayList<Enemigo> enemigos, Jugador jugador) {
        for (int i = proyectiles.size() - 1; i >= 0; i--) {
            Proyectil proyectil = proyectiles.get(i);
            proyectil.update(delta);
            
            if (chequearColisionProyectil(proyectil, enemigos, jugador) || tileColisionManager.esColision(proyectil.getBoundingRectangle())) {
                proyectiles.remove(i); 
            }
        }
    }
    private boolean chequearColisionProyectil(Proyectil proyectil, ArrayList<Enemigo> enemigos, Jugador jugador) {
        for (Enemigo enemigo : enemigos) {
            if (proyectil.collidesWith(enemigo) && proyectil.isDisparadoPorJugador()) {
                enemigo.recibirDaño(proyectil.getDaño());
//                System.out.println(enemigo.getClass().getSimpleName() + "recibio daño");
                return true;
            }
        }
        if (proyectil.collidesWith(jugador) && !proyectil.isDisparadoPorJugador()) {
            jugador.recibirDaño(proyectil.getDaño());
//            System.out.println("Jugador vida: " + jugador.getVidaActual());
            return true;
        }
        return false;
    }
    public void dispararProyectil(OrthographicCamera camara, Arma arma, float jugadorX, float jugadorY, int screenX, int screenY) {
        if (arma.puedeDisparar()) {
            Vector3 unprojected = camara.unproject(new Vector3(screenX, screenY, 0));
            Vector2 target = new Vector2(unprojected.x, unprojected.y);
            Vector2 position = new Vector2(jugadorX, jugadorY);

            arma.disparar(position, target, proyectiles);

            if (!arma.esMunicionInfinita()) {
                arma.dispararProyectil(position, target, proyectiles);
            }
        }
    }
    public void draw(Batch batch) {
        for (Proyectil proyectil : proyectiles) {
            proyectil.draw(batch);
        }
    }
    public void dispose() {
        for (Proyectil proyectil : proyectiles) {
            proyectil.getTexture().dispose();
        }
    }
}
