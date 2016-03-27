package com.netcracker.database.dal;

import com.netcracker.database.entity.Compilator;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Stateless
public class CompilatorFacade extends AbstractFacade<Compilator> implements CompilatorFacadeLocal {

    @PersistenceContext(unitName = "com.netcracker_DatabaseModule_ejb_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CompilatorFacade() {
        super(Compilator.class);
    }

    @Override
    public List<Compilator> findAll() {
        TypedQuery query = em.createNamedQuery("Compilator.findAll", Compilator.class);
        return query.getResultList();
    }

    @Override
    public Compilator find(Object id) {
        return super.find(id, "Compilator.findById");
    }

    @Override
    public Compilator findByName(String name) {
        TypedQuery query = em.createNamedQuery("Compilator.findByName", Compilator.class);
        query.setParameter("name", name);
        List<Compilator> results = query.getResultList();
        if (results.isEmpty())
            return null;
        else
            return results.get(0);
    }

    @Override
    public Compilator loadAuthorDecisions(Compilator compilator) {
        em.merge(compilator).getAuthorDecisionList();
        return compilator;
    }

    @Override
    public Compilator loadSubmissions(Compilator compilator) {
        em.merge(compilator).getSubmissionList();
        return compilator;
    }
    
    
    
}
