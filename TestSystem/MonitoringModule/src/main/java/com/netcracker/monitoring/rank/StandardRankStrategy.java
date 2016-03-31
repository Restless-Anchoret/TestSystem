/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netcracker.monitoring.rank;

import com.netcracker.monitoring.info.ProblemResultInfo;
import com.netcracker.monitoring.info.TotalResultInfo;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Магистраж
 */
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

        Collections.sort(resultInfoList, new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                TotalResultInfo obj1 = (TotalResultInfo) o1;
                TotalResultInfo obj2 = (TotalResultInfo) o2;

                if (obj1.getPoints() > obj2.getPoints()) {
                    return -1;
                } else if (obj2.getPoints() > obj1.getPoints()) {
                    return 1;
                } else {
                    return obj1.getFine() < obj2.getFine() ? -1 : 1;
                }
            }
        });

        for (int i = 0; i < resultInfoList.size(); i++) {
            resultInfoList.get(i).setPlace(i + 1);

        }

        return resultInfoList;
    }

}
