/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.CustomerEntity;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.CustomerNotFoundException;
import util.exception.InvalidLoginCredentialException;

/**
 *
 * @author Wai Kin
 */
@Stateless
@Local (CustomerEntityControllerLocal.class)
@Remote (CustomerEntityControllerRemote.class)
public class CustomerEntityController implements CustomerEntityControllerRemote, CustomerEntityControllerLocal {

    @PersistenceContext(unitName = "HotelReservationSystem-ejbPU")
    private EntityManager em;

    public CustomerEntityController() {
    }

    @Override
    public CustomerEntity customerLogin(String username, String password) throws InvalidLoginCredentialException
    {
        try
        {
            CustomerEntity customerEntity = retrieveCustomerByUsername(username);
            
            if(customerEntity.getPassword().equals(password))
            {             
                return customerEntity;
            }
            else
            {
                throw new InvalidLoginCredentialException("Username does not exist or invalid password!");
            }
        }
        catch(CustomerNotFoundException ex)
        {
            throw new InvalidLoginCredentialException("Username does not exist or invalid password!");
        }
        
    }
    @Override
    public CustomerEntity retrieveCustomerByUsername(String username) throws CustomerNotFoundException
    {
        Query query = em.createQuery("SELECT s FROM CustomerEntity s WHERE s.username = :inUsername");
        query.setParameter("inUsername", username);
        
        try
        {
            return (CustomerEntity)query.getSingleResult();
        }
        catch(NoResultException | NonUniqueResultException ex)
        {
            throw new CustomerNotFoundException("Customer Username " + username + " does not exist!");
        }
    }
}