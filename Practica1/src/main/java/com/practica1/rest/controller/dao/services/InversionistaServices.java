package com.practica1.rest.controller.dao.services;
import com.practica1.rest.controller.dao.InversionistaDao;
import com.practica1.rest.controller.dao.ProyectoDao;
import com.practica1.rest.controller.tda.list.LinkedList;
import com.practica1.rest.models.Inversionista;
import com.practica1.rest.models.Proyecto;

public class InversionistaServices {
    private InversionistaDao obj;

    public InversionistaServices() {
        obj = new InversionistaDao();
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

    public Inversionista getInversionista() {
        return obj.getInversionista();
    }

    public void setInversionista(Inversionista inversionista) {
        obj.setInversionista(inversionista);
    }

    public Inversionista get(Integer id) throws Exception {
        return obj.get(id);
    }

     public Object buscar(String attribute, Object element, String method) throws Exception {
        LinkedList<Proyecto> lista = listAll();
        
        if (lista == null || lista.isEmpty()) {
            throw new Exception("La lista está vacía.");
        }
        
        InversionistaDao inversionistaDao = new InversionistaDao();
        return inversionistaDao.buscar(attribute, element, method);
    }

}

