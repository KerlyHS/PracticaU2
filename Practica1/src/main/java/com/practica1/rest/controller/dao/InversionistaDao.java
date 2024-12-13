package com.practica1.rest.controller.dao;
import java.lang.reflect.Field;
import java.util.function.ToIntBiFunction;
import com.practica1.rest.controller.dao.implement.AdapterDao;
import com.practica1.rest.controller.tda.list.LinkedList;
import com.practica1.rest.models.Inversionista;
import com.practica1.rest.models.Proyecto;

public class InversionistaDao extends AdapterDao<Inversionista> {
    
    private Inversionista inversionista;
    private LinkedList listAll;
    public InversionistaDao() {
        super(Inversionista.class);
    }
    public Inversionista getInversionista() {
        if (inversionista == null) {
            inversionista = new Inversionista();
        }
        return this.inversionista;
    }

    public void setInversionista(Inversionista inversionista) {
        this.inversionista = inversionista;
    }
    
    public LinkedList getListAll() {
        if(listAll == null){
            this.listAll = listAll();
        }
        return listAll;
    }

    public Boolean save() throws Exception {
        Integer id = getListAll().getSize()+1;
        inversionista.setId(id);
        this.persist(this.inversionista);
        return true;
    }

    public Boolean update() throws Exception {
        this.merge(getInversionista(), getInversionista().getId());
        return true;
    }

     public Object buscar(String attribute, Object element, String method) throws Exception {
        LinkedList<Proyecto> lista = getListAll();
        if (lista.isEmpty()) {
            throw new Exception("La lista está vacía. No se puede buscar.");
        }
        return buscar(lista, element, attribute, 0, lista.getSize() - 1, method);
    }
    
    private Object buscar(LinkedList<Proyecto> list, Object element, String attribute, int low, int high, String method) throws Exception {
        if (method.equals("binary")) {
            while (low <= high) {
                int mid = (low + high) / 2;
                Proyecto model = list.get(mid);
                Object midValue = getAttributeValue(model, attribute);
                
                Object searchValue = element instanceof String ? element : (element instanceof Integer ? Integer.valueOf(element.toString()) : Float.valueOf(element.toString()));
    
                if (midValue.equals(searchValue)) {
                    return model;
                } else if (((Comparable<Object>) midValue).compareTo(searchValue) < 0) {
                    low = mid + 1;
                } else {
                    high = mid - 1;
                }
            }
            return null;
        } else if (method.equals("sequential")) {
            for (int i = 0; i < list.getSize(); i++) {
                Proyecto model = list.get(i);
                Object modelValue = getAttributeValue(model, attribute);
                if (modelValue.equals(element)) {
                    return model;
                }
            }
            return null;
        } else {
            throw new Exception("Método de búsqueda no válido. Debe ser 'binary' o 'sequential'.");
        }
    }
    
    private Object getAttributeValue(Proyecto model, String attribute) throws Exception {
        Field field = model.getClass().getDeclaredField(attribute);
        field.setAccessible(true);
        return field.get(model);
    }


}


