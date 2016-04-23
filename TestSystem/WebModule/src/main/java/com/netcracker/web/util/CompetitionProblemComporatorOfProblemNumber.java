package com.netcracker.web.util;

import com.netcracker.database.entity.CompetitionProblem;
import java.util.Comparator;


public class CompetitionProblemComporatorOfProblemNumber implements Comparator<CompetitionProblem> {

    @Override
    public int compare(CompetitionProblem t, CompetitionProblem t1) {
        return t.getProblemNumber().compareToIgnoreCase(t1.getProblemNumber());
    }

}
