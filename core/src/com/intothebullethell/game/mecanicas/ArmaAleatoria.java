package com.intothebullethell.game.mecanicas;

import java.util.ArrayList;
import java.util.Random;

import com.intothebullethell.game.objects.armas.Arma;
import com.intothebullethell.game.objects.armas.Escopeta;
import com.intothebullethell.game.objects.armas.Pistola;

public class ArmaAleatoria {
    private ArrayList<Arma> armas;
    private Random random;

    public ArmaAleatoria() {
        this.armas = new ArrayList<>();
        this.random = new Random();
        inicializarArmas();
    }

    private void inicializarArmas() {
        armas.add(new Pistola());
        armas.add(new Escopeta());
    }

    public Arma obtenerArmaAleatoria() {
        return armas.get(random.nextInt(armas.size()));
    }
}
