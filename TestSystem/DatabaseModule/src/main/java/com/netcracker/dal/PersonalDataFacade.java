/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netcracker.dal;

import com.netcracker.entity.PersonalData;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Ataman
 */
@Stateless
public class PersonalDataFacade extends AbstractFacade<PersonalData> implements PersonalDataFacadeLocal {

    @PersistenceContext(unitName = "com.netcracker_DatabaseModule_ejb_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PersonalDataFacade() {
        super(PersonalData.class);
    }

    @Override
    public PersonalData find(Object id) {
        return super.find(id, "PersonalData.findById");
    } 
    
    
}
