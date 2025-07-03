package com.suaempresa.cidadeinteligente.util;

/*
* Classe com o intuito de localizar objetos na nossa cidade, como as lixeiras eletrônicas espalhadas
* */

public class Localizacao {
    private final double x, y; // coordenadas de algum ponto na cidade.

    public Localizacao(double x, double y){
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public String toString() {
        return "Localizacao{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
