/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.PartnerEntity;
import java.util.List;
import util.exception.InvalidLoginCredentialException;
import util.exception.PartnerNotFoundException;

/**
 *
 * @author Lance
 */
public interface PartnerEntityControllerRemote 
{

    public PartnerEntity partnerLogin(String username, String password) throws InvalidLoginCredentialException;

    public PartnerEntity retrievePartnerByUsername(String username) throws PartnerNotFoundException;

    public PartnerEntity createNewPartner(PartnerEntity newPartnerEntity);

    public List<PartnerEntity> retrieveAllPartners();
    
}
