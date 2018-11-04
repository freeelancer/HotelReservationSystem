/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author Wai Kin
 */
@Entity
public class RoomExceptionReportEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportId;
    private Date date;
    @OneToMany(mappedBy = "roomExceptionReportEntity")
    private List<AllocationExceptionEntity> firstExceptionList;
    @OneToMany(mappedBy = "roomExceptionReportEntity")
    private List<AllocationExceptionEntity> secondExceptionList;

    public RoomExceptionReportEntity() {
    }

    public RoomExceptionReportEntity(Date date, List<AllocationExceptionEntity> firstExceptionList, List<AllocationExceptionEntity> secondExceptionList) {
        this.date = date;
        this.firstExceptionList = firstExceptionList;
        this.secondExceptionList = secondExceptionList;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<AllocationExceptionEntity> getFirstExceptionList() {
        return firstExceptionList;
    }

    public void setFirstExceptionList(List<AllocationExceptionEntity> firstExceptionList) {
        this.firstExceptionList = firstExceptionList;
    }

    public List<AllocationExceptionEntity> getSecondExceptionList() {
        return secondExceptionList;
    }

    public void setSecondExceptionList(List<AllocationExceptionEntity> secondExceptionList) {
        this.secondExceptionList = secondExceptionList;
    }

    public Long getReportId() {
        return reportId;
    }

}
