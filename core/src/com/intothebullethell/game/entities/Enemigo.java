package com.intothebullethell.game.entities;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.graphics.Texture;

public class Enemigo extends Entidad {
    private float attackInterval = 5;
    private float attackTimer = 5;
    private ArrayList<Proyectil> proyectiles;
    private Jugador jugador;
    private ArrayList<Enemigo> enemigos;

    public Enemigo(Jugador jugador, ArrayList<Enemigo> enemigos) {
        super(new Texture("imagenes/enemigos/ENEMIGO.png"), 4, 10, new Texture("imagenes/objetos/armaProyectil/bala.png"));
        this.proyectiles = new ArrayList<>();
        this.jugador = jugador;
        this.enemigos = enemigos; // Asigna la lista de enemigos correctamente
    }


    @Override
    public void update(float delta) {
        attackTimer -= delta;
        if (attackTimer <= 0) {
            atacar(proyectiles);
            attackTimer = attackInterval;
        }


        Iterator<Proyectil> iterator = proyectiles.iterator();
        while (iterator.hasNext()) {
            Proyectil proyectil = iterator.next();
            proyectil.update(delta);

            if (proyectil.collidesWith(jugador)) {
            	jugador.recibirDaño(proyectil.getDaño());
                iterator.remove();
            }
        }

    }

    public ArrayList<Proyectil> getProjectiles() {
        return proyectiles;
    }
    
    @Override
    protected void remove() {
    	 enemigos.remove(this);
    }

    @Override
    public void atacar(ArrayList<Proyectil> projectil) {
//        Vector2 projectileStart = new Vector2(getX() + getWidth() / 2, getY() + getHeight() / 2);
//        Vector2 target = new Vector2(player.getX(), player.getY());
//        projectiles.add(new Projectile(projectileTexture, projectileStart, target, 200, 10));
    }


    @Override
    public void recibirDaño(float daño) {
        vida -= daño;
        if (vida <= 0) {
            remove();
        }
    }
}