package com.netcracker.monitoring.info;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "results")
public class Results {

    private List<TotalResultInfo> totalResultInfo = null;

    public Results() { }
    
    public Results(List<TotalResultInfo> totalResultInfo) {
        this.totalResultInfo = totalResultInfo;
    }

    public List<TotalResultInfo> getTotalResult() {
        return totalResultInfo;
    }

    @XmlElement(name = "total-result")
    @XmlElementWrapper(name = "total-results")
    public void setTotalResult(List<TotalResultInfo> totalResult) {
        this.totalResultInfo = totalResult;
    }

}
