package com.netcracker.web.util;

import com.netcracker.database.entity.CompetitionProblem;
import java.util.Comparator;


public class CompetitionProblemComporatorOfProblemNumber implements Comparator<CompetitionProblem> {

    @Override
    public int compare(CompetitionProblem problem, CompetitionProblem problem1) {
        return problem.getProblemNumber().compareToIgnoreCase(problem1.getProblemNumber());
    }

}
