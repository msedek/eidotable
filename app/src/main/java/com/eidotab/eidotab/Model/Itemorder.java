package com.eidotab.eidotab.Model;

import android.widget.ScrollView;

import java.util.ArrayList;
import java.util.Date;

public class Itemorder implements Comparable<Itemorder>
{
    private ArrayList<String> contornos; //PROPIOS
    private ArrayList<String> ingredientes; // ADICIONALES
    private String estadoitem;
    private String nombre_plato;
    private String categoria;
    private String sub_categoria;
    private String foto_movil;
    private Double precio_plato;
    private String nombre_platol;//nombre para mostrar en el tab
    private Integer posi;
    private Boolean enviado; // STATUS PARA SABER SI SE REALIZO EL PEDIDO SOLO SE USA EN BD INTERNA


    public ArrayList<String> getContornos() {
        return contornos;
    }

    public void setContornos(ArrayList<String> contornos) {
        this.contornos = contornos;
    }

    public String getEstadoitem() {
        return estadoitem;
    }

    public void setEstadoitem(String estadoitem) {
        this.estadoitem = estadoitem;
    }

    public String getNombre_plato() {
        return nombre_plato;
    }

    public void setNombre_plato(String nombre_plato) {
        this.nombre_plato = nombre_plato;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getSub_categoria() {
        return sub_categoria;
    }

    public void setSub_categoria(String sub_categoria) {
        this.sub_categoria = sub_categoria;
    }


    public String getFoto_movil() {
        return foto_movil;
    }

    public void setFoto_movil(String foto_movil) {
        this.foto_movil = foto_movil;
    }

    public Double getPrecio_plato() {
        return precio_plato;
    }

    public void setPrecio_plato(Double precio_plato) {
        this.precio_plato = precio_plato;
    }

    public Integer getPosi() {
        return posi;
    }

    public void setPosi(Integer posi) {
        this.posi = posi;
    }

    public ArrayList<String> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(ArrayList<String> ingredientes) {
        this.ingredientes = ingredientes;
    }


    @Override
    public int compareTo(Itemorder other)
    {
        return getCategoria().compareTo(other.getCategoria());
    }

    public Boolean getEnviado() {
        return enviado;
    }

    public void setEnviado(Boolean enviado) {
        this.enviado = enviado;
    }

    public String getNombre_platol() {
        return nombre_platol;
    }

    public void setNombre_platol(String nombre_platol) {
        this.nombre_platol = nombre_platol;
    }
}
