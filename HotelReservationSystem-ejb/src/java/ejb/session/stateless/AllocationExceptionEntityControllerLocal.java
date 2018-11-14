/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.AllocationExceptionEntity;
import javax.ejb.Local;

/**
 *
 * @author Wai Kin
 */
public interface AllocationExceptionEntityControllerLocal {

    public AllocationExceptionEntity createNewException(AllocationExceptionEntity newAllocationException);
    
}
