package com.intothebullethell.game.mecanicas;

import java.util.ArrayList;
import java.util.Random;

import com.intothebullethell.game.objects.guns.Arma;
import com.intothebullethell.game.objects.guns.Escopeta;
import com.intothebullethell.game.objects.guns.Pistola;

public class ArmaAleatoria {
    private ArrayList<Arma> armas;
    private Random random;

    public ArmaAleatoria() {
        armas = new ArrayList<>();
        random = new Random();
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
