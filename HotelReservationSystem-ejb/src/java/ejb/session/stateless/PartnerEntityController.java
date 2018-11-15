/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.PartnerEntity;
import entity.ReservationEntity;
import java.util.List;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.InvalidLoginCredentialException;
import util.exception.PartnerNotFoundException;
import util.exception.ReservationNotFoundException;

/**
 *
 * @author Lance
 */
@Stateless
@Local(PartnerEntityControllerLocal.class)
@Remote(PartnerEntityControllerRemote.class)
public class PartnerEntityController implements PartnerEntityControllerRemote, PartnerEntityControllerLocal 
{
    @PersistenceContext(unitName = "HotelReservationSystem-ejbPU")
    private EntityManager em;

    @Override
    public PartnerEntity partnerLogin(String username, String password) throws InvalidLoginCredentialException
    {
        try
        {
            PartnerEntity partnerEntity = retrievePartnerByUsername(username);
            
            if(partnerEntity.getPassword().equals(password))
            {             
                return partnerEntity;
            }
            else
            {
                throw new InvalidLoginCredentialException("Username does not exist or invalid password!");
            }
        }
        catch(PartnerNotFoundException ex)
        {
            throw new InvalidLoginCredentialException("Username does not exist or invalid password!");
        }
        
    }
    
    @Override
    public PartnerEntity retrievePartnerByUsername(String username) throws PartnerNotFoundException
    {
        Query query = em.createQuery("SELECT p FROM PartnerEntity p WHERE p.username = :inUsername");
        query.setParameter("inUsername", username);
        
        try
        {
            return (PartnerEntity)query.getSingleResult();
        }
        catch(NoResultException | NonUniqueResultException ex)
        {
            throw new PartnerNotFoundException("Partner Username " + username + " does not exist!");
        }
    }
    
    @Override
    public PartnerEntity createNewPartner(PartnerEntity newPartnerEntity)
    {
        em.persist(newPartnerEntity);
        em.flush();
        return newPartnerEntity;
    }
    
    @Override
    public List<PartnerEntity> retrieveAllPartners()
    {
       Query query = em.createQuery("SELECT p FROM PartnerEntity p");
       return query.getResultList();
    }
    
    @Override
    public List<ReservationEntity> retrieveAllReservations(Long partnerId) throws ReservationNotFoundException{
        Query query = em.createQuery("SELECT c FROM CustomerEntity c WHERE c.customerId = :inPartnerId");
        query.setParameter("inCustomerId", partnerId);
        PartnerEntity partner = (PartnerEntity) query.getSingleResult();
        
        List<ReservationEntity> reservations = partner.getReservationEntities();

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

