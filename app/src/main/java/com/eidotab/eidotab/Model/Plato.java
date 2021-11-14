package com.eidotab.eidotab.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;


public class Plato implements Serializable, Comparable<Plato>
{

    private String _id;
    private String nombre_plato;
    private String categoria_plato;
    private String sub_categoria_plato;
    private String descripcion;
    private Double precio_plato;
    private String foto_plato;
    private String foto_movil;
    private Boolean m1;
    private Boolean m2;
    private Boolean m3;
    private String estado_contorno;
    private ArrayList<String> contorno;
    private ArrayList<String> ingrediente;
    private Date fecha_creacion;
    private String idioma;
    private String par;
    private boolean sugerencia;



    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getNombre_plato() {
        return nombre_plato;
    }

    public void setNombre_plato(String nombre_plato) {
        this.nombre_plato = nombre_plato;
    }

    public String getCategoria_plato() {
        return categoria_plato;
    }

    public void setCategoria_plato(String categoria_plato) {
        this.categoria_plato = categoria_plato;
    }

    public String getSub_categoria_plato() {
        return sub_categoria_plato;
    }

    public void setSub_categoria_plato(String sub_categoria_plato) {
        this.sub_categoria_plato = sub_categoria_plato;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio_plato() {
        return precio_plato;
    }

    public void setPrecio_plato(double precio_plato) {
        this.precio_plato = precio_plato;
    }

    public String getFoto_plato() {
        return foto_plato;
    }

    public void setFoto_plato(String foto_plato) {
        this.foto_plato = foto_plato;
    }

    public String getEstado_contorno() {
        return estado_contorno;
    }

    public void setEstado_contorno(String estado_contorno) {
        this.estado_contorno = estado_contorno;
    }

    public ArrayList<String> getContorno() {
        return contorno;
    }

    public void setContorno(ArrayList<String> contorno) {
        this.contorno = contorno;
    }

    public ArrayList<String> getIngrediente() {
        return ingrediente;
    }

    public void setIngrediente(ArrayList<String> ingrediente) {
        this.ingrediente = ingrediente;
    }

    public Date getFecha_creacion() {
        return fecha_creacion;
    }

    public void setFecha_creacion(Date fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public boolean isSugerencia() {
        return sugerencia;
    }

    public void setSugerencia(boolean sugerencia) {
        this.sugerencia = sugerencia;
    }

    public String getFoto_movil() {
        return foto_movil;
    }

    public void setFoto_movil(String foto_movil) {
        this.foto_movil = foto_movil;
    }

    public String getPar() {
        return par;
    }

    public void setPar(String par) {
        this.par = par;
    }

    @Override
    public int compareTo(Plato other) {
        return categoria_plato.compareTo(other.categoria_plato);
    }


    public Boolean getM1() {
        return m1;
    }

    public void setM1(Boolean m1) {
        this.m1 = m1;
    }

    public Boolean getM2() {
        return m2;
    }

    public void setM2(Boolean m2) {
        this.m2 = m2;
    }

    public Boolean getM3() {
        return m3;
    }

    public void setM3(Boolean m3) {
        this.m3 = m3;
    }

}
