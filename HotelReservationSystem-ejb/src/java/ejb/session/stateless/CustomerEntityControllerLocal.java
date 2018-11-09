/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.CustomerEntity;
import entity.ReservationEntity;
import java.util.List;
import util.exception.CustomerNotFoundException;
import util.exception.InvalidLoginCredentialException;
import util.exception.ReservationNotFoundException;

/**
 *
 * @author Wai Kin
 */
public interface CustomerEntityControllerLocal {

    public CustomerEntity customerLogin(String username, String password) throws InvalidLoginCredentialException;

    public CustomerEntity retrieveCustomerByUsername(String username) throws CustomerNotFoundException;

    public List<CustomerEntity> retrieveAllCustomers();

    public CustomerEntity createNewCustomer(CustomerEntity newCustomerEntity);

    public void updateCustomer(CustomerEntity customerEntity);

    public void deleteCustomer(String username) throws CustomerNotFoundException;
    
    public List<ReservationEntity> retrieveAllReservations(Long customerId) throws ReservationNotFoundException;
}
