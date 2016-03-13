/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netcracker.dal;

import com.netcracker.entity.TestGroup;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Ataman
 */
@Local
public interface TestGroupFacadeLocal {

    void create(TestGroup testGroup);

    void edit(TestGroup testGroup);

    void remove(TestGroup testGroup);

    TestGroup find(Object id);
    
    List<TestGroup> getTestGroupsByProblemId(Object problemId);
    
}
