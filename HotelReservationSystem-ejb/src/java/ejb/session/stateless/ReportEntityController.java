/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.RoomExceptionReportEntity;
import java.util.Date;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Wai Kin
 */
@Stateless
@Local(ReportEntityControllerLocal.class)
@Remote(ReportEntityControllerRemote.class)
public class ReportEntityController implements ReportEntityControllerRemote, ReportEntityControllerLocal {

    @PersistenceContext(unitName = "HotelReservationSystem-ejbPU")
    private EntityManager em;

    @Override
    public RoomExceptionReportEntity createNewReport(RoomExceptionReportEntity newReport){
        em.persist(newReport);
        em.flush();
        em.refresh(newReport);
        
        return newReport;
    }
    
    @Override
    public RoomExceptionReportEntity retrieveReportByDate(Date date){
        Query query = em.createQuery("SELECT r FROM RoomExceptionReportEntity r WHERE r.date = :inDate");
        query.setParameter("inDate", date);
        
        RoomExceptionReportEntity report = (RoomExceptionReportEntity) query.getSingleResult();
        report.getFirstExceptionList().size();
        report.getSecondExceptionList().size();
        return report;
    }
}
