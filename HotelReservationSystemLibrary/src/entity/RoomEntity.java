/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 *
 * @author Wai Kin
 */
@Entity
public class RoomEntity implements Serializable {

    @OneToOne(mappedBy = "roomEntity", optional=true)
    private ReservationEntity reservationEntity;
    @ManyToOne (optional=false)
    private RoomTypeEntity roomTypeEntity;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomId;
    @Column(length = 10, nullable = false, unique = true)
    private String roomNumber;
    @Column(nullable = false)
    
    private Boolean usable=false;
    @Column(nullable = false)
    private Boolean disabled=false;
    @Column(nullable = false)
    private Boolean occupied=false;
    @Column(nullable = false)
    private Boolean allocated=false;

    public RoomEntity() {
    }

    public RoomEntity(ReservationEntity reservationEntity, RoomTypeEntity roomTypeEntity, String roomNumber, Boolean used, Boolean disabled, Boolean occupied, Boolean allocated) {
        this.reservationEntity = reservationEntity;
        this.roomTypeEntity = roomTypeEntity;
        this.roomNumber = roomNumber;
        this.usable = used;
        this.disabled = disabled;
        this.occupied = occupied;
        this.allocated = allocated;
    }

    public Boolean getOccupied() {
        return occupied;
    }

    public void setOccupied(Boolean occupied) {
        this.occupied = occupied;
    }

    public Boolean getAllocated() {
        return allocated;
    }

    public void setAllocated(Boolean allocated) {
        this.allocated = allocated;
    }
    
    public Long getRoomId() {
        return roomId;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public ReservationEntity getReservationEntity() {
        return reservationEntity;
    }

    public void setReservationEntity(ReservationEntity reservationEntity) {
        this.reservationEntity = reservationEntity;
    }

    public RoomTypeEntity getRoomTypeEntity() {
        return roomTypeEntity;
    }

    public void setRoomTypeEntity(RoomTypeEntity roomTypeEntity) {
        this.roomTypeEntity = roomTypeEntity;
    }

    public Boolean getUsable() {
        return usable;
    }

    public void setUsable(Boolean usable) {
        this.usable = usable;
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }
}
