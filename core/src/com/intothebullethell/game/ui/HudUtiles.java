package com.intothebullethell.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.intothebullethell.game.objects.guns.Arma;

public class HudUtiles {
    public static void drawHearts(SpriteBatch batch, Texture fullHeart, Texture halfHeart, Texture emptyHeart, int currentHealth, int maxHealth, int x, int y) {
        int healthPerHeart = 2;
        int fullHearts = currentHealth / healthPerHeart;
        boolean hasHalfHeart = (currentHealth % healthPerHeart) > 0;
        int maxHearts = maxHealth / healthPerHeart;

        for (int i = 0; i < maxHearts; i++) {
            if (i < fullHearts) {
                batch.draw(fullHeart, x + i * fullHeart.getWidth(), y);
            } else if (hasHalfHeart && i == fullHearts) {
                batch.draw(halfHeart, x + i * fullHeart.getWidth(), y);
            } else {
                batch.draw(emptyHeart, x + i * fullHeart.getWidth(), y);
            }
        }
    }

    public static void drawWeaponInfo(SpriteBatch batch, Arma currentWeapon, BitmapFont font) {
        if (currentWeapon != null) {
            String ammoText = currentWeapon.esMunicionInfinita() ? 
                "Reserva: " + currentWeapon.getBalasEnReserva() + " / INF" :
                "Cargador: " + currentWeapon.getBalasEnMunicion() + " / Reserva: " + currentWeapon.getBalasEnReserva();
            
            font.draw(batch, ammoText, 10, Gdx.graphics.getHeight() - 10);
        }
    }
}
