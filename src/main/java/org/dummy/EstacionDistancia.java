package org.dummy;

import java.io.Serializable;

public class EstacionDistancia implements Serializable {

    private final String id;
    private final double distancia;
    private final double angulo;
    private final int quality;

    public EstacionDistancia(String id, double distancia, double angulo, int quality) {
        this.id = id;
        this.distancia = distancia;
        this.angulo = angulo;
        this.quality = quality;
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

    public int getQuality() {
        return quality;
    }
}
