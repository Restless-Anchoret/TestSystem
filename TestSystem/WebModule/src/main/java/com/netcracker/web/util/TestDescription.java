package com.netcracker.web.util;

public class TestDescription {

    private Integer number;
    private String testGroupType;
    private String verdict;
    private Integer decisionTime;

    public TestDescription(Integer number, String testGroupType, String verdict, Integer decisionTime) {
        this.number = number;
        this.testGroupType = testGroupType;
        this.verdict = verdict;
        this.decisionTime = decisionTime;
    }

    public Integer getNumber() {
        return number;
    }

    public String getTestGroupType() {
        return testGroupType;
    }

    public String getVerdict() {
        return verdict;
    }

    public Integer getDecisionTime() {
        return decisionTime;
    }
    
}