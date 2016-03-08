/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netcracker.dal;

import com.netcracker.entity.AuthorDecision;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Ataman
 */
@Local
public interface AuthorDecisionFacadeLocal {

    void create(AuthorDecision authorDecision);

    void edit(AuthorDecision authorDecision);

    void remove(AuthorDecision authorDecision);

    AuthorDecision find(Object id);

    List<AuthorDecision> findAll();

    List<AuthorDecision> findAll(int[] range);

    int count();
    
}
