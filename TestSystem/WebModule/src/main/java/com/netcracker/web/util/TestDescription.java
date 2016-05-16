package com.netcracker.web.util;

public class TestDescription {

    private Integer number;
    private String testGroupType;
    private String verdict;
    private Integer decisionTime;
    private Integer numberInGroup;

    public TestDescription(Integer number, String testGroupType, String verdict, Integer decisionTime, Integer numberInGroup) {
        this.number = number;
        this.testGroupType = testGroupType;
        this.verdict = verdict;
        this.decisionTime = decisionTime;
        this.numberInGroup = numberInGroup;
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

    public Integer getNumberInGroup() {
        return numberInGroup;
    }
    
}