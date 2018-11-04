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
import util.enumeration.BedTypeEnum;

/**
 *
 * @author Wai Kin
 */
@Entity
public class RoomTypeEntity implements Serializable {

    @OneToMany(mappedBy = "roomTypeEntity")
    private List<RoomEntity> roomEntities;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomTypeId;
    @Column(length = 32, nullable = false, unique = true)
    private String name;
    @Column(length = 500, nullable = false)
    private String description;
    @Column(nullable = false)
    private Integer size;
    @Enumerated(EnumType.STRING)
    private BedTypeEnum bedTypeEnum;
    @Column(length = 1, nullable = false)
    private Integer capacity;
    @Column(length = 100, nullable = false)
    private String amenities;
    @Column(nullable = false)
    private Boolean used = true;
    @OneToMany(mappedBy = "roomTypeEntity")
    private List<DateEntity> dateEntities;
    @Column(nullable = false)
    private Boolean disable = false;
    @OneToMany(mappedBy = "roomTypeEntity")
    private List<DateEntity> dates;

    public RoomTypeEntity() {
    }

    public RoomTypeEntity(List<RoomEntity> roomEntities, String name, String description, Integer size, BedTypeEnum bedTypeEnum, Integer capacity, String amenities) {
        this.roomEntities = roomEntities;
        this.name = name;
        this.description = description;
        this.size = size;
        this.bedTypeEnum = bedTypeEnum;
        this.capacity = capacity;
        this.amenities = amenities;
    }

    public List<DateEntity> getDates() {
        return dates;
    }

    public void setDates(List<DateEntity> dates) {
        this.dates = dates;
    }

    public Long getRoomTypeId() {
        return roomTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public BedTypeEnum getBedTypeEnum() {
        return bedTypeEnum;
    }

    public void setBedTypeEnum(BedTypeEnum bedTypeEnum) {
        this.bedTypeEnum = bedTypeEnum;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getAmenities() {
        return amenities;
    }

    public void setAmenities(String amenities) {
        this.amenities = amenities;
    }

    public Boolean getUsed() {
        return used;
    }

    public void setUsed(Boolean used) {
        this.used = used;
    }

    public Boolean getDisable() {
        return disable;
    }

    public void setDisable(Boolean disable) {
        this.disable = disable;
    }

    public List<RoomEntity> getRoomEntities() {
        return roomEntities;
    }

    public void setRoomEntities(List<RoomEntity> roomEntities) {
        this.roomEntities = roomEntities;
    }
}
