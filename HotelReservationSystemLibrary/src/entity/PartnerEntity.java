/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import util.enumeration.EmployeeAccessRightsEnum;
import util.enumeration.PartnerAccessRightsEnum;

/**
 *
 * @author Wai Kin
 */
@Entity
public class PartnerEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long partnerId;
    @Column(length = 32, nullable = false)
    private String firstName;
    @Column(length = 32, nullable = false)
    private String lastName;
    @Column(length = 32, nullable = false, unique = true)
    private String username;
    @Column(length = 32, nullable = false, unique = true)
    private String password;
    @Enumerated(EnumType.STRING)
    private PartnerAccessRightsEnum accessRightsEnum;
    @OneToMany(mappedBy = "partnerEntity")
    private List<ReservationEntity> reservationEntities;

    public PartnerEntity() {
    }

    public PartnerEntity(List<ReservationEntity> reservationEntities, String firstName, String lastName, String username, String password, PartnerAccessRightsEnum accessRightEnum) {
        this.reservationEntities = reservationEntities;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.accessRightsEnum = accessRightEnum;
    }

    public Long getPartnerId() {
        return partnerId;
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

    public PartnerAccessRightsEnum getAccessRightsEnum() {
        return accessRightsEnum;
    }

    public void setAccessRightsEnum(PartnerAccessRightsEnum accessRightsEnum) {
        this.accessRightsEnum = accessRightsEnum;
    }

    public List<ReservationEntity> getReservationEntities() {
        return reservationEntities;
    }

    public void setReservationEntities(List<ReservationEntity> reservationEntities) {
        this.reservationEntities = reservationEntities;
    }

}
