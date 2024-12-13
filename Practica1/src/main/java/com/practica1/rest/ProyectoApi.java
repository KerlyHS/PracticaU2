package com.practica1.rest;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.practica1.rest.controller.dao.ProyectoDao;
import com.practica1.rest.controller.dao.services.InversionistaServices;
import com.practica1.rest.controller.dao.services.ProyectoServices;
import com.practica1.rest.models.Inversionista;
import com.practica1.rest.controller.tda.list.LinkedList;
import com.practica1.rest.models.Plan;
import com.practica1.rest.models.Proyecto;

@Path("proyecto")
public class ProyectoApi {

    @Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllFamilys() {
        HashMap<String, Object> map = new HashMap<>();
        ProyectoServices ps = new ProyectoServices();
        map.put("msg", "Ok");
        map.put("data", ps.listAll().toArray());
        if (ps.listAll().isEmpty()) {
            map.put("data", new Object[] {});
        }
        return Response.ok(map).build();
    }

    @Path("/save")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(HashMap<String, Object> map) {
        ProyectoServices ps = new ProyectoServices();

        ps.getProyecto().setNombre(map.get("nombre").toString());
        ps.getProyecto().setFechaInicio(map.get("fechaInicio").toString());
        ps.getProyecto().setFechaFin(map.get("fechaFin").toString());
        ps.getProyecto().setEstado(map.get("estado").toString());

        // Asignar Inversionista y Plan desde el HashMap
        HashMap<String, Object> inversionistaMap = (HashMap<String, Object>) map.get("inversionista");
        HashMap<String, Object> planMap = (HashMap<String, Object>) map.get("plan");

        Inversionista inversionista = new Inversionista();
        inversionista.setId(Integer.parseInt(inversionistaMap.get("id").toString()));
        ps.getProyecto().setInversionista(inversionista);

        Plan Plan = new Plan();
        Plan.setId(Integer.parseInt(planMap.get("id").toString()));
        ps.getProyecto().setPlan(Plan);

        HashMap<String, Object> res = new HashMap<>();
        try {
            ps.save();
            res.put("msg", "OK");
            res.put("data", "Proyecto registrado correctamente");
            return Response.ok(res).build();
        } catch (Exception e) {
            res.put("msg", "Error");
            res.put("data", e.toString());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }

    @Path("/get/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllProyecto(@PathParam("id") Integer id) {
        HashMap<String, Object> map = new HashMap<>();
        ProyectoServices ps = new ProyectoServices();
        try {
            ps.setProyecto(ps.get(id));
        } catch (Exception e) {
            // TODO: handle exception
        }

        map.put("msg", "Ok");
        map.put("data", ps.getProyecto());
        if (ps.getProyecto().getId() == null) {
            map.put("data", "Inversionista no encontrado");
            return Response.status(Status.BAD_REQUEST).entity(map).build();
        }
        return Response.ok(map).build();
    }

    @Path("/update")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(HashMap<String, Object> map) {
        ProyectoServices ps = new ProyectoServices();
        HashMap<String, Object> res = new HashMap<>();

        try {
            // Obtener y actualizar los datos del proyecto
            ps.setProyecto(ps.get(Integer.parseInt(map.get("id").toString()))); // Obteniendo proyecto existente por ID
            ps.getProyecto().setNombre(map.get("nombre").toString());
            ps.getProyecto().setFechaInicio(map.get("fechaInicio").toString());
            ps.getProyecto().setFechaFin(map.get("fechaFin").toString());
            ps.getProyecto().setEstado(map.get("estado").toString());

            // Asignar Inversionista y Plan al Proyecto desde el HashMap
            HashMap<String, Object> inversionistaMap = (HashMap<String, Object>) map.get("inversionista");
            HashMap<String, Object> planMap = (HashMap<String, Object>) map.get("plan");

            Inversionista inversionista = new Inversionista();
            inversionista.setId(Integer.parseInt(inversionistaMap.get("id").toString()));
            ps.getProyecto().setInversionista(inversionista);

            Plan plan = new Plan();
            plan.setId(Integer.parseInt(planMap.get("id").toString()));
            ps.getProyecto().setPlan(plan);

            // Actualizando el proyecto
            ps.update();

            res.put("msg", "OK");
            res.put("data", "Proyecto actualizado correctamente");
            return Response.ok(res).build();

        } catch (NumberFormatException e) {
            res.put("msg", "Error");
            res.put("data", "Formato de número inválido: " + e.getMessage());
            return Response.status(Status.BAD_REQUEST).entity(res).build();

        } catch (Exception e) {
            res.put("msg", "Error");
            res.put("data", "Error al actualizar el proyecto: " + e.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }

    @Path("/ordenar/{attribute}/{type}/{algoritmo}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response ordenar(@PathParam("attribute") String attribute,
            @PathParam("type") Integer type,
            @PathParam("algoritmo") String algoritmo) {
        ProyectoServices ps = new ProyectoServices();
        HashMap<String, Object> map = new HashMap<>();
    
        try {
            LinkedList<Proyecto> proyectosOrdenados = ps.ordenar(attribute, type, algoritmo);
    
            // Incluir la lista ordenada en la respuesta
            map.put("msg", "OK");
            map.put("data", proyectosOrdenados); // Solo agregamos la lista ordenada aquí
    
            return Response.ok(map).build();
        } catch (Exception e) {
            map.put("msg", "Error");
            map.put("data", "Error al ordenar proyectos: " + e.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(map).build();
        }
    }
    
    @GET
    @Path("/buscar")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarProyecto(
            @QueryParam("attribute1") String attribute1,
            @QueryParam("element1") String element1,
            @QueryParam("attribute2") String attribute2,
            @QueryParam("element2") String element2,
            @QueryParam("method") String method) {
        Map<String, Object> map = new HashMap<>();
        try {
            ProyectoDao proyectoDao = new ProyectoDao();
            Map<String, Object> criteria = new HashMap<>();
            if (attribute1 != null && element1 != null) {
                criteria.put(attribute1, element1);
            }
            if (attribute2 != null && element2 != null) {
                criteria.put(attribute2, element2);
            }
            Object resultado = proyectoDao.buscar(criteria, method);
            if (resultado != null) {
                map.put("msg", "Success");
                map.put("data", resultado);
                return Response.ok(map).build();
            } else {
                map.put("msg", "Not Found");
                map.put("data", "No se encontró el proyecto con los atributos especificados.");
                return Response.status(Status.NOT_FOUND).entity(map).build();
            }
        } catch (Exception e) {
            map.put("msg", "Error");
            map.put("data", "Error al buscar el proyecto: " + e.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(map).build();
        }
    }

}
