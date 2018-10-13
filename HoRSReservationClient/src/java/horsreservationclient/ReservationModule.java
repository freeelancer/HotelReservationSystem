/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package horsreservationclient;

import ejb.session.stateless.CustomerEntityControllerRemote;
import entity.CustomerEntity;

/**
 *
 * @author Wai Kin
 */
public class ReservationModule {
    
    private CustomerEntity currentCustomerEntity;
    private CustomerEntityControllerRemote customerEntityController;

    public ReservationModule() {
    }

    public ReservationModule(CustomerEntity currentCustomerEntity, CustomerEntityControllerRemote customerEntityController) {
        this.currentCustomerEntity = currentCustomerEntity;
        this.customerEntityController = customerEntityController;
    }
   
}
