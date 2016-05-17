package com.netcracker.web.participants;

import com.netcracker.testing.system.Verdict;
import com.netcracker.testing.system.VerdictInfo;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class VerdictInfoController {

    public VerdictInfo getVerdictInfo(String verdict, Short points, Integer wrongTestNumber) {
        return new VerdictInfo(Verdict.valueOf(verdict.toUpperCase()), points)
                .setWrongTestNumber(wrongTestNumber);
    }
    
}