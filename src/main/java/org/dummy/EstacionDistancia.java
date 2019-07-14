package org.dummy;

import java.io.Serializable;

public class EstacionDistancia implements Serializable {

    private final String id;
    private final double distancia;
    private final double angulo;

    public EstacionDistancia(String id, double distancia, double angulo) {
        this.id = id;
        this.distancia = distancia;
        this.angulo = angulo;
    }

    public String getId() {
        return id;
    }

    public double getDistancia() {
        return distancia;
    }

    public double getAngulo() {
        return angulo;
    }
}
