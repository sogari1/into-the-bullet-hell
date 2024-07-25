package com.intothebullethell.game.entities;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.intothebullethell.game.objects.Arma;
import com.intothebullethell.game.objects.Escopeta;
import com.intothebullethell.game.objects.Pistola;

public class Player extends Sprite implements InputProcessor {
    private Vector2 velocity = new Vector2();
    private float speed = 60 * 2;
    private TiledMapTileLayer collisionLayer;
    private String blockKey = "bloque";
    private boolean upPressed, downPressed, leftPressed, rightPressed;
    private TextureRegion upSprite, downSprite, leftSprite, rightSprite;
    private Vector2 mousePosition = new Vector2();
    private OrthographicCamera camera;
    private ArrayList<Projectile> projectiles;
    private boolean shooting;
    private Arma currentWeapon;
    private float shootTimer;

    public Player(Sprite sprite, TiledMapTileLayer collisionLayer, TextureRegion upSprite, TextureRegion downSprite, TextureRegion leftSprite, TextureRegion rightSprite, OrthographicCamera camera) {
        super(sprite);
        this.collisionLayer = collisionLayer;
        this.upSprite = upSprite;
        this.downSprite = downSprite;
        this.leftSprite = leftSprite;
        this.rightSprite = rightSprite;
        this.projectiles = new ArrayList<>();
        this.camera = camera;
        this.shooting = false;
        this.currentWeapon = new Pistola(); // Inicialmente con una pistola
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

    public void update(float delta) {
        float oldX = getX(), oldY = getY();
        boolean collisionX = false, collisionY = false;

        if (shooting) {
            shootTimer -= delta;
            if (shootTimer <= 0) {
                shootProjectile(Gdx.input.getX(), Gdx.input.getY());
                shootTimer = currentWeapon.getFireRate(); // Reinicia el temporizador
            }
        }
        
        // Moverse en X
        setX(getX() + velocity.x * delta);
        if (velocity.x < 0) { // Moverse a la izquierda
            collisionX = isCellBlocked(getX(), getY() + getHeight()) ||
                         isCellBlocked(getX(), getY() + getHeight() / 2) ||
                         isCellBlocked(getX(), getY());
        } else if (velocity.x > 0) { // Moverse a la derecha
            collisionX = isCellBlocked(getX() + getWidth(), getY() + getHeight()) ||
                         isCellBlocked(getX() + getWidth(), getY() + getHeight() / 2) ||
                         isCellBlocked(getX() + getWidth(), getY());
        }

        // Reaccionar a colisión X
        if (collisionX) {
            setX(oldX);
            velocity.x = 0;
        }

        // Moverse en Y
        setY(getY() + velocity.y * delta);
        if (velocity.y < 0) { // Moverse hacia abajo
            collisionY = isCellBlocked(getX(), getY()) ||
                         isCellBlocked(getX() + getWidth() / 2, getY()) ||
                         isCellBlocked(getX() + getWidth(), getY());
        } else if (velocity.y > 0) { // Moverse hacia arriba
            collisionY = isCellBlocked(getX(), getY() + getHeight()) ||
                         isCellBlocked(getX() + getWidth() / 2, getY() + getHeight()) ||
                         isCellBlocked(getX() + getWidth(), getY() + getHeight());
        }

        // Reaccionar a colisión Y
        if (collisionY) {
            setY(oldY);
            velocity.y = 0;
        }

        updateSprite();

        // Actualizar los proyectiles
        Iterator<Projectile> iterator = projectiles.iterator();
        while (iterator.hasNext()) {
            Projectile projectile = iterator.next();
            projectile.update(delta);
        }
    }

    private boolean isCellBlocked(float x, float y) {
        Cell cell = collisionLayer.getCell((int) (x / collisionLayer.getTileWidth()), (int) (y / collisionLayer.getTileHeight()));
        return cell != null && cell.getTile() != null && cell.getTile().getProperties().containsKey(blockKey);
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

    public TiledMapTileLayer getCollisionLayer() {
        return collisionLayer;
    }

    public void setCollisionLayer(TiledMapTileLayer collisionLayer) {
        this.collisionLayer = collisionLayer;
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
	public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
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

    private void shootProjectile(int screenX, int screenY) {
        Vector3 unprojected = camera.unproject(new Vector3(screenX, screenY, 0));
        Vector2 target = new Vector2(unprojected.x, unprojected.y);
        Vector2 position = new Vector2(getX() + getWidth() / 2, getY() + getHeight() / 2);
        currentWeapon.shoot(position, target, projectiles);
    }

    public void setWeapon(Arma weapon) {
        this.currentWeapon = weapon;
    }

}

