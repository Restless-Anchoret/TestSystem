package com.netcracker.database.dal;

import com.netcracker.database.entity.Problem;
import com.netcracker.database.entity.TestGroup;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Stateless
public class TestGroupFacade extends AbstractFacade<TestGroup> implements TestGroupFacadeLocal {

    @PersistenceContext(unitName = "com.netcracker_DatabaseModule_ejb_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TestGroupFacade() {
        super(TestGroup.class);
    }

    @Override
    public List<TestGroup> getTestGroupsByProblemId(Object problemId) {
        TypedQuery<Problem> query = em.createNamedQuery("Problem.findById", Problem.class);
        query.setParameter("id", problemId);
        Problem problem = (Problem) query.getSingleResult();
        return problem.getTestGroupList();
    }

    @Override
    public TestGroup find(Object id) {
        return super.find(id, "TestGroup.findById");
    }
    
    
    
}
