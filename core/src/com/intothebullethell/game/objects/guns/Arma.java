package com.intothebullethell.game.objects.guns;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.intothebullethell.game.entities.Player;
import com.intothebullethell.game.entities.Projectile;

public abstract class Arma {
    protected String name;
    protected float projectileSpeed;
    protected int damage;
    protected float fireRate;
    protected Texture projectileTexture;
    protected Texture weaponTexture;
    protected int magazineCapacity;
    protected boolean infiniteAmmo;
    protected int bulletsInReserve;
    protected int bulletsInMagazine;
    private Player player;

    public Arma(String name, float projectileSpeed, int damage, float fireRate, int magazineCapacity, boolean infiniteAmmo, int bulletsInReserve, Texture projectileTexture, Texture weaponTexture) {
        this.name = name;
        this.projectileSpeed = projectileSpeed;
        this.damage = damage;
        this.fireRate = fireRate;
        this.magazineCapacity = magazineCapacity;
        this.infiniteAmmo = infiniteAmmo;
        this.bulletsInReserve = bulletsInReserve;
        this.bulletsInMagazine = magazineCapacity;  // El cargador comienza lleno
        this.projectileTexture = projectileTexture;
        this.weaponTexture = weaponTexture;
    }

    public abstract void shoot(Vector2 position, Vector2 target, ArrayList<Projectile> projectiles);

    public boolean canShoot() {
        return bulletsInMagazine > 0 || infiniteAmmo;
    }

    public void reload() {
        if (!infiniteAmmo && bulletsInReserve > 0) {
            int bulletsNeeded = magazineCapacity - bulletsInMagazine;
            int bulletsToReload = Math.min(bulletsNeeded, bulletsInReserve);

            bulletsInMagazine += bulletsToReload;
            bulletsInReserve -= bulletsToReload;
        }
    }


    public void shootProjectile(Vector2 position, Vector2 target, ArrayList<Projectile> projectiles) {
        if (canShoot()) {
            shoot(position, target, projectiles);  // Llama al método abstracto de la clase hija
            if (!infiniteAmmo) {
                bulletsInMagazine--;  // Decrementa las balas en el cargador
            }
        }
    }


    public boolean isInfiniteAmmo() {
        return infiniteAmmo;
    }

    public int getBulletsInMagazine() {
        return bulletsInMagazine;
    }

    public int getBulletsInReserve() {
        return bulletsInReserve;
    }

    public String getName() {
        return name;
    }

    public Texture getProjectileTexture() {
        return projectileTexture;
    }

    public float getFireRate() {
		return fireRate;
	}

	public Texture getWeaponTexture() {
        return weaponTexture;
    }
}
