/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netcracker.dal;

import com.netcracker.entity.Compilator;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Ataman
 */
@Local
public interface CompilatorFacadeLocal {

    void create(Compilator compilator);

    void edit(Compilator compilator);

    void remove(Compilator compilator);

    Compilator find(Object id);

    List<Compilator> findAll();

    List<Compilator> findRange(int[] range);

    int count();
    
}
