package com.practica1.rest.controller.dao.services;
import com.practica1.rest.controller.dao.ProyectoDao;
import com.practica1.rest.controller.tda.list.LinkedList;
import com.practica1.rest.models.Inversionista;
import com.practica1.rest.models.Proyecto;

public class ProyectoServices {
    private ProyectoDao obj;

    public ProyectoServices() {
        obj = new ProyectoDao();
    }
    
    public Boolean save() throws Exception {
        return obj.save();
    }

    public Boolean update() throws Exception {
        return obj.update();
    }

    public LinkedList listAll() {
        return obj.getListAll();
    }

    public Proyecto getProyecto() {
        return obj.getProyecto();
    }

    public void setProyecto(Proyecto proyecto) {
        obj.setProyecto(proyecto);
    }

    public Proyecto get(Integer id) throws Exception {
        return obj.get(id);
    }

    public LinkedList<Proyecto> ordenar(String attribute, Integer type, String algoritmo) throws Exception {
        LinkedList<Proyecto> lista = listAll();
    
        if (lista == null || lista.isEmpty()) {
            throw new Exception("La lista está vacía o no se pudo recuperar.");
        }
    
        lista = lista.ordenar(attribute, type, algoritmo); // Usa el método de ordenación definido en tu clase
        return lista;
    }
    
    public Object buscar(String attribute, Object element, String method) throws Exception {
        LinkedList<Proyecto> lista = listAll();
        
        if (lista == null || lista.isEmpty()) {
            throw new Exception("La lista está vacía.");
        }
        
        ProyectoDao proyectoDao = new ProyectoDao();
        return proyectoDao.buscar(attribute, element, method);
    }
}

