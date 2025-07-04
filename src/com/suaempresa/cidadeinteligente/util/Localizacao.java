package com.suaempresa.cidadeinteligente.util;

public class Localizacao {
    private final double x, y;

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

    /**
     * Calcula a distância euclidiana (em linha reta) até outra localização.
     * @param outra A outra localização para a qual a distância será calculada.
     * @return A distância como um valor double.
     */
    public double distanciaPara(Localizacao outra) {
        double dx = this.x - outra.getX();
        double dy = this.y - outra.getY();
        return Math.sqrt(dx*dx + dy*dy);
    }

    @Override
    public String toString() {
        return "Localizacao{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}