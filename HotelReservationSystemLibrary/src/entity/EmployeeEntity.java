/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import util.enumeration.EmployeeAccessRightsEnum;

/**
 *
 * @author Wai Kin
 */
@Entity
public class EmployeeEntity implements Serializable {

    @OneToOne(mappedBy = "employeeEntity")
    private ReservationEntity reservationEntity;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeId;
    @Column(length = 32, nullable = false)
    private String firstName;
    @Column(length = 32, nullable = false)
    private String lastName;
    @Column(length = 32, nullable = false, unique = true)
    private String username;
    @Column(length = 32, nullable = false, unique = false)
    private String password;
    @Enumerated(EnumType.STRING)
    private EmployeeAccessRightsEnum accessRightsEnum;

    public EmployeeEntity() {
    }

    public EmployeeEntity(ReservationEntity reservationEntity, String firstName, String lastName, String username, String password, EmployeeAccessRightsEnum accessRightEnum) {
        this.reservationEntity = reservationEntity;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.accessRightsEnum = accessRightEnum;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public EmployeeAccessRightsEnum getAccessRightsEnum() {
        return accessRightsEnum;
    }

    public void setAccessRightsEnum(EmployeeAccessRightsEnum accessRightEnum) {
        this.accessRightsEnum = accessRightEnum;
    }

    public ReservationEntity getReservationEntity() {
        return reservationEntity;
    }

    public void setReservationEntity(ReservationEntity reservationEntity) {
        this.reservationEntity = reservationEntity;
    }

    public Long getEmployeeId() {
        return employeeId;
    }
}
