/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.EmployeeEntity;
import java.util.List;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.EmployeeNotFoundException;
import util.exception.InvalidLoginCredentialException;

/**
 *
 * @author Lance
 */
@Stateless
@Local(EmployeeEntityControllerLocal.class)
@Remote(EmployeeEntityControllerRemote.class)
public class EmployeeEntityController implements EmployeeEntityControllerRemote, EmployeeEntityControllerLocal 
{
    @PersistenceContext(unitName = "HotelReservationSystem-ejbPU")
    private EntityManager em;

    public EmployeeEntityController() {
    }

    @Override
    public EmployeeEntity employeeLogin(String username, String password) throws InvalidLoginCredentialException
    {
        try
        {
            EmployeeEntity employeeEntity = retrieveEmployeeByUsername(username);
            
            if(employeeEntity.getPassword().equals(password))
            {             
                return employeeEntity;
            }
            else
            {
                throw new InvalidLoginCredentialException("Username does not exist or invalid password!");
            }
        }
        catch(EmployeeNotFoundException ex)
        {
            throw new InvalidLoginCredentialException("Username does not exist or invalid password!");
        }
        
    }
    @Override
    public EmployeeEntity retrieveEmployeeByUsername(String username) throws EmployeeNotFoundException
    {
        Query query = em.createQuery("SELECT s FROM EmployeeEntity s WHERE s.username = :inUsername");
        query.setParameter("inUsername", username);
        
        try
        {
            return (EmployeeEntity)query.getSingleResult();
        }
        catch(NoResultException | NonUniqueResultException ex)
        {
            throw new EmployeeNotFoundException("Employee Username " + username + " does not exist!");
        }
    }
    
    @Override
    public EmployeeEntity createNewEmployee(EmployeeEntity newEmployeeEntity)
    {
        em.persist(newEmployeeEntity);
        em.flush();
        return newEmployeeEntity;
    }
    
    @Override
    public List<EmployeeEntity> retrieveAllEmployees()
    {
       Query query = em.createQuery("SELECT e FROM EmployeeEntity e");
       return query.getResultList();
    }
    
}
