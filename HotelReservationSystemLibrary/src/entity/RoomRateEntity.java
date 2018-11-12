/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import util.enumeration.RateTypeEnum;

/**
 *
 * @author Wai Kin
 */
@Entity
public class RoomRateEntity implements Serializable {

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
    private List<Date> validityPeriod; 
    @OneToMany(mappedBy = "roomRateEntity")
    private List<ReservationEntity> reservation;
    @ManyToOne(optional=false)
    private RoomTypeEntity roomType;

    public RoomRateEntity() {
    }

    public RoomRateEntity(RoomTypeEntity roomTypeEntity, String name, RateTypeEnum rateTypeEnum, BigDecimal ratePerNight, List<Date> validityPeriod, List<ReservationEntity> reservationEntities) {
        this.roomType = roomTypeEntity;
        this.name = name;
        this.rateTypeEnum = rateTypeEnum;
        this.ratePerNight = ratePerNight;
        this.validityPeriod = validityPeriod;
        this.reservation = reservationEntities;
    }

    public RoomTypeEntity getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomTypeEntity roomType) {
        this.roomType = roomType;
    }

    public Long getRoomRateId() {
        return roomRateId;
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

    public List<Date> getValidityPeriod() {
        return validityPeriod;
    }

    public void setValidityPeriod(List<Date> validityPeriod) {
        this.validityPeriod = validityPeriod;
    }

    public List<ReservationEntity> getReservation() {
        return reservation;
    }

    public void setReservation(List<ReservationEntity> reservation) {
        this.reservation = reservation;
    }
    
}
