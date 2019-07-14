package org.dummy;

import java.io.Serializable;

public final class EstacionesUbicacion implements Serializable {

    private final String id;
    private final double latitude;
    private final double longitude;

    public EstacionesUbicacion(String id, double latitude, double longitude) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getId() {
        return id;
    }

    public double getLat() {
        return latitude;
    }

    public double getLon() {
        return longitude;
    }
}
