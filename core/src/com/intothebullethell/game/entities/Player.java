package com.intothebullethell.game.entities;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.intothebullethell.game.objects.guns.Arma;
import com.intothebullethell.game.objects.guns.Escopeta;
import com.intothebullethell.game.objects.guns.Pistola;
import com.intothebullethell.game.screens.HUD;

public class Player extends Entity implements InputProcessor {
    private Vector2 velocity = new Vector2();
    private float shootTimer;
    private Arma currentWeapon;
    private OrthographicCamera camera;
    private ArrayList<Projectile> projectiles;
    private ArrayList<Enemy> enemies;
    private boolean shooting;
    private Vector2 mousePosition = new Vector2();
    private boolean upPressed, downPressed, leftPressed, rightPressed;
    private TextureRegion upSprite, downSprite, leftSprite, rightSprite;
    private HUD hud;
    private int maxHealth = health;

    public Player(TextureRegion sprite, TiledMapTileLayer collisionLayer, TextureRegion upSprite, TextureRegion downSprite, TextureRegion leftSprite, TextureRegion rightSprite, OrthographicCamera camera) {
        super(sprite.getTexture(), 10, 100, null); // El último parámetro debe ser la textura del proyectil
        setCollisionLayer(collisionLayer);
        this.upSprite = upSprite;
        this.downSprite = downSprite;
        this.leftSprite = leftSprite;
        this.rightSprite = rightSprite;
        this.projectiles = new ArrayList<>();
        this.camera = camera;
        this.shooting = false;
        this.currentWeapon = new Pistola(); 
        this.shootTimer = 0;
    }

	@Override
    public void draw(Batch batch) {
        update(Gdx.graphics.getDeltaTime());
        super.draw(batch);
        for (Projectile projectile : projectiles) {
            projectile.draw(batch);
        }
    }
	public void dispose() {
        for (Projectile projectile : projectiles) {
            projectile.getTexture().dispose();
        }
    }

    @Override
    public void update(float delta) {
        updateMovement();
        handleShooting(delta);
        updateSprite();
        updateProjectiles(delta);
    }
    private void updateMovement() {
        velocity.set(0, 0);
        if (upPressed) velocity.y = speed;
        if (downPressed) velocity.y = -speed;
        if (leftPressed) velocity.x = -speed;
        if (rightPressed) velocity.x = speed;
        move(Gdx.graphics.getDeltaTime(), velocity);
    }
    private void handleShooting(float delta) {
        if (shooting) {
            shootTimer -= delta;
            if (shootTimer <= 0) {
                shootProjectile(Gdx.input.getX(), Gdx.input.getY());
                shootTimer = currentWeapon.getFireRate();
            }
        }
    }

    private void updateProjectiles(float delta) {
        Iterator<Projectile> iterator = projectiles.iterator();
        while (iterator.hasNext()) {
            Projectile projectile = iterator.next();
            projectile.update(delta);
            checkProjectileCollision(projectile, iterator);
        }
    }

    private void checkProjectileCollision(Projectile projectile, Iterator<Projectile> iterator) {
        for (Enemy enemy : enemies) {
            if (projectile.collidesWith(enemy)) {
                enemy.takeDamage(projectile.getDamage());
                iterator.remove();
                break;
            }
        }
    }
    public Vector2 getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }
    public void setEnemies(ArrayList<Enemy> enemies) {
        this.enemies = enemies;
    }
    private void updateSprite() {
        Vector2 playerCenter = new Vector2(getX() + getWidth() / 2, getY() + getHeight() / 2);
        Vector3 mouseWorldPos3 = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
        Vector2 mouseWorldPos = new Vector2(mouseWorldPos3.x, mouseWorldPos3.y);
        Vector2 direction = mouseWorldPos.sub(playerCenter).nor();
        float angle = direction.angleDeg();

        if (angle >= 45 && angle < 135) {
            setRegion(upSprite);  // Arriba
        } else if (angle >= 135 && angle < 225) {
            setRegion(leftSprite);  // Izquierda
        } else if (angle >= 225 && angle < 315) {
            setRegion(downSprite);  // Abajo
        } else {
            setRegion(rightSprite);  // Derecha
        }
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Keys.W:
                upPressed = true;
                break;
            case Keys.A:
                leftPressed = true;
                break;
            case Keys.D:
                rightPressed = true;
                break;
            case Keys.S:
                downPressed = true;
                break;
            case Keys.NUM_1:
                setWeapon(new Pistola());
                break;
            case Keys.NUM_2:
                setWeapon(new Escopeta());
                break;
            case Keys.R:
                if (currentWeapon != null) {
                    currentWeapon.reload();
                }
                break;
        }
        updateVelocity();
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Keys.W:
                upPressed = false;
                break;
            case Keys.A:
                leftPressed = false;
                break;
            case Keys.D:
                rightPressed = false;
                break;
            case Keys.S:
                downPressed = false;
                break;
        }
        updateVelocity();
        return true;
    }


    private void updateVelocity() {
        velocity.x = 0;
        velocity.y = 0;

        if (upPressed && !downPressed) {
            velocity.y = speed;
        } else if (!upPressed && downPressed) {
            velocity.y = -speed;
        }

        if (leftPressed && !rightPressed) {
            velocity.x = -speed;
        } else if (!leftPressed && rightPressed) {
            velocity.x = speed;
        }
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }
    public void shoot(Vector2 position, Vector2 target, ArrayList<Projectile> projectiles) {
        if (currentWeapon != null) {
            currentWeapon.shootProjectile(position, target, projectiles);  // Usa el método de `Arma` que gestiona la munición
        }
    }
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (button == Input.Buttons.LEFT) {
            if (shootTimer <= 0) {
                shootProjectile(screenX, screenY);
                shootTimer = currentWeapon.getFireRate(); // Reinicia el temporizador
            }
            shooting = true;
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (button == Input.Buttons.LEFT) {
            shooting = false;
        }
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (shooting && shootTimer <= 0) {
            shootProjectile(screenX, screenY);
            shootTimer = currentWeapon.getFireRate(); // Reinicia el temporizador
        }
        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        mousePosition.set(screenX, screenY);
        mousePosition = mousePosition.scl(1, -1).add(0, Gdx.graphics.getHeight());
        return true;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

    public void shootProjectile(int screenX, int screenY) {
        if (currentWeapon.canShoot()) {  // Verifica si se puede disparar
            Vector3 unprojected = camera.unproject(new Vector3(screenX, screenY, 0));
            Vector2 target = new Vector2(unprojected.x, unprojected.y);
            Vector2 position = new Vector2(getX() + getWidth() / 2, getY() + getHeight() / 2);
            
            // Dispara el proyectil usando el arma actual
            currentWeapon.shoot(position, target, projectiles);
            
            if (!currentWeapon.isInfiniteAmmo()) {
                // Resta una bala del cargador después de disparar
                currentWeapon.shootProjectile(position, target, projectiles);
            }
        }
    }



    public Arma getCurrentWeapon() {
        return currentWeapon;
    }
    public void reloadWeapon() {
        if (currentWeapon != null) {
            currentWeapon.reload();
        }
    }

    public void setWeapon(Arma weapon) {
        this.currentWeapon = weapon;
        if (hud != null) {
            hud.updateWeaponSprite();
        }
    }
    public void setHUD(HUD hud) {
        this.hud = hud;
    }
    public Texture getWeaponTexture() {
        return currentWeapon.getWeaponTexture();
    }
	@Override
	public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public void attack(ArrayList<Projectile> projectiles) {
	}

	@Override
	protected void remove() {
	}

    public int getMaxHealth() {
		return maxHealth;
	}
}
