/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netcracker.dal;

import com.netcracker.entity.Problem;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Ataman
 */
@Local
public interface ProblemFacadeLocal {

    void create(Problem problem);

    void edit(Problem problem);

    void remove(Problem problem);

    Problem find(Object id);

    List<Problem> findAll();

    List<Problem> findRange(int[] range);

    int count();
    
}
