/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 *
 * @author Wai Kin
 */
@Entity
public class AllocationExceptionEntity implements Serializable {

    @ManyToOne(optional=false)
    private RoomExceptionReportEntity roomExceptionReportEntity;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long exceptionId;
    @OneToOne
    private RoomTypeEntity reservedRoomType;
    @JoinColumn(nullable=true)
    @OneToOne
    private RoomTypeEntity allocatedRoomType;
    @OneToOne(optional=false)
    private ReservationEntity reservationEntity;

    public AllocationExceptionEntity() {
    }

    public AllocationExceptionEntity(RoomTypeEntity reservedRoomType, RoomTypeEntity allocatedRoomType, ReservationEntity reservationEntity) {
        this.reservedRoomType = reservedRoomType;
        this.allocatedRoomType = allocatedRoomType;
        this.reservationEntity = reservationEntity;
    }

    public ReservationEntity getReservationEntity() {
        return reservationEntity;
    }

    public void setReservationEntity(ReservationEntity reservationEntity) {
        this.reservationEntity = reservationEntity;
    }

    public RoomExceptionReportEntity getRoomExceptionReportEntity() {
        return roomExceptionReportEntity;
    }

    public void setRoomExceptionReportEntity(RoomExceptionReportEntity roomExceptionReportEntity) {
        this.roomExceptionReportEntity = roomExceptionReportEntity;
    }

    public RoomTypeEntity getReservedRoomType() {
        return reservedRoomType;
    }

    public void setReservedRoomType(RoomTypeEntity reservedRoomType) {
        this.reservedRoomType = reservedRoomType;
    }

    public RoomTypeEntity getAllocatedRoomType() {
        return allocatedRoomType;
    }

    public void setAllocatedRoomType(RoomTypeEntity allocatedRoomType) {
        this.allocatedRoomType = allocatedRoomType;
    }
    
    public Long getExceptionId() {
        return exceptionId;
    }

    public void setExceptionId(Long exceptionId) {
        this.exceptionId = exceptionId;
    }

}
