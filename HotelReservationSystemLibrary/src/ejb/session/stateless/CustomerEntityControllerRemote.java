/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.CustomerEntity;
import java.util.List;
import util.exception.UsernameExistException;
import util.exception.CustomerNotFoundException;
import util.exception.InvalidLoginCredentialException;

/**
 *
 * @author Wai Kin
 */
public interface CustomerEntityControllerRemote {
    
    public CustomerEntity customerLogin(String username, String password) throws InvalidLoginCredentialException;

    public CustomerEntity retrieveCustomerByUsername(String username) throws CustomerNotFoundException;

    public List<CustomerEntity> retrieveAllCustomers();

    public CustomerEntity createNewCustomer(CustomerEntity newCustomerEntity);

    public void updateCustomer(CustomerEntity customerEntity);

    public void deleteCustomer(String username) throws CustomerNotFoundException;
    
}
