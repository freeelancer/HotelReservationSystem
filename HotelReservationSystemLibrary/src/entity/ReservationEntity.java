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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author Wai Kin
 */
@Entity
public class ReservationEntity implements Serializable {

    @ManyToOne (optional = true)
    private CustomerEntity customerEntity;
    @OneToOne (optional = true)
    private EmployeeEntity employeeEntity;
    @ManyToOne (optional = true)
    private PartnerEntity partnerEntity;
    @ManyToOne (optional = true)
    private RoomRateEntity roomRateEntity;
    @OneToOne
    private RoomTypeEntity roomTypeEntity;
    @OneToMany(mappedBy = "reservationEntity")
    private List<RoomEntity> roomEntitys;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservationId;
    @Column(nullable=false)
    private Date checkInDate;
    @Column(nullable=false)
    private Date checkOutDate;
    private BigDecimal totalAmount;
    @OneToOne(mappedBy = "reservationEntity", optional=true)
    private AllocationExceptionEntity allocationExceptionEntity;
    private Integer numRooms;

    public ReservationEntity() {
    }

    public ReservationEntity(CustomerEntity customerEntity, EmployeeEntity employeeEntity, PartnerEntity partnerEntity, RoomRateEntity roomRateEntity, RoomTypeEntity roomTypeEntity, List<RoomEntity> roomEntitys, Date checkInDate, Date checkOutDate, BigDecimal totalAmount, AllocationExceptionEntity allocationExceptionEntity, Integer numRooms) {
        this.customerEntity = customerEntity;
        this.employeeEntity = employeeEntity;
        this.partnerEntity = partnerEntity;
        this.roomRateEntity = roomRateEntity;
        this.roomTypeEntity = roomTypeEntity;
        this.roomEntitys = roomEntitys;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.totalAmount = totalAmount;
        this.allocationExceptionEntity = allocationExceptionEntity;
        this.numRooms = numRooms;
    }
    
    public Integer getNumRooms() {
        return numRooms;
    }

    public void setNumRooms(Integer numRooms) {
        this.numRooms = numRooms;
    }

    public AllocationExceptionEntity getAllocationExceptionEntity() {
        return allocationExceptionEntity;
    }

    public void setAllocationExceptionEntity(AllocationExceptionEntity allocationExceptionEntity) {
        this.allocationExceptionEntity = allocationExceptionEntity;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(Date checkInDate) {
        this.checkInDate = checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(Date checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
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

    public List<RoomEntity> getRoomEntitys() {
        return roomEntitys;
    }

    public void setRoomEntitys(List<RoomEntity> roomEntitys) {
        this.roomEntitys = roomEntitys;
    }

    public Long getReservationId() {
        return reservationId;
    }

    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
    }
}
