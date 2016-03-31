package com.netcracker.database.dal;

import com.netcracker.database.entity.AuthorDecision;
import com.netcracker.database.entity.Problem;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class ProblemFacade extends AbstractFacade<Problem> implements ProblemFacadeLocal {

    @PersistenceContext(unitName = "com.netcracker_DatabaseModule_ejb_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProblemFacade() {
        super(Problem.class);
    }

    @Override
    public Problem loadAuthorDecisions(Problem problem) {
        em.merge(problem).getAuthorDecisionList();
        return problem;
    }

    @Override
    public Problem find(Object id) {
        return super.find(id, "Problem.findById");
    }

    @Override
    public Problem loadCompetitionProblems(Problem problem) {
        em.merge(problem).getCompetitionProblemList();
        return problem;
    }

    @Override
    public Problem loadTestGroups(Problem problem) {
        em.merge(problem).getTestGroupList();
        return problem;
    }
    
    
}
