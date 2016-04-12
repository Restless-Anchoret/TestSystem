package com.netcracker.monitoring.rank;

import com.netcracker.monitoring.info.ProblemResultInfo;
import com.netcracker.monitoring.info.TotalResultInfo;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StandardRankStrategy implements RankStrategy {

    @Override
    public List<TotalResultInfo> formResults(List<ProblemResultInfo> problemResultInfos) {
        List<TotalResultInfo> resultInfoList = new ArrayList<>();
        Map<Integer, List<ProblemResultInfo>> idToProblemResultMap = new HashMap<>();

        for (ProblemResultInfo info : problemResultInfos) {
            if (!idToProblemResultMap.containsKey(info.getUserId())) {
                idToProblemResultMap.put(info.getUserId(), new ArrayList<>());
            }
            idToProblemResultMap.get(info.getUserId()).add(info);
        }
        for (Map.Entry<Integer, List<ProblemResultInfo>> entry : idToProblemResultMap.entrySet()) {
            resultInfoList.add(new TotalResultInfo(entry.getKey(), entry.getValue()));
        }

        Collections.sort(resultInfoList, ((info1, info2) -> {
            if (info1.getPoints() != info2.getPoints()) {
                return -Short.compare(info1.getPoints(), info2.getPoints());
            } else {
                return Integer.compare(info1.getFine(), info2.getFine());
            }
        }));

        for (int i = 0; i < resultInfoList.size(); i++) {
            resultInfoList.get(i).setPlace(i + 1);
        }

        return resultInfoList;
    }

}
