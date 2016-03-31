package com.netcracker.database.dal;

import com.netcracker.database.entity.AuthorDecision;
import java.util.List;
import javax.ejb.Local;

@Local
public interface AuthorDecisionFacadeLocal {

    void create(AuthorDecision authorDecision);

    void edit(AuthorDecision authorDecision);

    void remove(AuthorDecision authorDecision);

    AuthorDecision find(Object id);
    
    AuthorDecision loadCompilator(AuthorDecision authorDecision);
    
}
