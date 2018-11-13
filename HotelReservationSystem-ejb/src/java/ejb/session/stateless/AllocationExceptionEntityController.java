/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.AllocationExceptionEntity;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Wai Kin
 */
@Stateless
@Local(AllocationExceptionEntityControllerLocal.class)
@Remote(AllocationExceptionEntityControllerRemote.class)
public class AllocationExceptionEntityController implements AllocationExceptionEntityControllerRemote, AllocationExceptionEntityControllerLocal {

    @PersistenceContext(unitName = "HotelReservationSystem-ejbPU")
    private EntityManager em;
    
    @Override
    public AllocationExceptionEntity createNewException(AllocationExceptionEntity newAllocationException){
        em.persist(newAllocationException);
        em.flush();
        em.refresh(newAllocationException);
        
        return newAllocationException;
    }

}
