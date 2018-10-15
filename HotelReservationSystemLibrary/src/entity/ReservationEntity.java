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
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 *
 * @author Wai Kin
 */
@Entity
public class ReservationEntity implements Serializable {

    @ManyToOne
    private CustomerEntity customerEntity;
    @OneToOne (optional = true)
    private EmployeeEntity employeeEntity;
    @OneToOne (optional = true)
    private PartnerEntity partnerEntity;
    @OneToOne
    private RoomRateEntity roomRateEntity;
    @OneToOne
    private RoomTypeEntity roomTypeEntity;
    @OneToOne
    private RoomEntity roomEntity;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservationId;

    public ReservationEntity() {
    }

    public ReservationEntity(CustomerEntity customerEntity, EmployeeEntity employeeEntity, PartnerEntity partnerEntity, RoomRateEntity roomRateEntity, RoomTypeEntity roomTypeEntity, RoomEntity roomEntity) {
        this.customerEntity = customerEntity;
        this.employeeEntity = employeeEntity;
        this.partnerEntity = partnerEntity;
        this.roomRateEntity = roomRateEntity;
        this.roomTypeEntity = roomTypeEntity;
        this.roomEntity = roomEntity;
    }

    public Long getReservationId() {
        return reservationId;
    }

    public CustomerEntity getCustomerEntity() {
        return customerEntity;
    }

    public void setCustomerEntity(CustomerEntity customerEntity) {
        this.customerEntity = customerEntity;
    }

    public EmployeeEntity getEmployeeEntity() {
        return employeeEntity;
    }

    public void setEmployeeEntity(EmployeeEntity employeeEntity) {
        this.employeeEntity = employeeEntity;
    }

    public PartnerEntity getPartnerEntity() {
        return partnerEntity;
    }

    public void setPartnerEntity(PartnerEntity partnerEntity) {
        this.partnerEntity = partnerEntity;
    }

    public RoomRateEntity getRoomRateEntity() {
        return roomRateEntity;
    }

    public void setRoomRateEntity(RoomRateEntity roomRateEntity) {
        this.roomRateEntity = roomRateEntity;
    }

    public RoomTypeEntity getRoomTypeEntity() {
        return roomTypeEntity;
    }

    public void setRoomTypeEntity(RoomTypeEntity roomTypeEntity) {
        this.roomTypeEntity = roomTypeEntity;
    }

    public RoomEntity getRoomEntity() {
        return roomEntity;
    }

    public void setRoomEntity(RoomEntity roomEntity) {
        this.roomEntity = roomEntity;
    }
}
