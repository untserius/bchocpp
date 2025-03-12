package com.axxera.ocpp.model.ocpp;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "scheduled_maintenance")
public class ScheduledMaintenance extends BaseEntity{

    private static final long serialVersionUID = 1L;

    private long stnId;
    private long portId;
    private Date endTimeStamp;
    private String response;

    public long getStnId() {return stnId;}
    public void setStnId(long stnId) {this.stnId = stnId;}
    public long getPortId() {return portId;}
    public void setPortId(long portId) {this.portId = portId;}
    public Date getEndTimeStamp() {return endTimeStamp;}
    public void setEndTimeStamp(Date endTimeStamp) {this.endTimeStamp = endTimeStamp;}
    public String getResponse() {return response;}
    public void setResponse(String response) {this.response = response;}

    @Override
    public String toString() {
        return "ScheduledMaintenance{" +
                "stnId=" + stnId +
                ", portId=" + portId +
                ", endTimeStamp=" + endTimeStamp +
                ", response='" + response + '\'' +
                '}';
    }
}
