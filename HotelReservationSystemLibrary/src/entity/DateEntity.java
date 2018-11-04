/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;

/**
 *
 * @author Wai Kin
 */
@Entity
public class DateEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dateId;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateTime;
    
    @ManyToOne(optional=false)
    private RoomTypeEntity roomTypeEntity;
    private Integer numReserved=0;

    public DateEntity() {
    }

    public DateEntity(Date dateTime, RoomTypeEntity roomTypeEntity) {
        this.dateTime = dateTime;
        this.roomTypeEntity = roomTypeEntity;
    }

    public Integer getNumReserved() {
        return numReserved;
    }

    public void setNumReserved(Integer numReserved) {
        this.numReserved = numReserved;
    }

    public Long getDateId() {
        return dateId;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public RoomTypeEntity getRoomTypeEntity() {
        return roomTypeEntity;
    }

    public void setRoomTypeEntity(RoomTypeEntity roomTypeEntity) {
        this.roomTypeEntity = roomTypeEntity;
    }
    
}
