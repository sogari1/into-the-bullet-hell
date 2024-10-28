package com.intothebullethell.game.objects.armas;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.intothebullethell.game.entidades.Proyectil;
import com.intothebullethell.sonido.EfectoSonidoArma;

public abstract class Arma {
    protected String nombre;
    protected float proyectilVelocidad;
    protected int daño;
    protected float ratioFuego;
    protected Texture proyectilTextura;
    protected Texture armaTextura;
    protected int capacidadMunicion;
    protected boolean municionInfinita;
    protected int balasEnReserva;
    protected int balasEnMunicion;
    private EfectoSonidoArma efectosSonido;
    

    public Arma(String nombre, float proyectilVelocidad, int daño, float ratioFuego, int capacidadMunicion, boolean municionInfinita, int balasEnReserva, Texture proyectilTextura, Texture armaTextura, EfectoSonidoArma efectosSonido) {
        this.nombre = nombre;
        this.proyectilVelocidad = proyectilVelocidad;
        this.daño = daño;
        this.ratioFuego = ratioFuego;
        this.capacidadMunicion = capacidadMunicion;
        this.municionInfinita = municionInfinita;
        this.balasEnReserva = balasEnReserva;
        this.balasEnMunicion = capacidadMunicion; 
        this.proyectilTextura = proyectilTextura;
        this.armaTextura = armaTextura;
        this.efectosSonido = efectosSonido;
    }

    public abstract void disparar(Vector2 position, Vector2 target, ArrayList<Proyectil> proyectiles);

    public boolean puedeDisparar() {
        return balasEnMunicion > 0 || municionInfinita;
    }

    public void reload() {
        if (!municionInfinita && balasEnReserva > 0) {
            int bulletsNeeded = capacidadMunicion - balasEnMunicion;
            int bulletsToReload = Math.min(bulletsNeeded, balasEnReserva);

            balasEnMunicion += bulletsToReload;
            balasEnReserva -= bulletsToReload;
        }
    }


    public void dispararProyectil(Vector2 position, Vector2 target, ArrayList<Proyectil> proyectiles) {
        if (puedeDisparar()) {
        	disparar(position, target, proyectiles);  
            if (!municionInfinita) {
            	balasEnMunicion--;  
            }
        }
        efectosSonido.reproducirDisparo();
    }


    public boolean esMunicionInfinita() {
        return municionInfinita;
    }

    public int getBalasEnMunicion() {
        return balasEnMunicion;
    }

    public int getBalasEnReserva() {
        return balasEnReserva;
    }

    public String getNombre() {
        return nombre;
    }

    public Texture getProyectilTextura() {
        return proyectilTextura;
    }

    public float getRatioFuego() {
		return ratioFuego;
	}

	public Texture getArmaTextura() {
        return armaTextura;
    }
}
