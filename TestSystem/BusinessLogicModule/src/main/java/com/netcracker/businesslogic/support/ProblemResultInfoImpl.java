package com.netcracker.businesslogic.support;

import com.netcracker.entity.ParticipationResult;
import java.util.ArrayList;
import java.util.List;

public class ProblemResultInfoImpl/* implements ProblemResultInfo*/{
    
//    public static List<ProblemResultInfo> convertList(List<ParticipationResult> inputList) {
//        List<ProblemResultInfo> outputList = new ArrayList<>(inputList.size());
//        for (ParticipationResult result: inputList) {
//            outputList.add(new ProblemResultInfoImpl(result));
//        }
//        return outputList;
//    }
    
    private ParticipationResult participationResult;

    public ProblemResultInfoImpl(ParticipationResult participationResult) {
        this.participationResult = participationResult;
    }
    
    //@Override
    public short getPoints() {
        return participationResult.getPoints();
    }
    
    //@Override
    public int getFine() {
        return participationResult.getFine();
    }
    
    //@Override
    public int getUsedId() {
        return participationResult.getId();
    }
    
    //@Override
    public int getProblemId() {
        return participationResult.getCompetitionProblemId().getId();
    }

}