package com.intothebullethell.game.mecanicas;

import java.util.ArrayList;
import java.util.Random;

import com.intothebullethell.game.objects.armas.Arma;
import com.intothebullethell.game.objects.armas.Escopeta;
import com.intothebullethell.game.objects.armas.Pistola;

public class ArmaAleatoria {
    private ArrayList<Arma> listaArmas;
    private Random random;

    public ArmaAleatoria() {
        this.listaArmas = new ArrayList<>();
        this.random = new Random();
        inicializarArmas();
    }

    private void inicializarArmas() {
    	listaArmas.add(new Pistola());
//        armas.listaArmas(new Escopeta());
    }

    public Arma obtenerArmaAleatoria() {
        return listaArmas.get(random.nextInt(listaArmas.size()));
    }
}
