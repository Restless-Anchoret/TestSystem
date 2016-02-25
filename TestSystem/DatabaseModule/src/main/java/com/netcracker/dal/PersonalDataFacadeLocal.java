/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netcracker.dal;

import com.netcracker.entity.PersonalData;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Ataman
 */
@Local
public interface PersonalDataFacadeLocal {

    void create(PersonalData personalData);

    void edit(PersonalData personalData);

    void remove(PersonalData personalData);

    PersonalData find(Object id);

    List<PersonalData> findAll();

    List<PersonalData> findRange(int[] range);

    int count();
    
}
