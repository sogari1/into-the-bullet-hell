package com.intothebullethell.game.entities;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.intothebullethell.game.inputs.InputManager;
import com.intothebullethell.game.managers.ProyectilManager;
import com.intothebullethell.game.mecanicas.ArmaAleatoria;
import com.intothebullethell.game.objects.guns.Arma;
import com.intothebullethell.game.ui.Hud;

public class Jugador extends Entidad {
    private Vector2 velocity = new Vector2();
    private float shootTimer = 0;
    private Arma armaEquipada;
    private ArmaAleatoria armaAleatoria;
    private OrthographicCamera camara;
    private ArrayList<Proyectil> proyectiles;
    private ArrayList<Enemigo> enemigos;
    private boolean disparando = false;
    private Vector2 mousePosition = new Vector2();
    private TextureRegion upSprite, downSprite, leftSprite, rightSprite;
    private Hud hud;
    private int maxVida = vida;
    private InputManager inputManager;
    private ProyectilManager proyectilManager;

    public Jugador(TextureRegion sprite, TextureRegion upSprite, TextureRegion downSprite, TextureRegion leftSprite, TextureRegion rightSprite, OrthographicCamera camara, InputManager inputManager, TiledMapTileLayer collisionLayer) {
    	super(sprite.getTexture(), 10, 100, null, collisionLayer);
        this.upSprite = upSprite;
        this.downSprite = downSprite;
        this.leftSprite = leftSprite;
        this.rightSprite = rightSprite;
        this.proyectiles = new ArrayList<>();
        this.camara = camara;
        this.armaAleatoria = new ArmaAleatoria();
        this.armaEquipada = armaAleatoria.obtenerArmaAleatoria();
        this.inputManager = inputManager;
        this.inputManager.setJugador(this);
        this.proyectilManager = new ProyectilManager();
    }

    @Override
    public void draw(Batch batch) {
        update(Gdx.graphics.getDeltaTime());
        super.draw(batch);
        proyectilManager.draw(batch); 
    }

    public void dispose() {
        for (Proyectil proyectil : proyectiles) {
            proyectil.getTexture().dispose();
        }
    }

    @Override
    public void update(float delta) {
        actualizarMovimiento();
        manejarDisparos(delta);
        actualizarSprite();
        proyectilManager.actualizarProyectiles(delta, enemigos, this);
        actualizarCamara();
    }

    private void actualizarMovimiento() {
        velocity.set(0, 0);
        if (inputManager.upPressed && !inputManager.downPressed) {
            velocity.y = velocidad;  
        } else if (!inputManager.upPressed && inputManager.downPressed) {
            velocity.y = -velocidad; 
        }

        if (inputManager.leftPressed && !inputManager.rightPressed) {
            velocity.x = -velocidad;
        } else if (inputManager.rightPressed && !inputManager.leftPressed) {
            velocity.x = velocidad; 
        }
        mover(Gdx.graphics.getDeltaTime(), velocity);
    }

    private void manejarDisparos(float delta) {
        if (disparando) {
            shootTimer -= delta;
            if (shootTimer <= 0) {
            	proyectilManager.dispararProyectil(camara, armaEquipada, getX() + getWidth() / 2, getY() + getHeight() / 2, Gdx.input.getX(), Gdx.input.getY());
                shootTimer = armaEquipada.getRatioFuego(); 
            }
        }
    }
    private void actualizarCamara() {
        camara.position.set(getX() + getWidth() / 2, getY() + getHeight() / 2, 0);
        camara.update();
    }

    private void actualizarSprite() {
        Vector2 jugadorCentro = new Vector2(getX() + getWidth() / 2, getY() + getHeight() / 2);
        Vector3 mouseWorldPos3 = camara.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
        Vector2 mouseWorldPos = new Vector2(mouseWorldPos3.x, mouseWorldPos3.y);
        Vector2 direction = mouseWorldPos.sub(jugadorCentro).nor();
        float angulo = direction.angleDeg();

        if (angulo >= 45 && angulo < 135) {
            setRegion(upSprite);  // Arriba
        } else if (angulo >= 135 && angulo < 225) {
            setRegion(leftSprite);  // Izquierda
        } else if (angulo >= 225 && angulo < 315) {
            setRegion(downSprite);  // Abajo
        } else {
            setRegion(rightSprite);  // Derecha
        }
    }

    public void recargarArma() {
        if (armaEquipada != null) {
            armaEquipada.reload();
        }
    }

    public void cambiarArma() {
        this.armaEquipada = armaAleatoria.obtenerArmaAleatoria();
        hud.updateWeaponSprite(); 
    }

    public void setDisparando(boolean disparando) { 
    	this.disparando = disparando; 
    }
    public boolean isDisparando() {
        return disparando;
    }
    public void setMousePosition(int screenX, int screenY) {
        mousePosition.set(screenX, screenY);
        mousePosition = mousePosition.scl(1, -1).add(0, Gdx.graphics.getHeight());
    }
    public void setEnemies(ArrayList<Enemigo> enemigos) { 
    	this.enemigos = enemigos; 
    }
    public Arma getArmaEquipada() { 
    	return armaEquipada; 
    }
    public void setArma(Arma arma) {
        this.armaEquipada = arma;
        if (hud != null) {
            hud.updateWeaponSprite();
        }
    }
    public int getMaxVida() {
		return maxVida;
	}

	public void setHud(Hud hud) { 
    	this.hud = hud; 
    }
    public float getShootTimer() {
        return shootTimer;
    }
    public void setShootTimer(float shootTimer) {
        this.shootTimer = shootTimer;
    }
    @Override
    protected void remove() {
    }

    public Texture getArmaTextura() {
    	return armaEquipada.getArmaTextura();
    }
    public void setProyectilManager(ProyectilManager proyectilManager) {
        this.proyectilManager = proyectilManager;
    }

    public ProyectilManager getProyectilManager() {
        return proyectilManager;
    }
}
