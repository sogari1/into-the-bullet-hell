package com.intothebullethell.game.entities;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Enemy extends Entity {
    private float attackInterval = 5;
    private float attackTimer = 5;
    private ArrayList<Projectile> projectiles;
    private Player player;
    private ArrayList<Enemy> enemies;

    public Enemy(Player player, ArrayList<Enemy> enemies) {
        super(new Texture("imagenes/enemigos/ENEMIGO.png"), 4, 10, new Texture("imagenes/objetos/armaProyectil/BALA.png"));
        this.projectiles = new ArrayList<>();
        this.player = player;
        this.enemies = enemies; // Asigna la lista de enemigos correctamente
    }


    @Override
    public void update(float delta) {
        attackTimer -= delta;
        if (attackTimer <= 0) {
            attack(projectiles);
            attackTimer = attackInterval;
        }


        Iterator<Projectile> iterator = projectiles.iterator();
        while (iterator.hasNext()) {
            Projectile projectile = iterator.next();
            projectile.update(delta);

            if (projectile.collidesWith(player)) {
                player.takeDamage(projectile.getDamage());
                iterator.remove();
            }
        }

    }

    public ArrayList<Projectile> getProjectiles() {
        return projectiles;
    }
    
    @Override
    protected void remove() {
    	 enemies.remove(this);
    }

    @Override
    public void attack(ArrayList<Projectile> projectiles) {
//        Vector2 projectileStart = new Vector2(getX() + getWidth() / 2, getY() + getHeight() / 2);
//        Vector2 target = new Vector2(player.getX(), player.getY());
//        projectiles.add(new Projectile(projectileTexture, projectileStart, target, 200, 10));
    }


    @Override
    public void takeDamage(float damage) {
        health -= damage;
        if (health <= 0) {
            remove();
        }
    }
}