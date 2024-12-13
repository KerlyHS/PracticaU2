package com.practica1.rest.controller.dao.services;
import com.practica1.rest.controller.dao.PlanDao;
import com.practica1.rest.controller.tda.list.LinkedList;
import com.practica1.rest.models.Inversionista;
import com.practica1.rest.models.Plan;

public class PlanServices {
    private PlanDao obj;

    public PlanServices() {
        obj = new PlanDao();
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

    public Plan getPlan() {
        return obj.getPlan();
    }

    public void setPlan(Plan Plan) {
        obj.setPlan(Plan);
    }

    public Plan get(Integer id) throws Exception {
        return obj.get(id);
    }
}

