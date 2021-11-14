package com.eidotab.eidotab.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class DataRoot implements Serializable, Comparable<DataRoot>
{


    private String _id;
    private String mesa;
    private String nro_comanda;
    private String estado_orden;
    private String estado_pcuenta;
    private String estado_pago;
    private ArrayList<Itemorder> orden;
    private Date fechaorden;
    private String idinterno;


    public String get_id() {
        return _id;
    }


    public String getMesa() {
        return mesa;
    }

    public void setMesa(String mesa) {
        this.mesa = mesa;
    }

    public String getNro_comanda() {
        return nro_comanda;
    }

    public void setNro_comanda(String nro_comanda) {
        this.nro_comanda = nro_comanda;
    }

    public String getEstado_orden() {
        return estado_orden;
    }

    public void setEstado_orden(String estado_orden) {
        this.estado_orden = estado_orden;
    }

    public String getEstado_pcuenta() {
        return estado_pcuenta;
    }

    public void setEstado_pcuenta(String estado_pcuenta) {
        this.estado_pcuenta = estado_pcuenta;
    }

    public String getEstado_pago() {
        return estado_pago;
    }

    public void setEstado_pago(String estado_pago) {
        this.estado_pago = estado_pago;
    }

    public Date getFechaorden() {
        return fechaorden;
    }

    public void setFechaorden(Date fechaorden) {
        this.fechaorden = fechaorden;
    }

    public ArrayList<Itemorder> getOrden() {
        return orden;
    }

    public void setOrden(ArrayList<Itemorder> orden) {
        this.orden = orden;
    }

    @Override
    public int compareTo(DataRoot other) {
        return mesa.compareTo(other.mesa);
    }

    public String getIdinterno() {
        return idinterno;
    }

    public void setIdinterno(String idinterno) {
        this.idinterno = idinterno;
    }
}