package com.netcracker.database.dal;

import com.netcracker.database.entity.PersonalData;
import java.util.List;
import javax.ejb.Local;

@Local
public interface PersonalDataFacadeLocal {

    void create(PersonalData personalData);

    void edit(PersonalData personalData);

    void remove(PersonalData personalData);

    PersonalData find(Object id);
    
}
