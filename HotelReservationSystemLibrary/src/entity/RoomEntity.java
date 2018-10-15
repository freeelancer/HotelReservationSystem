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

    @OneToOne(mappedBy = "roomEntity")
    private ReservationEntity reservationEntity;
    @ManyToOne
    private RoomTypeEntity roomTypeEntity;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomId;
    @Column(length = 10, nullable = false, unique = true)
    private Integer roomNumber;
    @Column(nullable = false)
    private Boolean used;
    @Column(nullable = false)
    private Boolean disabled;

    public RoomEntity() {
    }

    public RoomEntity(ReservationEntity reservationEntity, RoomTypeEntity roomTypeEntity, Integer roomNumber, Boolean used, Boolean disabled) {
        this.reservationEntity = reservationEntity;
        this.roomTypeEntity = roomTypeEntity;
        this.roomNumber = roomNumber;
        this.used = used;
        this.disabled = disabled;
    } 

    public Long getRoomId() {
        return roomId;
    }

    public Integer getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(Integer roomNumber) {
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

    public Boolean getUsed() {
        return used;
    }

    public void setUsed(Boolean used) {
        this.used = used;
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }
}
