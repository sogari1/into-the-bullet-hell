package com.intothebullethell.game.mecanicas;

import com.intothebullethell.game.entidades.Jugador;

public class Tiempo extends Thread {
    private static int tiempo;
    private Jugador jugador;
    private boolean running;
    private boolean paused;

    public Tiempo(Jugador jugador) {
        Tiempo.tiempo = 30;
        this.jugador = jugador;
        this.running = true;
        this.paused = false;  
    }

    @Override
    public void run() {
        while (running) {
            if (!paused) { 
                try {
                    Thread.sleep(1000);
                    System.out.println("Aleatorizar arma en: " + tiempo + " segundos");
                    tiempo--;
                    if (tiempo < 0) {
                        jugador.cambiarArma();
                        tiempo = 30;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    running = false;
                }
            } else {
                try {
                    Thread.sleep(100);  
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static int getTiempo() {
        return tiempo;
    }

    public void detener() {
        running = false;
    }

    public void pausar() {
        paused = true;
    }

    public void reanudar() {
        paused = false;
    }
}

