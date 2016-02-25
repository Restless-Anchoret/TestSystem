/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netcracker.dal;

import com.netcracker.entity.Participation;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Ataman
 */
@Local
public interface ParticipationFacadeLocal {

    void create(Participation participation);

    void edit(Participation participation);

    void remove(Participation participation);

    Participation find(Object id);

    List<Participation> findAll();

    List<Participation> findRange(int[] range);

    int count();
    
}
