package com.practica1.rest.models;

public class Proyecto {

    private Integer id;
    private String nombre;
    private String fechaInicio;
    private String fechaFin;
    private String estado;
    private Inversionista inversionista;
    private Plan plan;

    public Proyecto() {
    }

    public Proyecto(Integer id, String nombre, String fechaInicio, String fechaFin, String estado, Inversionista inversionista, Plan plan) {
        this.id = id;
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estado = estado;
        this.inversionista = inversionista;
        this.plan = plan;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Inversionista getInversionista() {
        return inversionista;
    }

    public void setInversionista(Inversionista inversionista) {
        this.inversionista = inversionista;
    }

    public Plan getPlan() {
        return this.plan;
    }

    public void setPlan(Plan value) {
        this.plan = value;
    }
@Override
    public String toString() {
        return "Proyecto{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", fechaInicio='" + fechaInicio + '\'' +
                ", fechaFin='" + fechaFin + '\'' +
                ", estado='" + estado + '\'' +
                ", inversionista=" + inversionista +
                ", plan=" + plan +
                '}';
    }
}
