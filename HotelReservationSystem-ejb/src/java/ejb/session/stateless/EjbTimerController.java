/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.AllocationExceptionEntity;
import entity.ReservationEntity;
import entity.RoomEntity;
import entity.RoomExceptionReportEntity;
import entity.RoomTypeEntity;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Schedule;
import javax.ejb.Stateless;

/**
 *
 * @author Wai Kin
 */
@Stateless
@Local(EjbTimerControllerLocal.class)
@Remote(EjbTimerControllerRemote.class)
public class EjbTimerController implements EjbTimerControllerRemote, EjbTimerControllerLocal {

    @EJB
    ReservationEntityControllerLocal reservationEntityController;
    
    @EJB
    RoomEntityControllerLocal roomEntityController;
    
    @EJB
    RoomTypeEntityControllerLocal roomTypeEntityController;
    
    @EJB
    AllocationExceptionEntityControllerLocal allocationExceptionEntityController;
    
    @EJB
    ReportEntityControllerLocal reportEntityController;
    
    @Schedule(hour = "2", info = "allocateRoom")
    @Override
    public void allocateRoom(){
        
        System.out.println("Allocate Room Function Triggered!");
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String todayStr = dateFormat.format(new Date());
        Date today = new Date();
        try {
            today = dateFormat.parse(todayStr);
        } catch (Exception e){
            e.printStackTrace();
        }
        List<ReservationEntity> reservations = reservationEntityController.retrieveAllReservationsForToday();
        List<RoomEntity> rooms = roomEntityController.retrieveAllRooms();
        for(RoomEntity room:rooms)
        {
            if(room.getReservationEntity() != null){
                if(!room.getReservationEntity().getCheckOutDate().after(today)){
                    room.setAllocated(Boolean.FALSE);
                }
            }
        }

        List<AllocationExceptionEntity> firstExceptions = new ArrayList<AllocationExceptionEntity>();
        List<AllocationExceptionEntity> secondExceptions = new ArrayList<AllocationExceptionEntity>();
        
        System.out.println("--- Allocation ---");
        System.out.printf("%10s%10s\n", "Resv Num", "Room Num");
        
        for(ReservationEntity reservation:reservations){
            //System.out.println("Processing reservation: " + reservation.getReservationId());
            RoomEntity roomToAllocate = roomEntityController.retrieveAvailableRoomByRoomType(reservation.getRoomTypeEntity());
            RoomTypeEntity roomType = reservation.getRoomTypeEntity();
            if(roomToAllocate == null){
                while(true){
                    roomType = roomTypeEntityController.getNextHigherRoomType(roomType);
                    if (roomType == null){
                        AllocationExceptionEntity allocationException = new AllocationExceptionEntity();
                        allocationException.setReservationEntity(reservation);
                        allocationException = allocationExceptionEntityController.createNewException(allocationException); 
                        secondExceptions.add(allocationException);
                        roomToAllocate = null;
                        break;
                    }
                    roomToAllocate = roomEntityController.retrieveAvailableRoomByRoomType(roomType);
                    if (roomToAllocate != null){
                        AllocationExceptionEntity allocationException = new AllocationExceptionEntity();
                        allocationException.setReservedRoomType(reservation.getRoomTypeEntity());
                        allocationException.setAllocatedRoomType(roomType);
                        allocationException.setReservationEntity(reservation);
                        allocationException = allocationExceptionEntityController.createNewException(allocationException); 
                        firstExceptions.add(allocationException);
                        break;
                    }
                }    
            }
            if (roomToAllocate != null){
                roomToAllocate.setAllocated(Boolean.TRUE);
                System.out.printf("%10s%10s\n", reservation.getReservationId(), roomToAllocate.getRoomNumber());
            }
        }
        RoomExceptionReportEntity newReport = new RoomExceptionReportEntity();
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        try {
            newReport.setDate(df.parse(df.format(new Date())));
            newReport.setFirstExceptionList(firstExceptions);
            newReport.setSecondExceptionList(secondExceptions);
            newReport = reportEntityController.createNewReport(newReport);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
