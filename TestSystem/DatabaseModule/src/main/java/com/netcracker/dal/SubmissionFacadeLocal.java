/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netcracker.dal;

import com.netcracker.entity.Submission;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Ataman
 */
@Local
public interface SubmissionFacadeLocal {

    void create(Submission submission);

    void edit(Submission submission);

    void remove(Submission submission);

    Submission find(Object id);

    List<Submission> findAll();

    List<Submission> findRange(int[] range);

    int count();
    
}
