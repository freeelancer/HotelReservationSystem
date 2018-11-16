/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.RoomExceptionReportEntity;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;

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
        query.setParameter("inDate", date, TemporalType.DATE);
        
        RoomExceptionReportEntity report = (RoomExceptionReportEntity) query.getSingleResult();
        
        report.getFirstExceptionList().size();
        report.getSecondExceptionList().size();
        return report;
    }
    
    @Override
    public RoomExceptionReportEntity retrieveReportById(Long reportId){
        Query query = em.createQuery("SELECT r FROM RoomExceptionReportEntity r WHERE r.reportId = :inReportId");
        query.setParameter("inReportId", reportId);
        
        return (RoomExceptionReportEntity)query.getSingleResult();
    }
    
    @Override
    public List<RoomExceptionReportEntity> retrieveAllReports(){
        Query query = em.createQuery("SELECT r FROM RoomExceptionReportEntity r");
        return query.getResultList();
    }
    
    @Override
    public void updateReport(RoomExceptionReportEntity newReport){
        em.merge(newReport);
    }
}
