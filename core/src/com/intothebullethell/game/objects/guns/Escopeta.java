package com.intothebullethell.game.objects.guns;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.intothebullethell.game.entities.Proyectil;
import com.intothebullethell.sound.EfectoSonidoArma;

public class Escopeta extends Arma {
    public Escopeta() {
        super("Escopeta", 150, 15, 1.5f, 8, false, 64, new Texture("imagenes/objetos/armaProyectil/pistola_projectil.png"), new Texture("imagenes/objetos/armas/escopeta.png"), new EfectoSonidoArma("sonidos/armas/Escopeta.wav"));
    }

    @Override
    public void disparar(Vector2 position, Vector2 target, ArrayList<Proyectil> proyectiles) {
        // Escopeta dispara múltiples proyectiles en un abanico
        for (int i = -2; i <= 2; i++) {
            // Puedes ajustar los ángulos o el rango según el diseño del abanico
        	Vector2 spreadTarget = new Vector2(target).add(new Vector2(i * 30, 0)); // Ajusta el spread aquí
        	proyectiles.add(new Proyectil(projectilTextura, position, spreadTarget, projectilVelocidad, daño));
        }
        
    }
}
