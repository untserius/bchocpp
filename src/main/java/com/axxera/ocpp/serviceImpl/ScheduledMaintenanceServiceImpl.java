package com.axxera.ocpp.serviceImpl;

import com.axxera.ocpp.dao.GeneralDao;
import com.axxera.ocpp.model.ocpp.ScheduledMaintenance;
import com.axxera.ocpp.service.ScheduledMaintenanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ScheduledMaintenanceServiceImpl implements ScheduledMaintenanceService {

    @Autowired
    private GeneralDao<?, ?> generalDao;

    @Override
    public void deleteFromScheduledMaintenance(long portId) {
        try {
            String deleteQuery = "DELETE FROM ScheduledMaintenance WHERE portId = " + portId;
            generalDao.updateHqlQuiries(deleteQuery);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insertIntoScheduledMaintenance(long portId, long stationId, Date endTimeStamp) {
        try {
            ScheduledMaintenance sm = new ScheduledMaintenance();
            sm.setStnId(stationId);
            sm.setPortId(portId);
            sm.setEndTimeStamp(endTimeStamp);
            sm.setResponse("Inprogress");

            generalDao.save(sm);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateResponseInMaintenance(long portId, String response) {
        try {
            String updateQuery = "UPDATE ScheduledMaintenance SET response = '" + response + "' WHERE portId = " + portId;
            generalDao.updateHqlQuiries(updateQuery);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isInScheduledMaintenance(long portId) {
        try {
            String query = "SELECT COUNT(*) FROM scheduled_maintenance WHERE portId = " + portId + " AND response = 'Accepted'";
            String result = generalDao.getRecordBySql(query);
            int count = Integer.parseInt(result);
            return count > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
