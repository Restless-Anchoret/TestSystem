package com.netcracker.web.util;

import java.io.Serializable;


public class MonitorColumn implements Serializable{

    private String header;
    private String value;

    public MonitorColumn(String header, String value) {
        this.header = header;
        this.value = value;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
