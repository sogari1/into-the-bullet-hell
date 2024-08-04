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
import com.intothebullethell.game.objects.guns.Pistola;
import com.intothebullethell.game.objects.guns.Escopeta;
import com.intothebullethell.game.screens.HUD;

public class Jugador extends Entidad implements InputProcessor {
    private Vector2 velocity = new Vector2();
    private float shootTimer;
    private Arma armaEquipada;
    private OrthographicCamera camera;
    private ArrayList<Proyectil> proyectiles;
    private ArrayList<Enemigo> enemigos;
    private boolean shooting;
    private Vector2 mousePosition = new Vector2();
    private boolean upPressed, downPressed, leftPressed, rightPressed;
    private TextureRegion upSprite, downSprite, leftSprite, rightSprite;
    private HUD hud;
    private int maxVida = vida;

    public Jugador(TextureRegion sprite, TiledMapTileLayer collisionLayer, TextureRegion upSprite, TextureRegion downSprite, TextureRegion leftSprite, TextureRegion rightSprite, OrthographicCamera camera) {
        super(sprite.getTexture(), 10, 100, null); // El último parámetro debe ser la textura del proyectil
        setCollisionLayer(collisionLayer);
        this.upSprite = upSprite;
        this.downSprite = downSprite;
        this.leftSprite = leftSprite;
        this.rightSprite = rightSprite;
        this.proyectiles = new ArrayList<>();
        this.camera = camera;
        this.shooting = false;
        this.armaEquipada = new Pistola(); 
        this.shootTimer = 0;
    }

	@Override
    public void draw(Batch batch) {
        update(Gdx.graphics.getDeltaTime());
        super.draw(batch);
        for (Proyectil proyectil : proyectiles) {
            proyectil.draw(batch);
        }
    }
	public void dispose() {
        for (Proyectil proyectil : proyectiles) {
            proyectil.getTexture().dispose();
        }
    }

    @Override
    public void update(float delta) {
        updateMovement();
        handleShooting(delta);
        updateSprite();
        updateProyectiles(delta);
    }
    private void updateMovement() {
        velocity.set(0, 0);
        if (upPressed) velocity.y = velocidad;
        if (downPressed) velocity.y = -velocidad;
        if (leftPressed) velocity.x = -velocidad;
        if (rightPressed) velocity.x = velocidad;
        move(Gdx.graphics.getDeltaTime(), velocity);
    }
    private void handleShooting(float delta) {
        if (shooting) {
            shootTimer -= delta;
            if (shootTimer <= 0) {
            	dispararProyectil(Gdx.input.getX(), Gdx.input.getY());
                shootTimer = armaEquipada.getRatioFuego();
            }
        }
    }

    private void updateProyectiles(float delta) {
        Iterator<Proyectil> iterator = proyectiles.iterator();
        while (iterator.hasNext()) {
            Proyectil proyectile = iterator.next();
            proyectile.update(delta);
            checkProyectileCollision(proyectile, iterator);
        }
    }

    private void checkProyectileCollision(Proyectil projectile, Iterator<Proyectil> iterator) {
        for (Enemigo enemigo : enemigos) {
            if (projectile.collidesWith(enemigo)) {
            	enemigo.recibirDaño(projectile.getDaño());
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

    public float getVelocidad() {
        return velocidad;
    }

    public void setSpeed(float velocidad) {
        this.velocidad = velocidad;
    }
    public void setEnemies(ArrayList<Enemigo> enemigos) {
        this.enemigos = enemigos;
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
                setArma(new Pistola());
                break;
            case Keys.NUM_2:
            	setArma(new Escopeta());
                break;
            case Keys.R:
                if (armaEquipada != null) {
                	armaEquipada.reload();
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
            velocity.y = velocidad;
        } else if (!upPressed && downPressed) {
            velocity.y = -velocidad;
        }

        if (leftPressed && !rightPressed) {
            velocity.x = -velocidad;
        } else if (!leftPressed && rightPressed) {
            velocity.x = velocidad;
        }
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }
    public void shoot(Vector2 position, Vector2 target, ArrayList<Proyectil> projectiles) {
        if (armaEquipada != null) {
        	armaEquipada.dispararProyectil(position, target, projectiles);  // Usa el método de `Arma` que gestiona la munición
        }
    }
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (button == Input.Buttons.LEFT) {
            if (shootTimer <= 0) {
            	dispararProyectil(screenX, screenY);
                shootTimer = armaEquipada.getRatioFuego(); // Reinicia el temporizador
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
        	dispararProyectil(screenX, screenY);
            shootTimer = armaEquipada.getRatioFuego(); // Reinicia el temporizador
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

    public void dispararProyectil(int screenX, int screenY) {
        if (armaEquipada.puedeDisparar()) {  // Verifica si se puede disparar
            Vector3 unprojected = camera.unproject(new Vector3(screenX, screenY, 0));
            Vector2 target = new Vector2(unprojected.x, unprojected.y);
            Vector2 position = new Vector2(getX() + getWidth() / 2, getY() + getHeight() / 2);
            
            // Dispara el proyectil usando el arma actual
            armaEquipada.disparar(position, target, proyectiles);
            
            if (!armaEquipada.esMunicionInfinita()) {
                // Resta una bala del cargador después de disparar
            	armaEquipada.dispararProyectil(position, target, proyectiles);
            }
        }
    }



    public Arma getArmaEquipada() {
        return armaEquipada;
    }
    public void recargarArma() {
        if (armaEquipada != null) {
        	armaEquipada.reload();
        }
    }

    public void setArma(Arma arma) {
        this.armaEquipada = arma;
        if (hud != null) {
            hud.updateWeaponSprite();
        }
    }
    public void setHUD(HUD hud) {
        this.hud = hud;
    }
    public Texture getArmaTextura() {
        return armaEquipada.getArmaTextura();
    }
	@Override
	public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public void atacar(ArrayList<Proyectil> Proyectiles) {
	}

	@Override
	protected void remove() {
	}

    public int getMaxVida() {
		return maxVida;
	}
}
