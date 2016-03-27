package com.netcracker.database.dal;

import com.netcracker.database.entity.Compilator;
import java.util.List;
import javax.ejb.Local;

@Local
public interface CompilatorFacadeLocal {

    void create(Compilator compilator);

    void edit(Compilator compilator);

    void remove(Compilator compilator);

    Compilator find(Object id);
    
    Compilator findByName(String name);

    List<Compilator> findAll();
    
    Compilator loadAuthorDecisions(Compilator compilator);
    
    Compilator loadSubmissions(Compilator compilator);
    
}
