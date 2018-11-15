/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.DateEntity;
import entity.RoomTypeEntity;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.RoomNotFoundException;
import util.exception.RoomTypeAlreadyDisabledException;
import util.exception.RoomTypeNotFoundException;
import util.exception.RoomTypeStillUsedException;

/**
 *
 * @author Lance
 */
@Stateless
@Local(RoomTypeEntityControllerLocal.class)
@Remote(RoomTypeEntityControllerRemote.class)
public class RoomTypeEntityController implements RoomTypeEntityControllerRemote, RoomTypeEntityControllerLocal 
{

    @PersistenceContext(unitName = "HotelReservationSystem-ejbPU")
    private EntityManager em;
    
    @EJB
    DateEntityControllerLocal dateEntityController;

    public RoomTypeEntityController() {
    }
    
    @Override
    public RoomTypeEntity createNewRoomType(RoomTypeEntity newRoomTypeEntity)
    {
//        Deal with this later
        em.persist(newRoomTypeEntity);
        em.flush();
        em.refresh(newRoomTypeEntity);
        List<DateEntity> dates = new ArrayList<DateEntity>();
        Date start = new Date();
        Date end = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(start);
        calendar.add(Calendar.YEAR, 1);
        end = calendar.getTime();
        try {
            newRoomTypeEntity = retrieveRoomTypeByName(newRoomTypeEntity.getName());

            for(Date current = start; current.before(end); ){
                DateEntity date = new DateEntity(current, newRoomTypeEntity);
                date = dateEntityController.createNewDate(date);
                dates.add(date);

                calendar = Calendar.getInstance();
                calendar.setTime(current);
                calendar.add(Calendar.DATE, 1);
                current = calendar.getTime();
            }
        } catch (RoomTypeNotFoundException e) {
            e.printStackTrace();
        }

        newRoomTypeEntity.setDates(dates);
        
        return newRoomTypeEntity;
    }
    
    @Override
    public RoomTypeEntity retrieveRoomTypeByName(String roomTypeName) throws RoomTypeNotFoundException
    {
        Query query = em.createQuery("SELECT r FROM RoomTypeEntity r WHERE r.name = :inRoomTypeName");
        query.setParameter("inRoomTypeName", roomTypeName);
        
        try
        {
            RoomTypeEntity roomType = (RoomTypeEntity)query.getSingleResult();
            roomType.getRoomRateEntities().size();
            return roomType;
        }
        catch(NoResultException | NonUniqueResultException ex)
        {
            throw new RoomTypeNotFoundException("Room Type " + roomTypeName + " does not exist!");
        }
    }
    
    @Override
    public void updateRoomType(RoomTypeEntity roomType)
    {   
        RoomTypeEntity roomTypeToUpdate = retrieveRoomTypeById(roomType.getRoomTypeId());
        roomTypeToUpdate.setAmenities(roomType.getAmenities());
        roomTypeToUpdate.setBedTypeEnum(roomType.getBedTypeEnum());
        roomTypeToUpdate.setCapacity(roomType.getCapacity());
        roomTypeToUpdate.setDescription(roomType.getDescription());
        roomTypeToUpdate.setName(roomType.getName()); 
        roomTypeToUpdate.setSize(roomType.getSize());
        roomTypeToUpdate.setRoomRateEntities(roomType.getRoomRateEntities());
        em.merge(roomTypeToUpdate);
    }
    
    private RoomTypeEntity retrieveRoomTypeById(Long roomTypeId)
    {
//        Query query = em.createQuery("SELECT rt FROM RoomTypeEntity rt WHERE rt.roomTypeId = :inRoomTypeId");
//        query.setParameter("inRoomTypeId", roomTypeId);
//        RoomTypeEntity roomType = (RoomTypeEntity) query.getSingleResult();
        RoomTypeEntity roomType = em.find(RoomTypeEntity.class, roomTypeId);
        
        return roomType;
    }
    
    @Override
    public void deleteRoomType(RoomTypeEntity roomType) throws RoomTypeStillUsedException, RoomTypeAlreadyDisabledException
    {
        RoomTypeEntity roomTypeToDelete = retrieveRoomTypeById(roomType.getRoomTypeId());
        if(roomTypeToDelete.getRoomEntities().size()==0)
        {
            em.remove(roomTypeToDelete);
        }
        else if(roomTypeToDelete.getUsed()==Boolean.TRUE)
        {
            roomTypeToDelete.setDisable(Boolean.TRUE);
            throw new RoomTypeStillUsedException("Room Type "+roomType.getName()+" currently still has rooms with such Room Type. As such it is disabled from further use but not deleted\n");
        }
        else
        {
            throw new RoomTypeAlreadyDisabledException("Room Type "+roomType.getName()+" currently still has rooms with such Room Type and is already disabled\n");
        }
    }
    
    @Override
    public List<RoomTypeEntity> retrieveAllRoomTypes()
    {
        Query query = em.createQuery("SELECT r FROM RoomTypeEntity r");
        return query.getResultList();
    }
    
    @Override
    public List<Date> checkAvailability(Date checkInDate, Date checkOutDate, RoomTypeEntity roomType){
        
        List<Date> unavailableDates = new ArrayList<Date>();
        RoomTypeEntity roomTypeEntity = retrieveRoomTypeById(roomType.getRoomTypeId()); 
        roomTypeEntity.getRoomEntities().size();
        
        for(Date current=checkInDate; current.before(checkOutDate); ){
            
            Query query = em.createQuery("SELECT d FROM DateEntity d WHERE d.dateTime = :inCurrent AND d.roomTypeEntity.roomTypeId = :inRoomTypeId");
            query.setParameter("inCurrent", current);
            query.setParameter("inRoomTypeId", roomType.getRoomTypeId());
            
            DateEntity date = (DateEntity)query.getSingleResult();
            if(date.getNumReserved() >= roomType.getRoomEntities().size()){
                unavailableDates.add(current);
            }
            
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(current);
            calendar.add(Calendar.DATE, 1);
            current = calendar.getTime();
        }
        
        return new ArrayList<Date>();
    }
    
    @Override
    public RoomTypeEntity getNextHigherRoomType(RoomTypeEntity roomType){
        List<RoomTypeEntity> roomTypes = retrieveAllRoomTypes();
        BigDecimal currentRate = roomType.getRoomRateEntities().get(0).getRatePerNight();
        RoomTypeEntity nextHigherRoomType = new RoomTypeEntity();
        
        for(RoomTypeEntity nextRoomType:roomTypes){
            BigDecimal nextRoomRate = nextRoomType.getRoomRateEntities().get(0).getRatePerNight();
            if(nextRoomRate.compareTo(currentRate) == 1){
                if (nextHigherRoomType.getName() == null){
                    nextHigherRoomType = nextRoomType;
                } else {
                    if (nextRoomRate.compareTo(nextHigherRoomType.getRoomRateEntities().get(0).getRatePerNight()) == -1){
                        nextHigherRoomType = nextRoomType;
                    }
                }
            }
        }
        
        return nextHigherRoomType;
    }
}
