/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.DateEntity;
import java.util.List;
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
@Local(DateEntityControllerLocal.class)
@Remote(DateEntityControllerRemote.class)
public class DateEntityController implements DateEntityControllerRemote, DateEntityControllerLocal {

    @PersistenceContext(unitName = "HotelReservationSystem-ejbPU")
    private EntityManager em;

    @Override
    public DateEntity createNewDate(DateEntity date){
        em.persist(date);
        em.flush();
        em.refresh(date);
        return date; 
    }
    
    @Override
    public List<DateEntity> retrieveAllDateEntitysForRoomTypeId(Long roomTypeId)
    {
        Query query = em.createQuery("SELECT d FROM DateEntity d WHERE d.roomTypeEntity.roomTypeId = :inRoomTypeId");
        query.setParameter("inRoomTypeId", roomTypeId);
        return query.getResultList();
    }
}
