package com.netcracker.database.dal;

import com.netcracker.database.entity.PersonalData;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
