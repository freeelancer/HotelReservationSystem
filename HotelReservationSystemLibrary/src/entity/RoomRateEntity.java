/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import util.enumeration.RateTypeEnum;

/**
 *
 * @author Wai Kin
 */
@Entity
public class RoomRateEntity implements Serializable {

    @OneToOne(mappedBy = "roomRateEntity")
    private ReservationEntity reservationEntity;
    @OneToOne
    private RoomTypeEntity roomTypeEntity;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomRateId;
    @Column(length = 32, nullable = false)
    private String name;
    @Enumerated(EnumType.STRING)
    private RateTypeEnum rateTypeEnum;
    @Column(nullable = false)
    private BigDecimal ratePerNight;
    @Column(nullable = false)
    private Timestamp[] validityPeriod; 

    public RoomRateEntity() {
    }

    public RoomRateEntity(ReservationEntity reservationEntity, RoomTypeEntity roomTypeEntity, String name, RateTypeEnum rateTypeEnum, BigDecimal ratePerNight, Timestamp[] validityPeriod) {
        this.reservationEntity = reservationEntity;
        this.roomTypeEntity = roomTypeEntity;
        this.name = name;
        this.rateTypeEnum = rateTypeEnum;
        this.ratePerNight = ratePerNight;
        this.validityPeriod = validityPeriod;
    }

    public Long getRoomRateId() {
        return roomRateId;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RateTypeEnum getRateTypeEnum() {
        return rateTypeEnum;
    }

    public void setRateTypeEnum(RateTypeEnum rateTypeEnum) {
        this.rateTypeEnum = rateTypeEnum;
    }

    public BigDecimal getRatePerNight() {
        return ratePerNight;
    }

    public void setRatePerNight(BigDecimal ratePerNight) {
        this.ratePerNight = ratePerNight;
    }

    public Timestamp[] getValidityPeriod() {
        return validityPeriod;
    }

    public void setValidityPeriod(Timestamp[] validityPeriod) {
        this.validityPeriod = validityPeriod;
    }
}
