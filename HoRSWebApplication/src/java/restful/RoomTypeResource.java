/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restful;

import datamodel.RetrieveAllRoomTypesResp;
import ejb.session.stateless.RoomTypeEntityControllerLocal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author Wai Kin
 */
@Path("RoomType")
public class RoomTypeResource {

    RoomTypeEntityControllerLocal roomTypeEntityController = lookupRoomTypeEntityControllerLocal();

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of RecordResource
     */
    public RoomTypeResource() {
    }

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public Response retrieveAllRecordEntities() {
        List<entity.RoomTypeEntity> roomTypes = roomTypeEntityController.retrieveAllRoomTypes();
        
        RetrieveAllRoomTypesResp retrieveAllRecordEntitiesResp = new RetrieveAllRoomTypesResp(roomTypes);
        
        return Response.status(Response.Status.OK).entity(retrieveAllRecordEntitiesResp).build();
    }

    private RoomTypeEntityControllerLocal lookupRoomTypeEntityControllerLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (RoomTypeEntityControllerLocal) c.lookup("java:global/HotelReservationSystem/HotelReservationSystem-ejb/RoomTypeEntityController!ejb.session.stateless.RoomTypeEntityControllerLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
    
    
}
