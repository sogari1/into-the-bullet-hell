package com.intothebullethell.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.intothebullethell.game.objects.armas.Arma;

public class HudUtiles {
	
	public static void drawHearts(SpriteBatch batch, Texture fullHeart, Texture halfHeart, Texture emptyHeart, int maxHealth, int currentHealth, int x, int y) {
	    int healthPerHeart = 2;
	    int maxHearts = maxHealth / healthPerHeart;
	    int fullHearts = currentHealth / healthPerHeart;
	    boolean hasHalfHeart = (currentHealth % healthPerHeart) > 0;

	    int heartsPerRow = 5;
	    int rowSpacing = fullHeart.getHeight() + 5; 

	    for (int i = 0; i < maxHearts; i++) {
	        int row = i / heartsPerRow; // Fila actual
	        int col = i % heartsPerRow; // Columna actual

	        int posX = x + col * fullHeart.getWidth();
	        int posY = y - row * rowSpacing;

	        if (i < fullHearts) {
	            batch.draw(fullHeart, posX, posY);
	        } else if (hasHalfHeart && i == fullHearts) {
	            batch.draw(halfHeart, posX, posY);
	        } else {
	            batch.draw(emptyHeart, posX, posY);
	        }
	    }
	}


	public static void drawWeaponInfo(SpriteBatch batch, Arma currentWeapon, Texto ammoTexto) {
	    if (currentWeapon != null) {
	        String ammoText = currentWeapon.esMunicionInfinita() ? 
	            "Reserva: " + currentWeapon.getBalasEnReserva() + " / INF" :
	            "Cargador: " + currentWeapon.getBalasEnMunicion() + " / Reserva: " + currentWeapon.getBalasEnReserva();
	        
	        ammoTexto.setText(ammoText);
	        ammoTexto.setPosition(10, Gdx.graphics.getHeight() - 850);

	        ammoTexto.draw(batch);
	    }
	}

}
