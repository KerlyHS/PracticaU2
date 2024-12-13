package com.practica1.rest.controller.dao;
import java.util.Map;
import java.util.function.ToIntBiFunction;
import com.practica1.rest.controller.dao.implement.AdapterDao;
import java.lang.reflect.Field;
import com.practica1.rest.controller.tda.list.LinkedList;
import com.practica1.rest.models.Proyecto;

public class ProyectoDao extends AdapterDao<Proyecto> {
    private Proyecto proyecto;
    private LinkedList listAll;
    public ProyectoDao() {
        super(Proyecto.class);
    }
    public Proyecto getProyecto() {
        if (proyecto == null) {
            proyecto = new Proyecto();
        }
        return this.proyecto;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }
    
    public LinkedList getListAll() {
        if(listAll == null){
            this.listAll = listAll();
        }
        return listAll;
    }

    public Boolean save() throws Exception {
        Integer id = getListAll().getSize()+1;
        proyecto.setId(id);
        this.persist(this.proyecto);
        return true;
    }

    public Boolean update() throws Exception {
        this.merge(getProyecto(), getProyecto().getId());
        return true;
    }

    public LinkedList<Proyecto> ordenar(String attribute, Integer type, String algoritmo) throws Exception {
        LinkedList<Proyecto> lista = getListAll();
        return lista.ordenar(attribute, type, algoritmo);
    }
    
    public Object buscar(Map<String, Object> criteria, String method) throws Exception {
        LinkedList<Proyecto> lista = getListAll();
        if (lista.isEmpty()) {
            throw new Exception("La lista está vacía. No se puede buscar.");
        }
        return buscar(lista, criteria, 0, lista.getSize() - 1, method);
    }

    private Object buscar(LinkedList<Proyecto> list, Map<String, Object> criteria, int low, int high, String method) throws Exception {
        if (method.equals("binary")) {
            while (low <= high) {
                int mid = (low + high) / 2;
                Proyecto model = list.get(mid);
                boolean match = true;
                for (Map.Entry<String, Object> entry : criteria.entrySet()) {
                    Object attributeValue = getAttributeValue(model, entry.getKey());
                    Object searchValue = entry.getValue();
                    if (!attributeValue.toString().toLowerCase().startsWith(searchValue.toString().toLowerCase())) {
                        match = false;
                        break;
                    }
                }
                if (match) {
                    return model;
                } else if (((Comparable<Object>) getAttributeValue(model, criteria.keySet().iterator().next())).compareTo(criteria.values().iterator().next()) < 0) {
                    low = mid + 1;
                } else {
                    high = mid - 1;
                }
            }
            return null;
        } else if (method.equals("sequential")) {
            for (int i = 0; i < list.getSize(); i++) {
                Proyecto model = list.get(i);
                boolean match = true;
                for (Map.Entry<String, Object> entry : criteria.entrySet()) {
                    Object attributeValue = getAttributeValue(model, entry.getKey());
                    Object searchValue = entry.getValue();
                    if (!attributeValue.toString().toLowerCase().startsWith(searchValue.toString().toLowerCase())) {
                        match = false;
                        break;
                    }
                }
                if (match) {
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
    public Object buscar(String attribute, Object element, String method) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'buscar'");
    }
}


