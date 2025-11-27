package restAPI;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/records")
public class EmployeeRecords {

    @GET
    @Path("/ping")
    public Response ping() { return Response.ok().entity("Service online").build(); }

}
