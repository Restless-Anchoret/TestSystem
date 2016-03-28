package com.netcracker.database.dal;

import com.netcracker.database.entity.TestGroup;
import java.util.List;
import javax.ejb.Local;

@Local
public interface TestGroupFacadeLocal {

    void create(TestGroup testGroup);

    void edit(TestGroup testGroup);

    void remove(TestGroup testGroup);

    TestGroup find(Object id);
    
    List<TestGroup> getTestGroupsByProblemId(Object problemId);
    
}
