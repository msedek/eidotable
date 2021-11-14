package com.eidotab.eidotab.Model;

import java.io.Serializable;



public class Tablet implements Serializable
{
    private String nro_tablet;
    private String nro_mesa;
    private String nro_mesero;

    public String getNro_tablet() {
        return nro_tablet;
    }

    public void setNro_tablet(String nro_tablet) {
        this.nro_tablet = nro_tablet;
    }

    public String getNro_mesa() {
        return nro_mesa;
    }

    public void setNro_mesa(String nro_mesa) {
        this.nro_mesa = nro_mesa;
    }

    public String getNro_mesero() {
        return nro_mesero;
    }

    public void setNro_mesero(String nro_mesero) {
        this.nro_mesero = nro_mesero;
    }
}
