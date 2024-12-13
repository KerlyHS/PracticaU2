package com.practica1.rest.controller.tda.list;

import com.practica1.rest.controller.tda.list.Exception.ListEmptyException;

import java.lang.reflect.Method;
import java.util.Arrays;
import com.practica1.rest.controller.tda.list.Node;

public class LinkedList<E> {
    private Node<E> header; // header=cabecera
    private Node<E> last; // last= cola
    private Integer size; // size= tamaño

    // constructor que permite realizar una lista enlazada
    public LinkedList() {
        this.header = null;
        this.last = null;
        this.size = 0;
    }

    // 1er metodo -->ver si esta vacia--- isEmpty= esta vacia (lista)
    public boolean isEmpty() {
        return (this.header == null || this.size == 0);
    }

    // 2do metodo --> agregar en cabeza
    public void addHeader(E dato) {
        Node<E> help;
        if (isEmpty()) {
            help = new Node<>(dato);
            header = help;
            last = help;

        } else {
            Node helpHeader = this.header;
            help = new Node<>(dato, helpHeader);
            this.header = help;
            // this.size++;
        }
        this.size++;
    }

    private void addLast(E info) {
        Node<E> help;
        if (isEmpty()) {
            addHeader(info);
        } else {
            help = new Node<>(info, null);
            last.setNext(help);
            last = help;
            this.size++;
        }
    }

    public void add(E info) {
        addLast(info);
    }

    private Node<E> getNode(Integer index) throws ListEmptyException, IndexOutOfBoundsException {
        if (isEmpty()) {
            throw new ListEmptyException("Error, List empty");
        } else if (index.intValue() < 0 || index.intValue() >= this.size) {
            throw new IndexOutOfBoundsException("Error, fuera de rango");
        } else if (index.intValue() == 0) {
            return header;
        } else if (index.intValue() == (this.size - 1)) {
            return last;
        } else {
            Node<E> search = header;
            int cont = 0;
            while (cont < index.intValue()) {
                cont++;
                search = search.getNext();
            }
            return search;
        }
    }

    private E getFirst() throws ListEmptyException {
        if (isEmpty()) {
            throw new ListEmptyException("Error, Lista vacía");
        }
        return header.getInfo();
    }

    private E getLast() throws ListEmptyException {
        if (isEmpty()) {
            throw new ListEmptyException("Error, Lista vacía");
        }
        return last.getInfo();
    }

    public E get(Integer index) throws ListEmptyException, IndexOutOfBoundsException {
        if (isEmpty()) {
            throw new ListEmptyException("Error, Lista vacía");
        } else if (index.intValue() < 0 || index.intValue() >= this.size.intValue()) {
            throw new IndexOutOfBoundsException("Error, fuera de rango");
        } else if (index.intValue() == 0) {
            return header.getInfo();
        } else if (index.intValue() == (this.size - 1)) {
            return last.getInfo();
        } else {
            Node<E> search = header;
            int cont = 0;
            while (cont < index.intValue()) {
                cont++;
                search = search.getNext();
            }
            return search.getInfo();
        }
    }

    public void add(E info, Integer index) throws ListEmptyException, IndexOutOfBoundsException {
        if (isEmpty() || index.intValue() == 0) {
            addHeader(info);
        } else if (index.intValue() == this.size.intValue()) {
            addLast(info);
        } else {
            Node<E> search_preview = getNode(index - 1);
            Node<E> search = getNode(index);
            Node<E> help = new Node<>(info, search);
            search_preview.setNext(help);
            this.size++;
        }
    }

    /*** END BY POSITION */
    public void reset() {
        this.header = null;
        this.last = null;
        this.size = 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("List Data \n");
        try {
            Node<E> help = header;
            while (help != null) {
                sb.append(help.getInfo()).append(" -> ");
                help = help.getNext();
            }
        } catch (Exception e) {
            sb.append(e.getMessage());
        }

        return sb.toString();
    }

    public Integer getSize() {
        return this.size;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    // METODO PARA TRANSFORMAR LA LISTA EN ARREGLO
    public E[] toArray() {
        E[] matrix = null;
        if (!isEmpty()) {
            Class clazz = header.getInfo().getClass();
            matrix = (E[]) java.lang.reflect.Array.newInstance(clazz, size);// (E[].. casteo de datos)
            Node<E> aux = header;
            for (int i = 0; i < this.size; i++) {
                matrix[i] = aux.getInfo();
                aux = aux.getNext();
            }
        }
        return matrix;
    }

    // METODO PARA TRANSFORMAR LA LISTA EN ARREGLO
    public LinkedList<E> toList(E[] matrix) {
        reset(); // para resetear la lista
        for (int i = 0; i < matrix.length; i++) {
            this.add(matrix[i]);
        }
        return this;
    }

    // METODO PARA ACTUALIZAR UN NODO
    public void update(E info, Integer index) throws ListEmptyException, IndexOutOfBoundsException {
        if (isEmpty()) {
            throw new ListEmptyException("Error, Lista vacía");
        } else if (index.intValue() < 0 || index.intValue() >= this.size.intValue()) {
            throw new IndexOutOfBoundsException("Error, fuera de rango");
        } else if (index.intValue() == 0) {
            header.setInfo(info);
        } else if (index.intValue() == (this.size - 1)) {
            last.setInfo(info);
        } else {
            Node<E> search = header;
            int cont = 0;
            while (cont < index.intValue()) {
                cont++;
                search = search.getNext();
            }
            search.setInfo(info);
        }
    }

    private Boolean compare(Object a, Object b, Integer type) {
        if (a == null || b == null) {
            return false; // Evita comparar valores nulos
        }
    
        switch (type) {
            case 0:
                return a instanceof Number
                    ? ((Number) a).doubleValue() > ((Number) b).doubleValue()
                    : a.toString().compareTo(b.toString()) > 0;
            default:
                return a instanceof Number
                    ? ((Number) a).doubleValue() < ((Number) b).doubleValue()
                    : a.toString().compareTo(b.toString()) < 0;
        }
    }
    

     /*-------------------------------------Comparacion Clase----------------------------------------------------------------------------- */
    private Boolean atrribute_compare(String attribute, E a, E b, Integer type) throws Exception {
        return compare(exist_attribute(a, attribute), exist_attribute(b, attribute), type);
    }

    private Object exist_attribute(E a, String attribute) throws Exception {
        if (attribute == null || attribute.isEmpty()) {
            throw new Exception("El atributo no puede ser null o vacío.");
        }
    
        Method method = null;
        String methodName = "get" + attribute.substring(0, 1).toUpperCase() + attribute.substring(1);
        for (Method aux : a.getClass().getMethods()) {
            if (aux.getName().equals(methodName)) {
                method = aux;
                break;
            }
        }
    
        if (method == null) {
            throw new Exception("El atributo '" + attribute + "' no existe en la clase.");
        }
    
        return method.invoke(a);
    }
    

    /*-------------------------------------Metodos de ordenacion------------------------------------------------------------------------------ */

    public LinkedList<E> ordenar(String attribute, Integer type, String algoritmo) throws Exception {
        if (isEmpty()) {
            throw new Exception("La lista está vacía. No se puede ordenar.");
        }

        E[] lista = this.toArray();

        if (algoritmo.equalsIgnoreCase("shellsort")) {
            shellSort(lista, attribute, type);
        } else if (algoritmo.equalsIgnoreCase("quicksort")) {
            quickSort(lista, 0, lista.length - 1, attribute, type);
        } else if (algoritmo.equalsIgnoreCase("mergesort")) {
            lista = mergeSort(lista, attribute, type);
        } else {
            throw new Exception(
                    "Algoritmo no reconocido: " + algoritmo + ". Usa 'shellsort', 'quicksort' o 'mergesort'.");
        }
        reset();
        for (E elemento : lista) {
            add(elemento);
        }

        return this;
    }

    private void shellSort(E[] lista, String attribute, Integer type) throws Exception {
        int n = lista.length;
        for (int gap = n / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < n; i++) {
                E temp = lista[i];
                int j;
                for (j = i; j >= gap && atrribute_compare(attribute, lista[j - gap], temp, type); j -= gap) {
                    lista[j] = lista[j - gap];
                }
                lista[j] = temp;
            }
        }
    }

    private void quickSort(E[] lista, int low, int high, String attribute, Integer type) throws Exception {
        if (low < high) {
            int pi = particion(lista, low, high, attribute, type);
            quickSort(lista, low, pi - 1, attribute, type);
            quickSort(lista, pi + 1, high, attribute, type);
        }
    }

    private int particion(E[] lista, int low, int high, String attribute, Integer type) throws Exception {
        E pivot = lista[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (!atrribute_compare(attribute, pivot, lista[j], type)) {
                i++;
                E temp = lista[i];
                lista[i] = lista[j];
                lista[j] = temp;
            }
        }
        E temp = lista[i + 1];
        lista[i + 1] = lista[high];
        lista[high] = temp;
        return i + 1;
    }

    private E[] mergeSort(E[] lista, String attribute, Integer type) throws Exception {
        if (lista.length <= 1)
            return lista;

        int mid = lista.length / 2;
        E[] izquierda = Arrays.copyOfRange(lista, 0, mid);
        E[] derecha = Arrays.copyOfRange(lista, mid, lista.length);

        izquierda = mergeSort(izquierda, attribute, type);
        derecha = mergeSort(derecha, attribute, type);

        return merge(izquierda, derecha, attribute, type);
    }

    private E[] merge(E[] izquierda, E[] derecha, String attribute, Integer type) throws Exception {
        E[] result = Arrays.copyOf(izquierda, izquierda.length + derecha.length);
        int i = 0, j = 0, k = 0;

        while (i < izquierda.length && j < derecha.length) {
            if (!atrribute_compare(attribute, izquierda[i], derecha[j], type)) {
                result[k++] = izquierda[i++];
            } else {
                result[k++] = derecha[j++];
            }
        }
        while (i < izquierda.length)
            result[k++] = izquierda[i++];
        while (j < derecha.length)
            result[k++] = derecha[j++];

        return result;
    }

    /*-------------------------------------Metodos de Busqueda------------------------------------------------------------------------------ */
 
 public int binaryPrimitive(Object[] array, Object data, int low, int high) {
    while (low <= high) {
        int mid = (low + high) / 2;
        if (array[mid].equals(data)) {
            return mid;
        } else if (((Comparable<Object>) array[mid]).compareTo(data) < 0) {
            low = mid + 1;
        } else {
            high = mid - 1;
        }
    }
    return -1;
}
public int binaryString(String[] array, String data, int low, int high) {
    while (low <= high) {
        int mid = (low + high) / 2;
        if (array[mid].toLowerCase().endsWith(data.toLowerCase())) {
            return mid;
        } else if (array[mid].toLowerCase().compareTo(data.toLowerCase()) < 0) {
            low = mid + 1;
        } else {
            high = mid - 1;
        }
    }
    return -1;
}

public Object buscar(LinkedList<Object> list, Object element, String attribute, int low, int high, String method) throws Exception {
    if (method.equals("binary")) {
        while (low <= high) {
            int mid = (low + high) / 2;
            Object model = list.get(mid);
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
            Object model = list.get(i);
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

private Object getAttributeValue(Object model, String attribute) throws Exception {
    Method method = model.getClass().getMethod("get" + capitalize(attribute));
    return method.invoke(model);
}

private String capitalize(String str) {
    if (str == null || str.isEmpty()) {
        return str;
    }
    return str.substring(0, 1).toUpperCase() + str.substring(1);
}

    public void addAll(LinkedList<E> otherList) {
        for (int i = 0; i < otherList.getSize(); i++) {
            try {
                this.add(otherList.get(i));
            } catch (ListEmptyException | IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        }
    }
/* binario Secuencial */
public LinkedList<Object> binaryPrimitiveSecuencial(Object[] array, Object data, int low, int high) {
    LinkedList<Object> result = new LinkedList<>();
    if (low > high) {
        return result;
    }

    int mid = (low + high) / 2;
    if (array[mid].equals(data)) {
        result.add(array[mid]);
    }

    if (mid + 1 <= high) {
        result.addAll(binaryPrimitiveSecuencial(array, data, mid + 1, high));
    }
    if (mid - 1 >= low) {
        result.addAll(binaryPrimitiveSecuencial(array, data, low, mid - 1));
    }
    return result;
}

public LinkedList<Object> binaryModelsSecuencial(LinkedList<Object> list, Object data, int low, int high, String attribute) throws Exception {
    LinkedList<Object> result = new LinkedList<>();
    if (low > high) {
        return result;
    }

    int mid = (low + high) / 2;
    Object model = list.get(mid);
    String targetValue = (String) getAttributeValue(model, attribute);
    String searchValue = data.toString().toLowerCase();

    if (targetValue.equals(searchValue)) {
        result.add(model);
    }

    if (mid + 1 <= high) {
        result.addAll(binaryModelsSecuencial(list, data, mid + 1, high, attribute));
    }
    if (mid - 1 >= low) {
        result.addAll(binaryModelsSecuencial(list, data, low, mid - 1, attribute));
    }

    return result;
}

public LinkedList<E> buscar(String data) throws ListEmptyException {
    LinkedList<E> result = new LinkedList<>();
    if (isEmpty()) {
        throw new ListEmptyException("List empty");
    } else {
        E[] array = toArray();
        for (int i = 0; i < array.length; i++) {
            if (array[i].toString().toLowerCase().contains(data.toLowerCase())) {
                result.add(array[i]);
            }
        }
    }
    return result;
}

}
