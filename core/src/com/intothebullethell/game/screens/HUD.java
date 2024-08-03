package com.intothebullethell.game.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.intothebullethell.game.entities.Player;
import com.intothebullethell.game.objects.guns.Arma;

public class HUD {
    private SpriteBatch batch;
    private Stage stage;
    private Texture fullHeart, halfHeart, blankHeart;
    private Player player;
    private Texture weaponSprite;
    private int playerHealth;
    private BitmapFont font;

    public HUD(SpriteBatch spriteBatch, Player player) {
        this.stage = new Stage(new ScreenViewport(), spriteBatch);
        this.player = player;
        this.batch = spriteBatch;
        fullHeart = new Texture("imagenes/otros/hud/fullHeart.png");
        halfHeart = new Texture("imagenes/otros/hud/halfHeart.png");
        blankHeart = new Texture("imagenes/otros/hud/blankHeart.png");
        font = new BitmapFont(); // Fuente para dibujar texto
        updateWeaponSprite();
    }

    public void draw() {
        stage.draw();
    }

    public void render() {
        batch.begin();
        drawHearts();
        drawWeapon();
        drawAmmo();  // Dibuja la información de munición
        batch.end();
    }

    private void drawHearts() {
        int maxHealth = player.getMaxHealth(); // Método para obtener la salud máxima
        playerHealth = player.getHealth();     // Método para obtener la salud actual
        int healthPerHeart = 2;  // Ajusta según sea necesario

        int maxHearts = maxHealth / healthPerHeart; // Total de corazones que se deben mostrar
        int fullHearts = playerHealth / healthPerHeart; // Número de corazones completos
        boolean hasHalfHeart = (playerHealth % healthPerHeart) > 0; // Si hay una fracción de corazón

        int x = Gdx.graphics.getWidth() - (maxHearts * fullHeart.getWidth()) - 10;
        int y = Gdx.graphics.getHeight() - fullHeart.getHeight() - 10;

        for (int i = 0; i < maxHearts; i++) {
            if (i < fullHearts) {
                // Dibuja un corazón completo
                batch.draw(fullHeart, x + i * fullHeart.getWidth(), y);
            } else if (hasHalfHeart && i == fullHearts) {
                // Dibuja un corazón a la mitad
                batch.draw(halfHeart, x + i * fullHeart.getWidth(), y);
            } else {
                // Dibuja un corazón vacío
                batch.draw(blankHeart, x + i * fullHeart.getWidth(), y);
            }
        }
    }

    private void drawWeapon() {
        if (weaponSprite != null) {
            batch.draw(weaponSprite, Gdx.graphics.getWidth() - weaponSprite.getWidth(), 0);
        }
    }

    private void drawAmmo() {
        Arma currentWeapon = player.getCurrentWeapon(); // Obtén el arma actual del jugador
        if (currentWeapon != null) {
            String ammoText;
            if (currentWeapon.isInfiniteAmmo()) {
                ammoText = "Reserva: " + currentWeapon.getBulletsInReserve() + " / INF";
            } else {
                ammoText = "Cargador: " + currentWeapon.getBulletsInMagazine() + " / Reserva: " + currentWeapon.getBulletsInReserve();
            }
            font.draw(batch, ammoText, 10, Gdx.graphics.getHeight() - 10); // Ajusta la posición del texto
        }
    }

    public void dispose() {
        fullHeart.dispose();
        halfHeart.dispose();
        blankHeart.dispose();
        stage.dispose();
        font.dispose();
    }

    public void updateWeaponSprite() {
        if (player != null) {
            weaponSprite = player.getWeaponTexture();
        }
    }

    public void updatePlayerHealth() {
        if (player != null) {
            playerHealth = player.getHealth();
        }
    }

    public void setPlayerHealth(int health) {
        this.playerHealth = health;
    }
}
