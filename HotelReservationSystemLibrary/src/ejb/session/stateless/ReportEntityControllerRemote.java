/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.RoomExceptionReportEntity;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Wai Kin
 */
public interface ReportEntityControllerRemote {

    public RoomExceptionReportEntity retrieveReportByDate(Date date);

    public RoomExceptionReportEntity retrieveReportById(Long reportId);

    public List<RoomExceptionReportEntity> retrieveAllReports();
}
