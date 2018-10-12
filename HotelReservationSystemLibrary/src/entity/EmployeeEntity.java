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
    private Long id;
    @Column(length = 32, nullable = false)
    private String firstName;
    @Column(length = 32, nullable = false)
    private String lastName;
    @Column(length = 32, nullable = false, unique = true)
    private String username;
    @Column(length = 32, nullable = false, unique = true)
    private String password;
    @Enumerated(EnumType.STRING)
    private EmployeeAccessRightsEnum accessRightEnum;

    public EmployeeEntity() {
    }

    public EmployeeEntity(ReservationEntity reservationEntity, String firstName, String lastName, String username, String password, EmployeeAccessRightsEnum accessRightEnum) {
        this.reservationEntity = reservationEntity;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.accessRightEnum = accessRightEnum;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public EmployeeAccessRightsEnum getAccessRightEnum() {
        return accessRightEnum;
    }

    public void setAccessRightEnum(EmployeeAccessRightsEnum accessRightEnum) {
        this.accessRightEnum = accessRightEnum;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EmployeeEntity)) {
            return false;
        }
        EmployeeEntity other = (EmployeeEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.EmployeeEntity[ id=" + id + " ]";
    }
    
}
