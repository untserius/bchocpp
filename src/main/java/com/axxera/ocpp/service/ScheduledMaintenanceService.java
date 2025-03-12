package com.axxera.ocpp.service;

import java.util.Date;

public interface ScheduledMaintenanceService {
    void deleteFromScheduledMaintenance(long portId);

    void insertIntoScheduledMaintenance(long portId, long stationId, Date endTimeStamp);

    void updateResponseInMaintenance(long portId, String response);

    boolean isInScheduledMaintenance(long portId);
}
