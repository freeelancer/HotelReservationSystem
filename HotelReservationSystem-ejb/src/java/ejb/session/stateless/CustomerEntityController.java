/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.CustomerEntity;
import entity.ReservationEntity;
import java.util.ArrayList;
import java.util.List;
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
import util.exception.ReservationNotFoundException;

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
    public CustomerEntity createNewCustomer(CustomerEntity newCustomerEntity)
    {
        em.persist(newCustomerEntity);
        em.flush();
        
        return newCustomerEntity;
    }
    
    @Override
    public List<CustomerEntity> retrieveAllCustomers()
    {
        Query query = em.createQuery("SELECT c FROM CustomerEntity c");
        
        return query.getResultList();
    }
    
    @Override
    public void updateCustomer(CustomerEntity customerEntity)
    {
        em.merge(customerEntity);
    }
    
    @Override
    public void deleteCustomer(String username) throws CustomerNotFoundException
    {
        CustomerEntity customerEntityToRemove = retrieveCustomerByUsername(username);
        em.remove(customerEntityToRemove);
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
            throw new CustomerNotFoundException("Customer username " + username + " does not exist!");
        }
    }
    
    @Override
    public List<ReservationEntity> retrieveAllReservations(Long customerId) throws ReservationNotFoundException
    {
        Query query = em.createQuery("SELECT c FROM CustomerEntity c WHERE c.customerId = :inStudentId");
        query.setParameter(":inCustomerId", customerId);
        CustomerEntity customer = (CustomerEntity) query.getSingleResult();
        
        List<ReservationEntity> reservations = customer.getReservationEntities();

        if(reservations.size() != 0)
        {
            return reservations;
        } 
        else 
        {
            throw new ReservationNotFoundException("There are no reservations!");
        }
        
    }
}
