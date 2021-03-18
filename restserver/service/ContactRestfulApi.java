package it.unical.dimes.reti.restserver.service;
import com.google.gson.Gson;
import it.unical.dimes.reti.restserver.model.Contact;
import it.unical.dimes.reti.restserver.queries.ContactQueries;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;

@Path("/")
public class ContactRestfulApi {

    static ContactQueries query = new ContactQueries();
    Gson gson = new Gson();

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        return "Benvenuto nella tua rubrica!";
    }

    @GET
    @Path("contact/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        Collection<Contact> ret = query.getAll();
        if (ret != null) {
            return Response.ok(gson.toJson(ret)).build();
        }
        return null;
    }

    @GET
    @Path("contact/byName/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getContactByName(@PathParam("name") String name) {
        Collection<Contact> ret = query.getByName(name);
        return Response.ok(gson.toJson(ret)).build();
    }

    @POST
    @Path("contact")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createContact(String contact) {
        query.insertOrUpdate(new Contact(contact));
        return Response.ok(gson.toJson(contact)).build();
    }

    @DELETE
    @Path("contact/{id}")
    public Response deleteContact(@PathParam("id") Long id) {
        query.delete(id);
        return Response.ok("Contatto cancellato!").build();
    }


}
