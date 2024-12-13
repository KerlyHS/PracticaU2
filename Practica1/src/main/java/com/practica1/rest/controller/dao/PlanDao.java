package com.practica1.rest.controller.dao;

import com.practica1.rest.controller.dao.implement.AdapterDao;
import com.practica1.rest.controller.tda.list.LinkedList;
import com.practica1.rest.models.Plan;

public class PlanDao extends AdapterDao<Plan> {
    private Plan Plan;
    private LinkedList listAll;

    public PlanDao() {
        super(Plan.class);
    }

    public Plan getPlan() {
        if (Plan == null) {
            Plan = new Plan();
        }
        return this.Plan;
    }

    public void setPlan(Plan Plan) {
        this.Plan = Plan;
    }
    
    public LinkedList getListAll() {
        if (listAll == null) {
            this.listAll = listAll();
        }
        return listAll;
    }

    public Boolean save() throws Exception {
        Integer id = getListAll().getSize() + 1;
        Plan.setId(id);
        this.persist(this.Plan);
        return true;
    }
    public Boolean update() throws Exception {
        this.merge(getPlan(), getPlan().getId());
        return true;
    }

}

