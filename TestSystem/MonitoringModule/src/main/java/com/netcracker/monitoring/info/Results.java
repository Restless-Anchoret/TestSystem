package com.netcracker.monitoring.info;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import com.netcracker.monitoring.info.TotalResultInfo;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Магистраж
 */
@XmlRootElement(name = "results")
public class Results {

    List<TotalResultInfo> totalResultInfo;

    public List<TotalResultInfo> getTotalResult() {
        return totalResultInfo;
    }

    @XmlElement(name = "total-result")
    @XmlElementWrapper(name = "total-results")
    public void setTotalResult(List<TotalResultInfo> totalResult) {
        this.totalResultInfo = totalResult;
    }

}
